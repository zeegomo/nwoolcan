package nwoolcan.controller;

import nwoolcan.controller.batch.BatchController;
import nwoolcan.controller.warehouse.WarehouseController;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.CreateBatchDTO;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.NewBatchViewModel;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * General controller of the {@link nwoolcan.model.brewery.Brewery}.
 */
public interface Controller {

    /**
     * Return the {@link WarehouseController}.
     * @return the {@link WarehouseController}.
     */
    WarehouseController getWarehouseController();
    /**
     * Returns the {@link BatchController}.
     * @return the {@link BatchController}.
     */
    BatchController getBatchController();
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
    /**
     * Stocks a {@link nwoolcan.model.brewery.batch.Batch}.
     * @param batchId the id of the {@link nwoolcan.model.brewery.batch.Batch}.
     * @param beerArticleId the id of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @param expirationDate the expiration {@link Date}.
     * @return a {@link Result} reporting possible errors.
     */
    Result<Empty> stockBatch(int batchId, int beerArticleId, Date expirationDate);
    /**
     * Stocks a {@link nwoolcan.model.brewery.batch.Batch} with no expiration {@link Date}.
     * @param batchId the id of the {@link nwoolcan.model.brewery.batch.Batch}.
     * @param beerArticleId the id of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @return a {@link Result} reporting possible errors.
     */
    Result<Empty> stockBatch(int batchId, int beerArticleId);
    /**
     * Set the name of the {@link nwoolcan.model.brewery.Brewery}.
     * @param breweryName the new name.
     */
    void setBreweryName(String breweryName);
    /**
     * Set the name of the owner of the {@link nwoolcan.model.brewery.Brewery}.
     * @param ownerName the name of the owner.
     */
    void setOwnerName(String ownerName);
    /**
     * Substitutes the current brewery with a new empty one.
     */
    void initializeNewBrewery();
    /**
     * Saves the current state of the Brewery to a file with the given name.
     * @param filename the name of the file to be saved.
     * @return a {@link Result} describing the operation's outcome.
     */
    Result<Empty> saveTo(File filename);
    /**
     * Loads the state of the Brewery from the file with the given name.
     * @param filename the name of the file to load.
     * @return a {@link Result} describing the operation's outcome.
     */
    Result<Empty> loadFrom(File filename);
}
