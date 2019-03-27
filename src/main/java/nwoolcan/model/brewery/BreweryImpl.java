package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.BatchInfo;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.WarehouseImpl;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Collection;

/**
 * Brewery Implementation.
 */
public final class BreweryImpl implements Brewery {

    private final String name;

    public BreweryImpl(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Warehouse getWarehouse() {
        return null;
    }

    @Override
    public Result<Collection<Batch>> getBatches(QueryBatch queryBatch) {
        return null;
    }

    @Override
    public Result<Empty> addBatch(Batch batch) {
        return null;
    }
}
