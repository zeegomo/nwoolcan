package nwoolcan.viewmodel.brewery;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * View representation of the {@link nwoolcan.model.brewery.Brewery}.
 */
public final class BreweryViewModel {

    private final Optional<String> breweryName;
    private final Optional<String> ownerName;
    private final WarehouseViewModel warehouse;
    private final Collection<MasterBatchViewModel> batches;

    /**
     * Constructor with decorator pattern.
     * @param brewery to be converted to {@link BreweryViewModel}.
     */
    public BreweryViewModel(final Brewery brewery) {
        this.breweryName = brewery.getBreweryName();
        this.ownerName = brewery.getOwnerName();
        this.warehouse = new WarehouseViewModel(brewery.getWarehouse());
        final QueryBatch allBatchesQuery = new QueryBatchBuilder().build().getValue();
        this.batches = brewery.getBatches(allBatchesQuery)
                              .stream()
                              .map(MasterBatchViewModel::new)
                              .collect(Collectors.toList());
    }
    /**
     * Getter of the name of the {@link Brewery}.
     * @return an {@link Optional} of {@link String} with the name of the {@link Brewery}.
     */
    public Optional<String> getBreweryName() {
        return breweryName;
    }
    /**
     * Getter of the name of the owner of the {@link Brewery}.
     * @return an {@link Optional} of {@link String} with the name of the owner of the {@link Brewery}.
     */
    public Optional<String> getOwnerName() {
        return ownerName;
    }
    /**
     * Getter of the {@link WarehouseViewModel} of the {@link Brewery}.
     * @return the only {@link WarehouseViewModel} of the {@link Brewery}.
     */
    public WarehouseViewModel getWarehouse() {
        return warehouse;
    }
    /**
     * Return all the {@link MasterBatchViewModel} of the {@link Brewery}.
     * @return a {@link Collection} of {@link MasterBatchViewModel} of the {@link Brewery}.
     */
    public Collection<MasterBatchViewModel> getAllBatches() {
        return batches;
    }
}
