package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.IdGenerator;
import nwoolcan.model.brewery.batch.misc.BeerDescription;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.StepFactory;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.Record;
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
import java.util.function.Supplier;

/**
 * Basic batch implementation.
 */
final class BatchImpl implements Batch {

    private static final String CANNOT_CREATE_STEP_EXCEPTION = "Cannot create a step with the given type: ";
    private static final String CANNOT_FINALIZE_CURRENT_STEP = "Cannot finalize current step.";
    private static final String CANNOT_GO_TO_STEP_MESSAGE = "From this step, cannot go to step: ";
    private static final String BATCH_NOT_ENDED_MESSAGE = "Cannot perform operation because batch is not in ended state.";
    private static final String BATCH_ALREADY_STOCKED_MESSAGE = "Cannot stock an already stocked batch.";
    private static final String INVALID_ARTICLE_UNIT_OF_MEASURE_MESSAGE = "Invalid article unit of measure when stocking.";

    private final int id;
    private final ModifiableBatchInfo batchInfo;
    private final List<Step> steps;
    private final StepFactory stepFactory;

    @Nullable
    private BatchEvaluation batchEvaluation;
    @Nullable
    private Integer stockIdReference;

    /**
     * Creates a new {@link Batch} in production.
     * @param beerDescription the batch's beer description.
     * @param batchMethod the batch's method.
     * @param initialSize the initial size of the batch.
     * @param ingredients the ingredients of the beer made by the batch.
     * @param waterMeasurement the water measurement of the batch.
     * @throws IllegalArgumentException if the initial step cannot be created.
     */
    BatchImpl(final BeerDescription beerDescription,
              final BatchMethod batchMethod,
              final Quantity initialSize,
              final Collection<Pair<IngredientArticle, Double>> ingredients,
              @Nullable final WaterMeasurement waterMeasurement,
              final IdGenerator generator,
              final StepFactory stepFactory) {
        this.stepFactory = stepFactory;

        if (waterMeasurement == null) {
            this.batchInfo = ModifiableBatchInfoFactory.create(ingredients, beerDescription, batchMethod, initialSize);
        } else {
            this.batchInfo = ModifiableBatchInfoFactory.create(ingredients, beerDescription, batchMethod, initialSize, waterMeasurement);
        }

        final Result<Step> res = this.stepFactory.create(batchMethod.getInitialStep());
        res.peekError(e -> {
            throw new IllegalArgumentException(e);
        }).peek(step -> step.addParameterObserver(batchInfo));

        this.steps = new ArrayList<>(Collections.singletonList(res.getValue()));
        this.id = generator.getNextId();
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
        return this.batchInfo.getInitialBatchSize();
    }

    @Override
    public List<Step> getSteps() {
        return Collections.unmodifiableList(this.steps);
    }

    private Result<Step> getPreviousStep() {
        return Results.ofChecked(() -> this.getSteps().get(this.getSteps().size() - 2));
    }

    private Result<Empty> checkAndFinalizeStep(final Step step) {
        if (!step.isFinalized()) {
            return step.finalize(null, new Date(), this.getCurrentSize());
        }
        return Result.ofEmpty();
    }

    @Override
    public Result<Empty> moveToNextStep(final StepType nextStepType) {
        return Result.of(this.getCurrentStep())
                     .require(p -> p.getNextStepTypes().contains(nextStepType), new IllegalArgumentException(CANNOT_GO_TO_STEP_MESSAGE + nextStepType.toString()))
                     .flatMap(this::checkAndFinalizeStep)
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
        return this.stockIdReference != null;
    }

    @Override
    public Result<Empty> stockBatchInto(final BeerArticle article, final Supplier<BeerStock> supplier) {
        return Result.of(article)
                     .require(this::isEnded, new IllegalStateException(BATCH_NOT_ENDED_MESSAGE))
                     .require(() -> !this.isStocked(), new IllegalStateException(BATCH_ALREADY_STOCKED_MESSAGE))
                     .require(a -> a.getUnitOfMeasure().equals(this.getCurrentSize().getUnitOfMeasure()),
                         new IllegalArgumentException(INVALID_ARTICLE_UNIT_OF_MEASURE_MESSAGE))
                     .peek(a -> {
                         final BeerStock stock = supplier.get();
                         stock.addRecord(new Record(
                             this.getCurrentSize(),
                             new Date(),
                             Record.Action.ADDING
                         ));
                         this.setStockReference(stock);
                     }).toEmpty();
    }

    private void setStockReference(final BeerStock stock) {
        this.stockIdReference = stock.getId();
    }

    @Override
    public Optional<Integer> getStockIdReference() {
        return Optional.ofNullable(this.stockIdReference);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BatchImpl.class.getSimpleName() + "[", "]")
            .add("id=" + this.id)
            .add("batchInfo=" + this.batchInfo)
            .add("currentStep=" + this.getCurrentStep())
            .add("batchEvaluation=" + this.batchEvaluation)
            .add("stockReference=" + this.stockIdReference)
            .toString();
    }
}
