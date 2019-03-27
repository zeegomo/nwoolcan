package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.WarehouseImpl;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Brewery Implementation.
 */
public final class BreweryImpl implements Brewery {

    private final String breweryName;
    private final String ownerName;
    private final Warehouse warehouse = new WarehouseImpl();
    private final Collection<Batch> batches = new ArrayList<>();
    // private final BatchIdManager ID_MANAGER = BatchIdManager.getInstance();

    /**
     * Constructor of an Implementation of the {@link Brewery}.
     * @param breweryName the name of the {@link Brewery}.
     * @param ownerName the name of the owner.
     */
    public BreweryImpl(final String breweryName, final String ownerName) {
        this.breweryName = breweryName;
        this.ownerName = ownerName;
    }

    @Override
    public String getBreweryName() {
        return breweryName;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public Warehouse getWarehouse() {
        return warehouse;
    }

    @Override
    public Result<Collection<Batch>> getBatches(final QueryBatch queryBatch) {
        return Result.of(batches); // TODO filters will be added once QueryBatch will be implemented.
    }

    @Override
    public Result<Empty> addBatch(final Batch newBatch) {
        return Result.of(newBatch)
                     // TODO it will be added once the BatchImpl and BatchIdManager will be finished.
                     //.require(ID_MANAGER::checkId)
                     .peek(batches::add)
                     .toEmpty();
    }
}
