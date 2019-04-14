package nwoolcan.viewmodel.brewery.production.step;

import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.parameter.QueryParameterBuilder;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DetailStepViewModel {

    private final String typeName;
    private final String notes;
    private final Date startDate;

    @Nullable
    private final Date endDate;
    @Nullable
    private final QuantityViewModel endSize;

    private final boolean finalized;

    private final List<ParameterViewModel> registeredParameters;

    public DetailStepViewModel(final Step step) {
        this.typeName = step.getStepInfo().getType().getName();
        this.notes = step.getStepInfo().getNote().orElse("");
        this.startDate = new Date(step.getStepInfo().getStartDate().getTime());
        this.endDate = step.getStepInfo().getEndDate().map(d -> new Date(d.getTime())).orElse(null);
        this.endSize = step.getStepInfo().getEndStepSize().map(QuantityViewModel::new).orElse(null);
        this.finalized = step.isFinalized();
        this.registeredParameters = step.getParameters(new QueryParameterBuilder().sortByDate(true)
                                                                                  .sortDescending(true)
                                                                                  .build().getValue())
                                        .stream()
                                        .map(ParameterViewModel::new)
                                        .collect(Collectors.toList());
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getNotes() {
        return this.notes;
    }

    public Date getStartDate() {
        return new Date(this.startDate.getTime());
    }

    @Nullable
    public Date getEndDate() {
        return Optional.ofNullable(this.endDate).map(d -> new Date(this.endDate.getTime())).orElse(null);
    }

    @Nullable
    public QuantityViewModel getEndSize() {
        return this.endSize;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public List<ParameterViewModel> getRegisteredParameters() {
        return Collections.unmodifiableList(this.registeredParameters);
    }
}
