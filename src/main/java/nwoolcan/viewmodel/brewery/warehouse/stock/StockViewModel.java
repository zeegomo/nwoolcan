package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.model.utils.Quantity;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticleViewModel;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public final class StockViewModel {

    private final ArticleViewModel article;
    private final Quantity remainingQuantity;
    private final Quantity usedQuantity;
    private final StockState stockState;
    @Nullable
    private final Date expirationDate;
    private final List<RecordViewModel> records;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param article the {@link ArticleViewModel} linked to this.
     * @param remainingQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param usedQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stockState the {@link StockState} of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param expirationDate of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param records of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    public StockViewModel(final ArticleViewModel article,
                          final Quantity remainingQuantity,
                          final Quantity usedQuantity,
                          final StockState stockState,
                          @Nullable final Date expirationDate,
                          final List<RecordViewModel> records) {
        this.article = article;
        this.remainingQuantity = remainingQuantity;
        this.usedQuantity = usedQuantity;
        this.stockState = stockState;
        this.expirationDate = expirationDate;
        this.records = records;
    }

    /**
     * Return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public ArticleViewModel getArticle() {
        return article;
    }
    /**
     * Return the remaining {@link nwoolcan.model.utils.Quantity}.
     * @return the remaining {@link nwoolcan.model.utils.Quantity}.
     */
    public Quantity getRemainingQuantity() {
        return remainingQuantity;
    }
    /**
     * Return the used {@link nwoolcan.model.utils.Quantity}.
     * @return the used {@link nwoolcan.model.utils.Quantity}.
     */
    public Quantity getUsedQuantity() {
        return usedQuantity;
    }
    /**
     * Return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     * @return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     */
    public StockState getState() {
        return stockState;
    }
    /**
     * Return the expiration {@link java.util.Date}.
     * @return the expiration {@link java.util.Date}.
     */
    public Optional<Date> getExpirationDate() {
        return Optional.ofNullable(expirationDate);
    }
    /**
     * Return the {@link List} of {@link RecordViewModel}.
     * @return the {@link List} of {@link RecordViewModel}.
     */
    public List<RecordViewModel> getRecords() {
        return records;
    }
}
