package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Interface for handling a beer batch.
 */
public interface Batch {

    /**
     * Returns the info of this batch.
     * @return the info of this batch.
     */
    BatchInfo getBatchInfo();

    /**
     * Returns the current production step of the batch.
     * @return the current production step of the batch.
     */
    Step getCurrentStep();

    /**
     * Returns a Result containing a list describing all production steps
     * that happened before the current one, in chronological order.
     * @return a list describing all production steps that happened before the current one.
     */
    Result<List<Step>> getLastSteps();

    /**
     * Goes to the next step of type passed by parameter if the current step has been finalized,
     * a Result with a {@link IllegalStateException} otherwise.
     * @param nextStepType the next step type.
     * @return a Result with an error if the current step has not been finalized.
     */
    Result<Empty> moveToNextStep(StepType nextStepType);

    /**
     * Finalize the current batch and sets a possible evaluation to the batch.
     * If the current step has not been finalized or the batch has already been finalized,
     * this method does nothing and returns a Result with a {@link IllegalStateException}.
     * @param evaluation the optional evaluation of the batch.
     * @return a Result with an error if the current step has not been finalized.
     */
    Result<Empty> finalizeAndSetEvaluation(@Nullable BatchEvaluation evaluation);

    /**
     * Returns the BatchEvaluation of this batch if it has one.
     * @return the BatchEvaluation of this batch if it has one.
     */
    Optional<BatchEvaluation> getEvaluation();
}
