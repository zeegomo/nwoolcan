package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.BatchInfo;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Collection;

/**
 * Brewery Implementation.
 */
public final class BreweryImpl implements Brewery {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Warehouse getWarehouse() {
        return null;
    }

    @Override
    public Result<Collection<Batch>> getBatches(final QueryBatch queryBatch) {
        return null;
    }

    @Override
    public Result<Empty> createBatch(final BatchInfo batchInfo) {
        return null;
    }
}
