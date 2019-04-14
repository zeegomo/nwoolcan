package nwoolcan.viewmodel.brewery.production.step;

import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.utils.Quantity;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

public class DetailStepViewModel {

    private final String typeName;
    private final String notes;
    private final Date startDate;

    @Nullable
    private final Date endDate;
    @Nullable
    private final QuantityViewModel endSize;

    private final List<ParameterViewModel>

    public DetailStepViewModel(final Step step) {

    }
}
