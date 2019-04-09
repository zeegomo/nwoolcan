package nwoolcan.viewmodel.brewery;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.production.batch.QueryBatchBuilder;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * View representation of the {@link nwoolcan.model.brewery.Brewery}.
 */
public final class BreweryViewModel {

    private final Brewery brewery;

    /**
     * Constructor with decorator pattern.
     * @param brewery to be converted to {@link BreweryViewModel}.
     */
    public BreweryViewModel(final Brewery brewery) {
        this.brewery = brewery;
    }
    /**
     * Getter of the name of the {@link Brewery}.
     * @return an {@link Optional} of {@link String} with the name of the {@link Brewery}.
     */
    public Optional<String> getBreweryName() {
        return brewery.getBreweryName();
    }
    /**
     * Getter of the name of the owner of the {@link Brewery}.
     * @return an {@link Optional} of {@link String} with the name of the owner of the {@link Brewery}.
     */
    public Optional<String> getOwnerName() {
        return brewery.getOwnerName();
    }
    /**
     * Getter of the {@link WarehouseViewModel} of the {@link Brewery}.
     * @return the only {@link WarehouseViewModel} of the {@link Brewery}.
     */
    public WarehouseViewModel getWarehouse() {
        return new WarehouseViewModel(brewery.getWarehouse());
    }
    /**
     * Return all the {@link MasterBatchViewModel} of the {@link Brewery}.
     * @return a {@link Collection} of {@link MasterBatchViewModel} of the {@link Brewery}.
     */
    public Collection<MasterBatchViewModel> getAllBatches() {
        final QueryBatch allBatchesQuery = new QueryBatchBuilder().build().getValue();
        return brewery.getBatches(allBatchesQuery)
                      .stream()
                      .map(MasterBatchViewModel::new)
                      .collect(Collectors.toList());
    }


}
