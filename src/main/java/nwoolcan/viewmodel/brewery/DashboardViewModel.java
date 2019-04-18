package nwoolcan.viewmodel.brewery;

import nwoolcan.viewmodel.brewery.production.ProductionViewModel;

/**
 * Data to show in the dashboard.
 */
public final class DashboardViewModel {
    private final ProductionViewModel productionViewModel;
    private final String breweryName;

    /**
     * Creates this view model.
     * @param productionViewModel the production view model to get data from
     * @param breweryName the name of this brewery
     */
    public DashboardViewModel(final ProductionViewModel productionViewModel, final String breweryName) {
        this.productionViewModel = productionViewModel;
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
     * Returns the name of the brewery.
     * @return the name of the brewery.
     */
    public String getBreweryName() {
        return breweryName;
    }
}
