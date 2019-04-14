package nwoolcan.controller.batch;

import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;

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
}
