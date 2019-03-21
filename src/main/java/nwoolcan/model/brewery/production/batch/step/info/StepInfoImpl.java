package nwoolcan.model.brewery.production.batch.step.info;

import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

/**
 * Simple StepInfo class implementation.
 */
public final class StepInfoImpl implements StepInfo {

    private static final String INVALID_END_DATE_MESSAGE = "endDate is before startDate.";

    private final StepType stepType;
    private final Date startDate;

    @Nullable
    private String note;
    @Nullable
    private Date endDate;
    @Nullable
    private Quantity endSize;

    /**
     * Constructs a simple StepInfo of stepType and started at startDate.
     * @param stepType type of the step.
     * @param startDate date when the step started.
     */
    public StepInfoImpl(final StepType stepType, final Date startDate) {
        this.stepType = stepType;
        this.startDate = startDate;
    }

    @Override
    public StepType getType() {
        return this.stepType;
    }

    @Override
    public Optional<String> getNote() {
        return Optional.ofNullable(this.note);
    }

    @Override
    public Result<Empty> setNote(@Nullable final String note) {
        this.note = note;
        return Result.ofEmpty();
    }

    @Override
    public Date getStartDate() {
        return new Date(this.startDate.getTime());
    }

    @Override
    public Optional<Date> getEndDate() {
        return Optional.ofNullable(this.endDate).map(d -> new Date(d.getTime()));
    }

    @Override
    public Result<Empty> setEndDate(final Date endDate) {
        return Result.of(endDate)
                     .require(d -> !d.before(this.startDate), new IllegalArgumentException(INVALID_END_DATE_MESSAGE))
                     .peek(d -> this.endDate = new Date(d.getTime()))
                     .toEmpty();
    }

    @Override
    public Optional<Quantity> getEndStepSize() {
        return Optional.ofNullable(this.endSize);
    }

    @Override
    public Result<Empty> setEndStepSize(final Quantity endSize) {
        this.endSize = endSize;
        return Result.ofEmpty();
    }

    @Override
    public String toString() {
        return "[StepInfoImpl] {"
            + "stepType=" + stepType
            + ", note='" + note + '\''
            + ", startDate=" + startDate
            + ", endDate=" + endDate
            + ", endSize=" + endSize
            + '}';
    }
}
