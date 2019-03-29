package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

/**
 * Implementation of StepInfo using pattern decorator for constructor.
 * Package-private.
 */
class StepInfoImpl implements StepInfo {

    private static final String INVALID_END_DATE_MESSAGE = "endDate is before startDate.";

    private final StepType stepType;
    private final Date startDate;

    @Nullable
    private String note;
    @Nullable
    private Date endDate;
    @Nullable
    private Quantity endSize;

    StepInfoImpl(final StepType stepType, final Date startDate) {
        this.stepType = stepType;
        this.startDate = new Date(startDate.getTime());
    }

    /**
     * Decorator constructor effectively copying the stepInfo passed by parameter.
     * @param stepInfo stepInfo to use as copy.
     */
    StepInfoImpl(final StepInfo stepInfo) {
        this(stepInfo.getType(), stepInfo.getStartDate());
        stepInfo.getNote().ifPresent(this::setInternalNote);
        stepInfo.getEndDate().ifPresent(this::setInternalEndDate);
        stepInfo.getEndStepSize().ifPresent(this::setInternalEndStepSize);
    }

    @Override
    public final StepType getType() {
        return this.stepType;
    }

    @Override
    public final Optional<String> getNote() {
        return Optional.ofNullable(this.note);
    }

    @Override
    public final Date getStartDate() {
        return new Date(this.startDate.getTime());
    }

    @Override
    public final Optional<Date> getEndDate() {
        return Optional.ofNullable(this.endDate).map(d -> new Date(d.getTime()));
    }

    @Override
    public final Optional<Quantity> getEndStepSize() {
        return Optional.ofNullable(this.endSize);
    }

    @Override
    public final String toString() {
        return "[StepInfoImpl] {"
            + "stepType=" + stepType
            + ", note='" + note + '\''
            + ", startDate=" + startDate
            + ", endDate=" + endDate
            + ", endSize=" + endSize
            + '}';
    }

    /**
     * Sets internal note.
     * @param note string to set as note.
     */
    void setInternalNote(@Nullable final String note) {
        this.note = note;
    }

    /**
     * Sets internal end date.
     * @param endDate date to set as end date.
     */
    void setInternalEndDate(final Date endDate) {
        this.endDate = new Date(endDate.getTime());
    }

    /**
     * Sets internal end size.
     * @param endSize quantity to set as end size.
     */
    void setInternalEndStepSize(final Quantity endSize) {
        this.endSize = endSize;
    }
}
