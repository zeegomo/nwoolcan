package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.model.utils.Quantity;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticleViewModel;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * View model version of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
 */
public class BeerStockViewModel extends AbstractStockViewModel {

    private final MasterBatchViewModel batch;

    /**
     * Constructor with the elements of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param article of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param remainingQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param usedQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param stockState of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param expirationDate of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param records of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @param batch of the {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     */
    public BeerStockViewModel(final ArticleViewModel article,
                              final Quantity remainingQuantity,
                              final Quantity usedQuantity,
                              final StockState stockState,
                              @Nullable final Date expirationDate,
                              final List<RecordViewModel> records,
                              final MasterBatchViewModel batch) {
        super(article, remainingQuantity, usedQuantity, stockState, expirationDate, records);
        this.batch = batch;
    }

    /**
     * Return the {@link MasterBatchViewModel} related to this {@link BeerStockViewModel}.
     * @return the {@link MasterBatchViewModel} related to this {@link BeerStockViewModel}.
     */
    public final MasterBatchViewModel getBatchViewModel() {
        return batch;
    }
}
