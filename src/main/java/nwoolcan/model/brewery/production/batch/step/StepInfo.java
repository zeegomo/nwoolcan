package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

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
     * Sets the notes written at step finalization.
     * @param note notes to be set.
     * @return a {@link Result} of {@link Empty} that contains a {@link UnsupportedOperationException}
     * if the object is unmodifiable.
     */
    Result<Empty> setNote(String note);

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
     * Sets the date when the step is finalized.
     * @param endDate the date when the step is finalized.
     * @return a {@link Result} that contains a {@link UnsupportedOperationException}
     * if the object is unmodifiable, or contains a {@link IllegalArgumentException} if the endDate
     * is before the startDate.
     */
    Result<Empty> setEndDate(Date endDate);

    /**
     * Returns the quantity at step finalization
     * or an empty optional if not already finalized.
     * @return the quantity at step finalization
     * or an empty optional if not already finalized.
     */
    Optional<Quantity> getEndStepSize();

    /**
     * Sets the quantity size at step finalization.
     * @param endSize the quantity size at step finalization.
     * @return a {@link Result} that contains a {@link UnsupportedOperationException}
     * if the object is unmodifiable.
     */
    Result<Empty> setEndStepSize(Quantity endSize);
}
