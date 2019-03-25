package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.production.batch.step.Steps;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Pair;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Basic batch implementation.
 */
public final class BatchImpl implements Batch {

    private static final String CANNOT_CREATE_STEP_EXCEPTION = "Cannot create a step with the given type: ";
    private static final String CANNOT_FINALIZE_CURRENT_STEP = "Cannot finalize current step.";

    private final ModifiableBatchInfo batchInfo;
    private final List<Step> steps;

    @Nullable
    private BatchEvaluation batchEvaluation;

    public BatchImpl(final BeerDescription beerDescription,
                     final BatchMethod batchMethod,
                     final Quantity initialSize,
                     final Collection<Pair<IngredientArticle, Quantity>> ingredients,
                     final StepType initialStep) {
        //TODO insert parameters
        this.batchInfo = new ModifiableBatchInfo();

        final Result<Step> res = Steps.create(initialStep);
        if (res.isError()) {
            throw new IllegalArgumentException(CANNOT_CREATE_STEP_EXCEPTION + initialStep);
        }

        res.getValue().addObserver(batchInfo);

        this.steps = new ArrayList<>(Collections.singletonList(res.getValue()));
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
        return Result.ofChecked(this.steps.get(this.steps.size() - 1));
    }

    private void checkAndFinalizeStep(final Step step) {
        if (!step.isFinalized()) {
            Quantity currentSize = getPreviousSteps().size() == 0 ? this.batchInfo.getBatchSize() : this.getPreviousSteps().get(this.getPreviousSteps().size() - 1).getStepInfo().getEndStepSize().get();
            step.finalize(null, new Date(), currentSize);
        }
    }

    @Override
    public Result<Empty> moveToNextStep(final StepType nextStepType) {
        return Result.of(this.getCurrentStep())
                     .require(() -> !this.isEnded(), new IllegalStateException())
                     .require(p -> p.getNextStepTypes().contains(nextStepType), new IllegalArgumentException())
                     .peek(this::checkAndFinalizeStep)
                     .flatMap(() -> Steps.create(nextStepType))
                     .peek(this.steps::add)
                     .peek(p -> p.addObserver(this.batchInfo))
                     .toEmpty();
    }

    @Override
    public boolean isEnded() {
        return this.getCurrentStep().getStepInfo().getType().equals(StepTypeEnum.Finalized);
    }

    @Override
    public Result<Empty> setEvaluation(final BatchEvaluation evaluation) {
        return Result.ofEmpty()
                     .require(this::isEnded, new IllegalStateException())
                     .peek(e -> this.batchEvaluation = evaluation);
    }

    @Override
    public Optional<BatchEvaluation> getEvaluation() {
        return Optional.ofNullable(this.batchEvaluation);
    }
}
