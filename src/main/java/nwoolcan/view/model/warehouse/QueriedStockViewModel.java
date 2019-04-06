package nwoolcan.view.model.warehouse;

import nwoolcan.view.model.warehouse.stock.StockViewModel;

import java.util.List;

/**
 * Model of the view of the list of the stocks.
 */
public final class QueriedStockViewModel {

    private final List<StockViewModel> stocks;

    /**
     * Constructor of the view version of the content of the {@link nwoolcan.model.brewery.warehouse.stock.StockManager} of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param stocks produced by a query.
     */
    public QueriedStockViewModel(final List<StockViewModel> stocks) {
        this.stocks = stocks;
    }

    /**
     * Returns a list of {@link StockViewModel}.
     * @return a list of {@link StockViewModel}.
     */
    public List<StockViewModel> getLastStocks() {
        return stocks;
    }

}
