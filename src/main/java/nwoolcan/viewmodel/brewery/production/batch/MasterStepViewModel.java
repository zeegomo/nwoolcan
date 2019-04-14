package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

public class MasterStepViewModel {

    private final StepType type;
    private final Date startDate;

    @Nullable
    private final Date endDate;
    @Nullable
    private final QuantityViewModel endSize;

    private final boolean finalized;

    public MasterStepViewModel(final Step step) {
        this.type = step.getStepInfo().getType();
        this.startDate = new Date(step.getStepInfo().getStartDate().getTime());
        this.endDate = step.getStepInfo().getEndDate().map(d -> new Date(d.getTime())).orElse(null);
        this.endSize = step.getStepInfo().getEndStepSize().map(QuantityViewModel::new).orElse(null);
        this.finalized = step.isFinalized();
    }

    public StepType getType() {
        return this.type;
    }

    public Date getStartDate() {
        return new Date(this.startDate.getTime());
    }

    @Nullable
    public Date getEndDate() {
        return Optional.ofNullable(this.endDate).map(d -> new Date(d.getTime())).orElse(null);
    }

    @Nullable
    public QuantityViewModel getEndSize() {
        return this.endSize;
    }

    public boolean isFinalized() {
        return this.finalized;
    }
}
