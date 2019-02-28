package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Simple StepInfo class implementation.
 */
public class StepInfoImpl implements StepInfo {

    private static final String INVALID_END_DATE_MESSAGE = "endDate is before startDate.";

    private final StepType stepType;
    private String note;
    private final Date startDate;
    private Date endDate;
    private Quantity endSize;

    /**
     * Constructs a simple StepInfo of stepType and started at startDate.
     * @param stepType type of the step.
     * @param startDate date when the step started.
     * @throws NullPointerException if stepType od startDate are null.
     */
    public StepInfoImpl(final StepType stepType, final Date startDate) {
        Objects.requireNonNull(stepType);
        Objects.requireNonNull(startDate);
        this.stepType = stepType;
        this.startDate = startDate;
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
    public final Result<Empty> setNote(final String note) {
        this.note = note;
        return Result.ofEmpty();
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
    public final Result<Empty> setEndDate(final Date endDate) {
        Result<Empty> res = Result.ofEmpty();
        if (endDate != null) {
            res = res.require(e -> !endDate.before(this.startDate),
                    new IllegalArgumentException(INVALID_END_DATE_MESSAGE));

            if (!res.isError()) {
                this.endDate = new Date(endDate.getTime());
            }
        } else {
            this.endDate = null;
        }

        return res;
    }

    @Override
    public final Optional<Quantity> getEndStepSize() {
        return Optional.ofNullable(this.endSize);
    }

    @Override
    public final Result<Empty> setEndStepSize(final Quantity endSize) {
        this.endSize = endSize;
        return Result.ofEmpty();
    }
}
