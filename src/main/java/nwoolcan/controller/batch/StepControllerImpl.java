package nwoolcan.controller.batch;

import nwoolcan.controller.ControllerUtils;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.step.parameter.ParameterFactory;
import nwoolcan.model.brewery.batch.step.parameter.QueryParameter;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.step.DetailStepViewModel;
import nwoolcan.viewmodel.brewery.production.step.ParameterViewModel;
import nwoolcan.viewmodel.brewery.production.step.RegisterParameterDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation on step controller.
 */
public final class StepControllerImpl implements StepController {

    private final ControllerUtils utils;

    /**
     * Basic constructor injecting the brewery model.
     * @param model the brewery model.
     */
    public StepControllerImpl(final Brewery model) {
        this.utils = new ControllerUtils(model);
    }

    @Override
    public Result<DetailStepViewModel> getDetailStepViewModel(final int batchId, final String stepTypeName) {
        return this.utils.getStepByBatchIdAndStepTypeName(batchId, stepTypeName)
                         .map(s -> new DetailStepViewModel(batchId, s));
    }

    @Override
    public List<ParameterViewModel> getRegisteredParameters(final int batchId, final String stepTypeName, final QueryParameter query) {
        return this.utils.getStepByBatchIdAndStepTypeName(batchId, stepTypeName)
                         .map(s -> s.getParameters(query)
                                    .stream()
                                    .map(ParameterViewModel::new)
                                    .collect(Collectors.toList()))
                         .orElse(Collections.emptyList());
    }

    @Override
    public Result<Empty> registerParameter(final RegisterParameterDTO data) {
        return this.utils.getBatchById(data.getBatchId())
                         .flatMap(b -> ParameterFactory.create(data.getType(), data.getValue(), data.getRegistrationDate())
                                                       .flatMap(p -> b.getCurrentStep().addParameter(p)));
    }
}
