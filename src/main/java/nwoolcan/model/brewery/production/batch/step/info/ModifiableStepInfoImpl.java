package nwoolcan.model.brewery.production.batch.step.info;

import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Simple ModifiableStepInfo class implementation.
 */
public final class ModifiableStepInfoImpl extends StepInfoImpl implements ModifiableStepInfo {

    private static final String INVALID_END_DATE_MESSAGE = "endDate is before startDate.";

    /**
     * Constructs a simple StepInfo of stepType and started at startDate.
     * @param stepType type of the step.
     * @param startDate date when the step started.
     */
    public ModifiableStepInfoImpl(final StepType stepType, final Date startDate) {
        super(stepType, startDate);
    }

    @Override
    public void setNote(@Nullable final String note) {
        super.setInternalNote(note);
    }

    @Override
    public Result<Empty> setEndDate(final Date endDate) {
        return Result.of(endDate)
                     .require(d -> !d.before(this.getStartDate()), new IllegalArgumentException(INVALID_END_DATE_MESSAGE))
                     .peek(super::setInternalEndDate)
                     .toEmpty();
    }

    @Override
    public void setEndStepSize(final Quantity endSize) {
        super.setInternalEndStepSize(endSize);
    }
}
