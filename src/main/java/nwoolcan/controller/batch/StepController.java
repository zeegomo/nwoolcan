package nwoolcan.controller.batch;

import nwoolcan.model.brewery.batch.step.parameter.QueryParameter;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.step.DetailStepViewModel;
import nwoolcan.viewmodel.brewery.production.step.ParameterViewModel;
import nwoolcan.viewmodel.brewery.production.step.RegisterParameterDTO;

import java.util.List;

/**
 * Interface representing a controller for steps.
 */
public interface StepController {

    /**
     * Returns the built view model for a step detail, given the batch id and the step type name.
     * @param batchId the step's batch id.
     * @param stepTypeName the step type name.
     * @return a {@link Result} with the build view model or an error if occurred.
     */
    Result<DetailStepViewModel> getDetailStepViewModel(int batchId, String stepTypeName);

    /**
     * Returns the list of all registered parameters for a step, given the batch id and the step type name.
     * The query can select only specified parameters.
     * This method is to use to refresh the list of parameters in the view.
     * @param batchId the step's batch id.
     * @param stepTypeName the step type name.
     * @param query the query to execute.
     * @return a list of view models for parameters.
     */
    List<ParameterViewModel> getRegisteredParameters(int batchId, String stepTypeName, QueryParameter query);

    /**
     * Registers a parameter for the current step of the batch described in the DTO.
     * @param data the data to use to register the parameter.
     * @return a {@link Result} with an error if occurred.
     */
    Result<Empty> registerParameter(RegisterParameterDTO data);
}
