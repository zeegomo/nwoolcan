package nwoolcan.controller.batch;

import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepDTO;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;

/**
 * Interface representing a batch controller.
 */
public interface BatchController {

    /**
     * Returns a {@link DetailBatchViewModel} representation of the batch of id passed by parameter, if any.
     * @param batchId the batch id.
     * @return a {@link Result} bearing the representation of the batch or with an error if no batch with this id is found.
     */
    Result<DetailBatchViewModel> getDetailBatchViewModelById(int batchId);

    /**
     * Returns a {@link GoNextStepViewModel} with the informations to choose the next step to go in the batch with
     * specified id.
     * @param batchId the batch id.
     * @return a {@link Result} bearing the informations to choose choose the next step to go.
     */
    Result<GoNextStepViewModel> getGoNextStepViewModel(int batchId);

    /**
     * Goes to the next step of the batch with passed id with informations specified in the dto.
     * @param batchId the batch id.
     * @param dto the dto specifying infos to go to next step.
     * @return a {@link Result} bearing an error if operation went wrong.
     */
    Result<Empty> goToNextStep(int batchId, GoNextStepDTO dto);
}
