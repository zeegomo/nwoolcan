package nwoolcan.viewmodel.brewery;

import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;

/**
 * Data to show in the dashboard.
 */
public final class DashboardViewModel {
    private final ProductionViewModel productionViewModel;
    private final WarehouseViewModel warehouseViewModel;
    private final String breweryName;

    /**
     * Creates this view model.
     * @param productionViewModel the production view model to get data from
     * @param breweryName the name of this brewery
     */
    public DashboardViewModel(final ProductionViewModel productionViewModel, final WarehouseViewModel warehouseViewModel, final String breweryName) {
        this.productionViewModel = productionViewModel;
        this.warehouseViewModel = warehouseViewModel;
        this.breweryName = breweryName;
    }

    /**
     * Returns data about the production.
     * @return the production view model
     */
    public ProductionViewModel getProduction() {
        return this.productionViewModel;
    }

    /**
     * Returns data about the warehouse.
     * @return the warehouse view model
     */
    public WarehouseViewModel getWarehouse() {
        return this.warehouseViewModel;
    }

    /**
     * Returns the name of the brewery.
     * @return the name of the brewery.
     */
    public String getBreweryName() {
        return breweryName;
    }
}
