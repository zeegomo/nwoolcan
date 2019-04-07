package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Brewery.
 */
public interface Brewery {
    /**
     * Getter of the name of the {@link Brewery}.
     * @return an {@link Optional} of {@link String} with the name of the {@link Brewery}.
     */
    Optional<String> getBreweryName();
    /**
     * Getter of the name of the owner of the {@link Brewery}.
     * @return an {@link Optional} of {@link String} with the name of the owner of the {@link Brewery}.
     */
    Optional<String> getOwnerName();
    /**
     * Getter of the only {@link Warehouse} in the {@link Brewery}.
     * @return the only {@link Warehouse} in the {@link Brewery}.
     */
    Warehouse getWarehouse();
    /**
     * Getter of the {@link Collection} of {@link Batch} in the warehouse.
     * @param queryBatch describes the type of the query.
     * @return a {@link Collection} of {@link Batch} accordingly with the given {@link QueryBatch}.
     */
    Collection<Batch> getBatches(QueryBatch queryBatch);
    /**
     * Adds a {@link Batch} to the brewery.
     * @param batch to be added
     */
    void addBatch(Batch batch);
    /**
     * Takes a {@link Batch}, checks to be in the final {@link nwoolcan.model.brewery.production.batch.step.Step} and creates a {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param batch from which create a {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param beerArticle the {@link BeerArticle} related to the new {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param expirationDate the {@link Date} in which this {@link nwoolcan.model.brewery.warehouse.stock.BeerStock} will expire.
     * @return a {@link Result} with an {@link Exception} if the {@link Batch} was not in the final {@link nwoolcan.model.brewery.production.batch.step.Step}.
     */
    Result<Empty> stockBatch(Batch batch, BeerArticle beerArticle, @Nullable Date expirationDate);
    /**
     * Set the name of the {@link Brewery}.
     * @param breweryName the new name.
     */
    void setBreweryName(String breweryName);
    /**
     * Set the name of the owner of the {@link Brewery}.
     * @param ownerName the new name.
     */
    void setOwnerName(String ownerName);
}
