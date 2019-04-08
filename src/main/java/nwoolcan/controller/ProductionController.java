package nwoolcan.controller;

import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.List;

/**
 * Interface representing the production controller.
 */
public interface ProductionController {

    /**
     * Returns the built view model for the view.
     * @return the built view model for the view.
     */
    ProductionViewModel getViewModel();

    /**
     * Returns the queried batches master view model from production.
     * @param query filter for batches.
     * @return the queried batches.
     */
    List<MasterBatchViewModel> getBatches(QueryBatch query);
}
