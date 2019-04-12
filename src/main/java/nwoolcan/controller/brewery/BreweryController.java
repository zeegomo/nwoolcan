package nwoolcan.controller.brewery;

import nwoolcan.controller.brewery.warehouse.WarehouseController;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.Collection;
import java.util.Date;

/**
 * General controller of the {@link nwoolcan.model.brewery.Brewery}.
 */
public interface BreweryController {

    /**
     * Return the {@link WarehouseController}.
     * @return the {@link WarehouseController}.
     */
    WarehouseController getWarehouseController();
    /**
     * Return a {@link Collection} of {@link MasterBatchViewModel} of the {@link nwoolcan.model.brewery.Brewery}.
     * @param queryBatch to filter the query.
     * @return a {@link Collection} of {@link MasterBatchViewModel} of the {@link nwoolcan.model.brewery.Brewery}.
     */
    Collection<MasterBatchViewModel> getBatches(QueryBatch queryBatch);
    /**
     * Stocks a {@link nwoolcan.model.brewery.production.batch.Batch}.
     * @param batchId the id of the {@link nwoolcan.model.brewery.production.batch.Batch}.
     * @param beerArticleId the id of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @param expirationDate the expiration {@link Date}.
     * @return a {@link Result} reporting possible errors.
     */
    Result<Empty> stockBatch(int batchId, int beerArticleId, Date expirationDate);
    /**
     * Stocks a {@link nwoolcan.model.brewery.production.batch.Batch} with no expiration {@link Date}.
     * @param batchId the id of the {@link nwoolcan.model.brewery.production.batch.Batch}.
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
}
