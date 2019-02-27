package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Date;
import java.util.Optional;

/**
 * Unmodifiable implementation of StepInfo using pattern decorator.
 */
public class UnmodifiableStepInfo implements StepInfo {

    private static final String UNMODIFIABLE_MESSAGE = "This StepInfo is unmodifiable.";
    private final StepInfo stepInfo;

    /**
     * Decorator for {@link StepInfo} that construct an unmodifiable version of the stepInfo passed by parameter.
     * @param stepInfo an unmodifiable version of the stepInfo passed by parameter.
     */
    public UnmodifiableStepInfo(final StepInfo stepInfo) {
        this.stepInfo = stepInfo;
    }

    private Result<Empty> getUnmodifiableErrorResult() {
        return Result.error(new UnsupportedOperationException(UNMODIFIABLE_MESSAGE));
    }

    @Override
    public final StepType getType() {
        return this.stepInfo.getType();
    }

    @Override
    public final Optional<String> getNote() {
        return this.stepInfo.getNote();
    }

    @Override
    public final Result<Empty> setNote(final String note) {
        return getUnmodifiableErrorResult();
    }

    @Override
    public final Date getStartDate() {
        return this.stepInfo.getStartDate();
    }

    @Override
    public final Optional<Date> getEndDate() {
        return this.stepInfo.getEndDate();
    }

    @Override
    public final Result<Empty> setEndDate(final Date endDate) {
        return getUnmodifiableErrorResult();
    }

    @Override
    public final Optional<Quantity> getEndStepSize() {
        return this.stepInfo.getEndStepSize();
    }

    @Override
    public final Result<Empty> setEndStepSize(final Quantity endSize) {
        return getUnmodifiableErrorResult();
    }
}
