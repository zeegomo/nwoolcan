package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.model.utils.Quantity;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticleViewModel;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * View model version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public class StockViewModel extends AbstractStockViewModel {

    /**
     * Constructor with the elements of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param article of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param remainingQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param usedQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stockState of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param expirationDate of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param records of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    public StockViewModel(final ArticleViewModel article,
                                  final Quantity remainingQuantity,
                                  final Quantity usedQuantity,
                                  final StockState stockState,
                                  @Nullable final Date expirationDate,
                                  final List<RecordViewModel> records) {
        super(article, remainingQuantity, usedQuantity, stockState, expirationDate, records);
    }
}
