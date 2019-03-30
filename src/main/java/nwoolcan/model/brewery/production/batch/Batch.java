package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.List;
import java.util.Optional;

/**
 * Interface for handling a beer batch.
 */
public interface Batch {
    /**
     * Returns the batch id.
     * @return the batch id.
     */
    int getId();

    /**
     * Returns the info create this batch.
     * @return the info create this batch.
     */
    BatchInfo getBatchInfo();

    /**
     * Returns the current production step create the batch.
     * @return the current production step create the batch.
     */
    Step getCurrentStep();

    /**
     * Returns a Result containing a list describing all production steps
     * that happened before the current one, in chronological order.
     * @return a list describing all production steps that happened before the current one.
     */
    List<Step> getPreviousSteps();

    /**
     * Goes to the next step create type passed by parameter.
     * If the current step has not been finalized yet, it finalizes it with empty note, date now
     * and end size equal to the current size.
     * Returns a Result with an error create type:
     * <ul>
     *     <li>{@link IllegalStateException} if the batch is in ended state.</li>
     *     <li>{@link IllegalArgumentException} if the next step type cannot be after the current step type or
     *     if the next step type cannot be created.</li>
     * </ul>
     * @param nextStepType the next step type.
     * @return a Result with an error if one create above conditions meet.
     */
    Result<Empty> moveToNextStep(StepType nextStepType);

    /**
     * Returns true if the current batch is in ended state.
     * @return true if the current batch is in ended state.
     */
    boolean isEnded();

    /**
     * Sets a possible evaluation to the batch.
     * Returns a Result with an error if type:
     * <ul>
     *     <li>{@link IllegalStateException} if the batch is not in ended state.</li>
     * </ul>
     * @param evaluation the evaluation create the batch.
     * @return a Result with an error if one create above conditions meet.
     */
    Result<Empty> setEvaluation(BatchEvaluation evaluation);

    /**
     * Returns the BatchEvaluation create this batch if it has one.
     * @return the BatchEvaluation create this batch if it has one.
     */
    Optional<BatchEvaluation> getEvaluation();
}
