package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.production.batch.step.Steps;
import nwoolcan.model.brewery.production.batch.step.utils.StepHelper;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Basic batch implementation.
 */
public final class BatchImpl implements Batch {

    private static final String CANNOT_CREATE_STEP_EXCEPTION = "Cannot create a step with the given type: ";
    private static final String CANNOT_FINALIZE_CURRENT_STEP = "Cannot finalize current step.";

    private final BatchInfo batchInfo;
    private final List<Step> steps;

    @Nullable
    private BatchEvaluation batchEvaluation;

    /**
     * Creates a batch with info and initial step passed bt parameter.
     * @param batchInfo new batch's info.
     * @param initialStep batch's initial step type.
     * @throws IllegalArgumentException if the initial step cannot be created.
     */
    public BatchImpl(final BatchInfo batchInfo, final StepType initialStep) {
        this.batchInfo = batchInfo;
        final Result<Step> res = Steps.create(initialStep);
        if (res.isError()) {
            throw new IllegalArgumentException(CANNOT_CREATE_STEP_EXCEPTION + initialStep);
        }
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
    public List<Step> getLastSteps() {
        return this.steps.subList(0, this.steps.size() - 1);
    }

    private Result<Empty> checkAndFinalizeCurrentStep() {
        return Result.ofEmpty()
                     .flatMap(() -> {
                         if (!this.getCurrentStep().isFinalized()) {
                            if (this.getCurrentStep().getStepInfo().getEndStepSize().isPresent()) {
                                return this.getCurrentStep().finalize(null, new Date(),
                                    this.getCurrentStep().getStepInfo().getEndStepSize().get());
                            }
                            return Result.error(new IllegalStateException(CANNOT_FINALIZE_CURRENT_STEP));
                         }
                         return Result.ofEmpty();
                     });
    }

    @Override
    public Result<Empty> moveToNextStep(final StepType nextStepType) {
        return Result.ofEmpty()
                     .require(() -> !this.isEnded(), new IllegalStateException())
                     .require(() -> StepHelper.getNextStepTypesOf(this.getCurrentStep().getStepInfo().getType()).contains(nextStepType),
                         new IllegalArgumentException())
                     .flatMap(this::checkAndFinalizeCurrentStep)
                     .flatMap(() -> Steps.create(nextStepType))
                     .peek(this.steps::add)
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
