package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Collection;

/**
 * Brewery.
 */
public interface Brewery {
    /**
     * Getter of the name of the {@link Brewery}.
     * @return a {@link String} with the name of the {@link Brewery}.
     */
    String getBreweryName();
    /**
     * Getter of the name of the owner of the {@link Brewery}.
     * @return a {@link String} with the name of the owner of the {@link Brewery}.
     */
    String getOwnerName();
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
    Result<Collection<Batch>> getBatches(QueryBatch queryBatch);
    /**
     * Adds a Batch to the brewery.
     * @param batch to be added
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> addBatch(Batch batch);
}
