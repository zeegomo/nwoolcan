package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Optional;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}, without explicit records.
 */
public abstract class MasterStockViewModel {

    private final int id;
    private final int articleId;
    private final QuantityViewModel remainingQuantity;
    private final QuantityViewModel usedQuantity;
    private final StockState stockState;
    private final Optional<Date> expirationDate;
    /**
     * Constructor of the abstract view version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stock the {@link Stock} to be converted in {@link MasterStockViewModel}.
     */
    public MasterStockViewModel(final Stock stock) {
        this.id = stock.getId();
        this.articleId = stock.getArticleId();
        this.remainingQuantity = new QuantityViewModel(stock.getRemainingQuantity());
        this.usedQuantity = new QuantityViewModel(stock.getUsedQuantity());
        this.stockState = stock.getState();
        this.expirationDate = stock.getExpirationDate();
    }
    /**
     * Return the id of the {@link Stock}.
     * @return the id of the {@link Stock}.
     */
    public int getId() {
        return id;
    }
    /**
     * Return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public final int getArticleId() {
        return articleId;
    }
    /**
     * Return the remaining {@link nwoolcan.model.utils.Quantity}.
     * @return the remaining {@link nwoolcan.model.utils.Quantity}.
     */
    public final QuantityViewModel getRemainingQuantity() {
        return remainingQuantity;
    }
    /**
     * Return the used {@link nwoolcan.model.utils.Quantity}.
     * @return the used {@link nwoolcan.model.utils.Quantity}.
     */
    public final QuantityViewModel getUsedQuantity() {
        return usedQuantity;
    }
    /**
     * Return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     * @return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     */
    public final StockState getStockState() {
        return stockState;
    }
    /**
     * Return the expiration {@link java.util.Date}.
     * @return the expiration {@link java.util.Date}.
     */
    public final String getExpirationDate() {
        return expirationDate.isPresent() ? dateFormatted(expirationDate.get()) : "";
    }
    /**
     * Generated a proper {@link MasterStockViewModel} from a general {@link Stock} accordingly with the {@link nwoolcan.model.brewery.warehouse.article.ArticleType} of the {@link nwoolcan.model.brewery.warehouse.article.Article} of the {@link Stock}.
     * @param stock to be converted
     * @return the converted {@link MasterStockViewModel}.
     */
    public static MasterStockViewModel getMasterViewStock(final Stock stock) {
        if (stock instanceof BeerStock) {
            return new BeerStockViewModel((BeerStock) stock);
        }
        return new PlainStockViewModel(stock);
    }

    static String dateFormatted(final Date date) {
        return DateFormatUtils.format(date, "dd-MM-yyyy");
    }
}
