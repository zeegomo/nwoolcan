package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;

/**
 * View model version of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
 */
public class BeerStockViewModel extends DetailStockViewModel {

    private final Integer batchId;

    /**
     * Constructor with the elements of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param beerStock to be converted in {@link nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel}.
     * @param article related to the stock.
     */
    public BeerStockViewModel(final BeerStock beerStock, final Article article) {
        super(beerStock, article);
        this.batchId = beerStock.getBatchId();
    }
    /**
     * Return the id of the {@link nwoolcan.model.brewery.batch.Batch} related to this {@link BeerStockViewModel}.
     * @return the id of the {@link nwoolcan.model.brewery.batch.Batch} related to this {@link BeerStockViewModel}.
     */
    public final Integer getBatchId() {
        return batchId;
    }
}
