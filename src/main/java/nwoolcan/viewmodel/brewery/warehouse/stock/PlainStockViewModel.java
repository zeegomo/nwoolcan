package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.Stock;

/**
 * View model version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public class PlainStockViewModel extends DetailStockViewModel {

    /**
     * Constructor with the elements of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stock the {@link Stock} to be converted in {@link PlainStockViewModel}.
     */
    public PlainStockViewModel(final Stock stock) {
        super(stock);
    }
}
