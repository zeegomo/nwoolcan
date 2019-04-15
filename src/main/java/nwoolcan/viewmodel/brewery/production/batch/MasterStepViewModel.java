package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

/**
 * View model representing a step in a master table view.
 */
public final class MasterStepViewModel {

    private final StepType type;
    private final Date startDate;

    @Nullable
    private final Date endDate;
    @Nullable
    private final QuantityViewModel endSize;

    private final boolean finalized;

    /**
     * Basic constructor with decorator-like pattern.
     * @param step the step to get data from.
     */
    public MasterStepViewModel(final Step step) {
        this.type = step.getStepInfo().getType();
        this.startDate = new Date(step.getStepInfo().getStartDate().getTime());
        this.endDate = step.getStepInfo().getEndDate().map(d -> new Date(d.getTime())).orElse(null);
        this.endSize = step.getStepInfo().getEndStepSize().map(QuantityViewModel::new).orElse(null);
        this.finalized = step.isFinalized();
    }

    /**
     * Returns the step type.
     * @return the step type.
     */
    public StepType getType() {
        return this.type;
    }

    /**
     * Returns the start date of the step.
     * @return the start date of the step.
     */
    public Date getStartDate() {
        return new Date(this.startDate.getTime());
    }

    /**
     * Returns the end date of the step (null if none).
     * @return the end date of the step (null if none).
     */
    @Nullable
    public Date getEndDate() {
        return Optional.ofNullable(this.endDate).map(d -> new Date(d.getTime())).orElse(null);
    }

    /**
     * Returns the {@link QuantityViewModel} representing the end size of the step.
     * @return the {@link QuantityViewModel} representing the end size of the step.
     */
    @Nullable
    public QuantityViewModel getEndSize() {
        return this.endSize;
    }

    /**
     * Returns true if the step is finalized, false otherwise.
     * @return true if the step is finalized.
     */
    public boolean isFinalized() {
        return this.finalized;
    }
}
