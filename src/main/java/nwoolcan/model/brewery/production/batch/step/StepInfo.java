package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;

import java.util.Date;
import java.util.Optional;

/**
 * Interface that describes all basic information that has a production step.
 */
public interface StepInfo {
    /**
     * Returns the {@link StepType} associated to the step.
     * @return the {@link StepType} associated to the step.
     */
    StepType getType();

    /**
     * Returns the notes written at step finalization.
     * @return the notes written at step finalization.
     */
    Optional<String> getNote();

    /**
     * Returns the date when the step was created.
     * @return the date when the step was created.
     */
    Date getStartDate();

    /**
     * Returns the date when the step was finalized
     * or an empty optional if not already finalized.
     * @return the date when the step was finalized
     * or an empty optional if not already finalized.
     */
    Optional<Date> getEndDate();

    /**
     * Returns the quantity at step finalization
     * or an empty optional if not already finalized.
     * @return the quantity at step finalization
     * or an empty optional if not already finalized.
     */
    Optional<Quantity> getEndStepSize();
}
