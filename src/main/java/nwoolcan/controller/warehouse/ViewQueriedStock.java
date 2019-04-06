package nwoolcan.controller.warehouse;

import nwoolcan.controller.warehouse.stock.ViewStock;

import java.util.List;

/**
 * Model of the view of the list of the stocks.
 */
public final class ViewQueriedStock {

    private final List<ViewStock> stocks;

    /**
     * Constructor of the view version of the content of the {@link nwoolcan.model.brewery.warehouse.stock.StockManager} of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param stocks produced by a query.
     */
    // Package-private
    ViewQueriedStock(final List<ViewStock> stocks) {
        this.stocks = stocks;
    }

    /**
     * Returns a list of {@link ViewStock}.
     * @return a list of {@link ViewStock}.
     */
    public List<ViewStock> getLastStocks() {
        return stocks;
    }

}
