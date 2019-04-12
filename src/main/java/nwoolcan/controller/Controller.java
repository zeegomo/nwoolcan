package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.CreateBatchDTO;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.NewBatchViewModel;

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
    /**
     * Returns the build view model for the view.
     * @return the build view model for the view.
     */
    NewBatchViewModel getNewBatchViewModel();
    /**
     * Creates a new batch in production with the specified data in the DTO passed by parameter.
     * @param batchDTO the DTO with all the data to create a new batch.
     * @return a {@link Result} with an error if creation failed.
     */
    Result<Empty> createNewBatch(CreateBatchDTO batchDTO);
}
