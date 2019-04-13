package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Modifiable version of StepInfo.
 */
public interface ModifiableStepInfo extends StepInfo {

    /**
     * Sets the notes written at step finalization.
     * @param note notes to be set.
     * if the object is unmodifiable.
     */
    void setNote(@Nullable String note);

    /**
     * Sets the date when the step is finalized.
     * @param endDate the date when the step is finalized.
     * @return a {@link Result} that contains a {@link IllegalArgumentException} if the endDate
     * is before the startDate.
     */
    Result<Empty> setEndDate(Date endDate);

    /**
     * Sets the quantity size at step finalization.
     * @param endSize the quantity size at step finalization.
     */
    void setEndStepSize(Quantity endSize);
}
