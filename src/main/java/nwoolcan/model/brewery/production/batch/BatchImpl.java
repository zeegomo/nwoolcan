package nwoolcan.model.brewery.production.batch;

import javafx.util.Pair;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.Steps;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;

import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

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
public final class BatchImpl implements Batch {

    private static final String CANNOT_CREATE_STEP_EXCEPTION = "Cannot create a step with the given type: ";
    private static final String CANNOT_FINALIZE_CURRENT_STEP = "Cannot finalize current step.";
    private static final String BATCH_IS_ENDED_MESSAGE = "Cannot perform operation because batch is in ended state.";
    private static final Object CANNOT_GO_TO_STEP_MESSAGE = "From this step, cannot go to step: ";
    private static final String BATCH_NOT_ENDED_MESSAGE = "Cannot perform operation because batch is not in ended state.";

    private final int id;
    private final ModifiableBatchInfo batchInfo;
    private final List<Step> steps;

    @Nullable
    private BatchEvaluation batchEvaluation;

    /**
     * Creates a new {@link Batch} in production.
     * @param beerDescription the batch's beer description.
     * @param batchMethod the batch's method.
     * @param initialSize the initial size of the batch.
     * @param ingredients the ingredients of the beer made by the batch.
     * @param initialStep the initial step of the batch.
     * @throws IllegalArgumentException if the initial step cannot be created.
     */
    public BatchImpl(final BeerDescription beerDescription,
                     final BatchMethod batchMethod,
                     final Quantity initialSize,
                     final Collection<Pair<IngredientArticle, Quantity>> ingredients,
                     final StepType initialStep) {
        this.id = BatchIdGenerator.getInstance().getNextId();
        this.batchInfo = new ModifiableBatchInfoImpl(ingredients, beerDescription, batchMethod, initialSize);

        final Result<Step> res = Steps.create(initialStep);
        if (res.isError()) {
            throw new IllegalArgumentException(CANNOT_CREATE_STEP_EXCEPTION + initialStep);
        }

        res.getValue().addParameterObserver(batchInfo);

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
    public List<Step> getPreviousSteps() {
        return this.steps.subList(0, this.steps.size() - 1);
    }

    private Result<Step> getPreviousStep() {
        return Results.ofChecked(() -> this.getPreviousSteps().get(this.getPreviousSteps().size() - 1));
    }

    private void checkAndFinalizeStep(final Step step) {
        if (!step.isFinalized()) {
            //noinspection OptionalGetWithoutIsPresent
            getPreviousStep().peekError(e -> step.finalize(null, new Date(), this.batchInfo.getBatchSize()))
                             .peek(lastStep -> step.finalize(null, new Date(), lastStep.getStepInfo().getEndStepSize().get()));
        }
    }

    @Override
    public Result<Empty> moveToNextStep(final StepType nextStepType) {
        return Result.of(this.getCurrentStep())
                     .require(() -> !this.isEnded(), new IllegalStateException(BATCH_IS_ENDED_MESSAGE))
                     .require(p -> p.getNextStepTypes().contains(nextStepType), new IllegalArgumentException(CANNOT_GO_TO_STEP_MESSAGE + nextStepType.toString()))
                     .peek(this::checkAndFinalizeStep)
                     .flatMap(() -> Steps.create(nextStepType))
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
    public String toString() {
        return new StringJoiner(", ", BatchImpl.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("batchInfo=" + batchInfo)
            .add("currentStep=" + this.getCurrentStep())
            .add("batchEvaluation=" + batchEvaluation)
            .toString();
    }
}
