package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.CreateBatchDTO;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.List;

/**
 * Controller.
 */
public interface Controller {
    /**
     * Return the {@link BreweryController}.
     * @return the {@link BreweryController}.
     */
    BreweryController getBreweryController();
    /**
     * Returns the built view model for the view.
     * @return the built view model for the view.
     */
    ProductionViewModel getProductionViewModel();
    /**
     * Returns the queried batches master view model from production.
     * @param query filter for batches.
     * @return the queried batches.
     */
    List<MasterBatchViewModel> getBatches(QueryBatch query);

    Result<Empty> createNewBatch(CreateBatchDTO batchDTO);
}
