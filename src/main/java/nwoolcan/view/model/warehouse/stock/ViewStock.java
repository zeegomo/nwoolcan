package nwoolcan.view.model.warehouse.stock;

import nwoolcan.view.model.warehouse.article.ViewArticle;
import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.model.utils.Quantity;

import java.util.Date;
import java.util.List;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public final class ViewStock {

    private final ViewArticle article;
    private final Quantity remainingQuantity;
    private final Quantity usedQuantity;
    private final StockState stockState;
    private final Date expirationDate;
    private final List<ViewRecord> records;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param article the {@link ViewArticle} linked to this.
     * @param remainingQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param usedQuantity of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stockState the {@link StockState} of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param expirationDate of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    public ViewStock(final ViewArticle article,
              final Quantity remainingQuantity,
              final Quantity usedQuantity,
              final StockState stockState,
              final Date expirationDate,
              final List<ViewRecord> records) {
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
    public ViewArticle getArticle() {
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
    public Date getExpirationDate() {
        return expirationDate;
    }
    /**
     * Return the {@link List} of {@link ViewRecord}.
     * @return the {@link List} of {@link ViewRecord}.
     */
    public List<ViewRecord> getRecords() {
        return records;
    }
}
