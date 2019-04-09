package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

/**
 * View model version of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
 */
public class BeerStockViewModel extends AbstractStockViewModel {

    private final BeerStock beerStock;

    /**
     * Constructor with the elements of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param beerStock to be converted in {@link nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel}.
     */
    public BeerStockViewModel(final BeerStock beerStock) {
        super(beerStock);
        this.beerStock = beerStock;
    }

    /**
     * Return the {@link MasterBatchViewModel} related to this {@link BeerStockViewModel}.
     * @return the {@link MasterBatchViewModel} related to this {@link BeerStockViewModel}.
     */
    public final MasterBatchViewModel getBatchViewModel() {
        return new MasterBatchViewModel(beerStock.getBatch());
    }
}
