package nwoolcan.viewmodel.brewery;

import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;

/**
 * Data to show in the dashboard.
 */
public final class DashboardViewModel {
    private final ProductionViewModel productionViewModel;
    private final WarehouseViewModel warehouseViewModel;
    private final int expiringSoon;
    private final String breweryName;
    private final String breweryOwner;

    /**
     * Creates this view model.
     * @param productionViewModel the production view model to get data from
     * @param warehouseViewModel the warehouse view model to get data from
     * @param expiringSoon number of stocks expiring soon
     * @param breweryName the name of this brewery
     * @param breweryOwner the name of the owner of the brewery
     */
    public DashboardViewModel(final ProductionViewModel productionViewModel, final WarehouseViewModel warehouseViewModel, final int expiringSoon, final String breweryName, final String breweryOwner) {
        this.productionViewModel = productionViewModel;
        this.warehouseViewModel = warehouseViewModel;
        this.expiringSoon = expiringSoon;
        this.breweryName = breweryName;
        this.breweryOwner = breweryOwner;
    }

    /**
     * Returns the total number of batches.
     * @return the total number of batches.
     */
    public long getNBatches() {
        return this.productionViewModel.getNBatches();
    }

    /**
     * Returns the number of batches that are still in progress.
     * @return the number of batches that are still in progress.
     */
    public long getInProgressBatches() {
        return this.productionViewModel.getNInProgressBatches();
    }

    /**
     * Returns the number of ended batches.
     * @return the number of ended batches.
     */
    public long getEndedBatches() {
        return this.productionViewModel.getNEndedBatches();
    }

    /**
     * Returns the number of stocked batches.
     * @return the number of stocked batches.
     */
    public long getStockedBatches() {
        return this.productionViewModel.getNStockedBatches();
    }

    /**
     * Returns the number of ended batches, but not stocked.
     * @return the number of ended batches, but not stocked.
     */
    public long getEndedNotStockedBatches() {
        return this.productionViewModel.getNEndedNotStockedBatches();
    }

    /**
     * Return the number of available {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @return the number of available {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     */
    public int getBeerAvailable() {
        return this.warehouseViewModel.getnBeerAvailable();
    }

    /**
     * Return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     */
    public int getIngredientAvailable() {
        return this.warehouseViewModel.getnIngredientAvailable();
    }

    /**
     * Return the number of available {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the number of available {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public int getMiscAvailable() {
        return this.warehouseViewModel.getnMiscAvailable();
    }

    /**
     * Return the total number of available stocks.
     * @return the total number of available stocks.
     */
    public int getTotalAvailable() {
        return this.getBeerAvailable() + this.getIngredientAvailable() + this.getMiscAvailable();
    }

    /**
     * Returns the number of stocks expiring in a short period.
     * @return the number of stocks expiring in a short period.
     */
    public int getExpiringSoonStocks() {
        return this.expiringSoon;
    }

    /**
     * Returns the name of the brewery.
     * @return the name of the brewery.
     */
    public String getBreweryName() {
        return breweryName;
    }


    /**
     * Returns the name of the owner of the brewery.
     * @return the name of the owner of the brewery.
     */
    public String getBreweryOwner() {
        return breweryOwner;
    }

}
