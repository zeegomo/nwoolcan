package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.model.utils.Quantity;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.MiscArticleViewModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public abstract class AbstractStockViewModel {

    private final Stock stock;

    /**
     * Constructor of the abstract view version of the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param stock the {@link Stock} to be converted in {@link AbstractStockViewModel}.
     */
    public AbstractStockViewModel(final Stock stock) {
        this.stock = stock;
    }

    /**
     * Return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public final ArticleViewModel getArticle() {
        switch (stock.getArticle().getArticleType()) {
            case INGREDIENT:
                return new IngredientArticleViewModel((IngredientArticle) stock.getArticle());
            case MISC:
                return new MiscArticleViewModel(stock.getArticle());
            case FINISHED_BEER:
            default:
            return new BeerArticleViewModel((BeerArticle) stock.getArticle());
        }
    }
    /**
     * Return the remaining {@link nwoolcan.model.utils.Quantity}.
     * @return the remaining {@link nwoolcan.model.utils.Quantity}.
     */
    public final Quantity getRemainingQuantity() {
        return stock.getRemainingQuantity();
    }
    /**
     * Return the used {@link nwoolcan.model.utils.Quantity}.
     * @return the used {@link nwoolcan.model.utils.Quantity}.
     */
    public final Quantity getUsedQuantity() {
        return stock.getUsedQuantity();
    }
    /**
     * Return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     * @return the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     */
    public final StockState getState() {
        return stock.getState();
    }
    /**
     * Return the expiration {@link java.util.Date}.
     * @return the expiration {@link java.util.Date}.
     */
    public final Optional<Date> getExpirationDate() {
        return stock.getExpirationDate();
    }
    /**
     * Return the {@link List} of {@link RecordViewModel}.
     * @return the {@link List} of {@link RecordViewModel}.
     */
    public final List<RecordViewModel> getRecords() {
        return stock.getRecords().stream().map(RecordViewModel::new).collect(Collectors.toList());
    }
}
