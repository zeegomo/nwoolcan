package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.model.utils.Quantity;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public abstract class AbstractStockViewModel {

    private final int id;

    private final AbstractArticleViewModel article;
    private final Quantity remainingQuantity;
    private final Quantity usedQuantity;
    private final StockState stockState;
    private final Optional<Date> expirationDate;
    private final List<RecordViewModel> records;
    /**
     * Constructor of the abstract view version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stock the {@link Stock} to be converted in {@link AbstractStockViewModel}.
     */
    public AbstractStockViewModel(final Stock stock) {
        this.id = stock.getId();
        this.article = AbstractArticleViewModel.getViewArticle(stock.getArticle());
        this.remainingQuantity = stock.getRemainingQuantity();
        this.usedQuantity = stock.getUsedQuantity();
        this.stockState = stock.getState();
        this.expirationDate = stock.getExpirationDate();
        this.records = stock.getRecords().stream().map(RecordViewModel::new).collect(Collectors.toList());
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
    public final AbstractArticleViewModel getArticle() {
        return article;
    }
    /**
     * Return the remaining {@link nwoolcan.model.utils.Quantity}.
     * @return the remaining {@link nwoolcan.model.utils.Quantity}.
     */
    public final Quantity getRemainingQuantity() {
        return remainingQuantity;
    }
    /**
     * Return the used {@link nwoolcan.model.utils.Quantity}.
     * @return the used {@link nwoolcan.model.utils.Quantity}.
     */
    public final Quantity getUsedQuantity() {
        return usedQuantity;
    }
    /**
     * Return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     * @return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     */
    public final StockState getState() {
        return stockState;
    }
    /**
     * Return the expiration {@link java.util.Date}.
     * @return the expiration {@link java.util.Date}.
     */
    public final Optional<Date> getExpirationDate() {
        return expirationDate;
    }
    /**
     * Return the {@link List} of {@link RecordViewModel}.
     * @return the {@link List} of {@link RecordViewModel}.
     */
    public final List<RecordViewModel> getRecords() {
        return records;
    }
    /**
     * Generated a proper {@link AbstractStockViewModel} from a general {@link Stock} accordingly with the {@link ArticleType} of the {@link nwoolcan.model.brewery.warehouse.article.Article} of the {@link Stock}.
     * @param stock to be converted
     * @return the converted {@link AbstractStockViewModel}.
     */
    public static AbstractStockViewModel getViewStock(final Stock stock) {
        if (stock.getArticle().getArticleType() == ArticleType.FINISHED_BEER) {
            return new BeerStockViewModel((BeerStock) stock);
        }
        return new StockViewModel(stock);
    }
}
