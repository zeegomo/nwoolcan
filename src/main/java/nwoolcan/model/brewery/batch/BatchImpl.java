package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.IdGenerator;
import nwoolcan.model.brewery.batch.misc.BeerDescription;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.StepFactory;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;

import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Basic batch implementation.
 */
final class BatchImpl implements Batch {

    private static final String CANNOT_CREATE_STEP_EXCEPTION = "Cannot create a step with the given type: ";
    private static final String CANNOT_FINALIZE_CURRENT_STEP = "Cannot finalize current step.";
    private static final Object CANNOT_GO_TO_STEP_MESSAGE = "From this step, cannot go to step: ";
    private static final String BATCH_NOT_ENDED_MESSAGE = "Cannot perform operation because batch is not in ended state.";

    private final int id;
    private final ModifiableBatchInfo batchInfo;
    private final List<Step> steps;
    private final StepFactory stepFactory;

    @Nullable
    private BatchEvaluation batchEvaluation;
    @Nullable
    private Stock stockReference;

    /**
     * Creates a new {@link Batch} in production.
     * @param beerDescription the batch's beer description.
     * @param batchMethod the batch's method.
     * @param initialSize the initial size of the batch.
     * @param ingredients the ingredients of the beer made by the batch.
     * @param initialStep the initial step of the batch.
     * @param waterMeasurement the water measurement of the batch.
     * @throws IllegalArgumentException if the initial step cannot be created.
     */
    BatchImpl(final BeerDescription beerDescription,
              final BatchMethod batchMethod,
              final Quantity initialSize,
              final Collection<Pair<IngredientArticle, Double>> ingredients,
              final StepType initialStep,
              @Nullable final WaterMeasurement waterMeasurement,
              final IdGenerator generator,
              final StepFactory stepFactory) {
        this.id = generator.getNextId();
        this.stepFactory = stepFactory;

        if (waterMeasurement == null) {
            this.batchInfo = ModifiableBatchInfoFactory.create(ingredients, beerDescription, batchMethod, initialSize);
        } else {
            this.batchInfo = ModifiableBatchInfoFactory.create(ingredients, beerDescription, batchMethod, initialSize, waterMeasurement);
        }

        final Result<Step> res = this.stepFactory.create(initialStep);
        res.peekError(e -> {
            throw new IllegalArgumentException(e);
        }).peek(step -> step.addParameterObserver(batchInfo));

        this.steps = new ArrayList<>(Collections.singletonList(res.getValue()));
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public BatchInfo getBatchInfo() {
        return this.batchInfo;
    }

    @Override
    public Step getCurrentStep() {
        return this.steps.get(this.steps.size() - 1);
    }

    @Override
    public Quantity getCurrentSize() {
        final Result<Quantity> prev = this.getPreviousStep()
                                          .flatMap(s -> Results.ofChecked(() -> s.getStepInfo().getEndStepSize().get()));
        if (prev.isPresent()) {
            return prev.getValue();
        }
        return this.batchInfo.getBatchSize();
    }

    @Override
    public List<Step> getSteps() {
        return Collections.unmodifiableList(this.steps);
    }

    private Result<Step> getPreviousStep() {
        return Results.ofChecked(() -> this.getSteps().get(this.getSteps().size() - 2));
    }

    private void checkAndFinalizeStep(final Step step) {
        if (!step.isFinalized()) {
            step.finalize(null, new Date(), this.getCurrentSize());
        }
    }

    @Override
    public Result<Empty> moveToNextStep(final StepType nextStepType) {
        return Result.of(this.getCurrentStep())
                     .require(p -> p.getNextStepTypes().contains(nextStepType), new IllegalArgumentException(CANNOT_GO_TO_STEP_MESSAGE + nextStepType.toString()))
                     .peek(this::checkAndFinalizeStep)
                     .flatMap(() -> this.stepFactory.create(nextStepType))
                     .peek(this.steps::add)
                     .peek(p -> p.addParameterObserver(this.batchInfo))
                     .toEmpty();
    }

    @Override
    public boolean isEnded() {
        return this.getCurrentStep().getStepInfo().getType().isEndType();
    }

    @Override
    public Result<Empty> setEvaluation(final BatchEvaluation evaluation) {
        return Result.ofEmpty()
                     .require(this::isEnded, new IllegalStateException(BATCH_NOT_ENDED_MESSAGE))
                     .peek(e -> this.batchEvaluation = evaluation);
    }

    @Override
    public Optional<BatchEvaluation> getEvaluation() {
        return Optional.ofNullable(this.batchEvaluation);
    }

    @Override
    public boolean isStocked() {
        return this.stockReference != null;
    }

    private void setStockReference(final Stock stock) {
        this.stockReference = stock;
    }

    @Override
    public Result<Empty> stockBatchInto(final Stock stock) {
        return Result.of(stock)
                     .require(this::isEnded)
                     .require(() -> !this.isStocked(), new IllegalStateException())
                     .require(s -> s.getArticle().getUnitOfMeasure().equals(this.getCurrentSize().getUnitOfMeasure()))
                     .peek(this::setStockReference)
                     .toEmpty();
    }

    @Override
    public Optional<Stock> getStockReference() {
        return Optional.ofNullable(this.stockReference);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BatchImpl.class.getSimpleName() + "[", "]")
            .add("id=" + this.id)
            .add("batchInfo=" + this.batchInfo)
            .add("currentStep=" + this.getCurrentStep())
            .add("batchEvaluation=" + this.batchEvaluation)
            .add("stockReference=" + this.stockReference)
            .toString();
    }
}
