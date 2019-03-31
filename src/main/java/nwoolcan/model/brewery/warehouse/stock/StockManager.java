package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manager for {@link Stock} objects. It is used by tests and by the
 * {@link nwoolcan.model.brewery.warehouse.Warehouse} in order to create {@link Stock}, check the
 * id of the {@link Stock}, avoid repetitions and set name of the {@link Stock}.
 */
public final class StockManager {

    @Nullable private static StockManager instance;
    private static final String STOCK_WITH_FINISHED_BEER = "You can not create a stock of finished"
                                                         + "beer. Create a BeerStock instead.";
    private final ArticleManager articleManager;
    private final Map<Stock, Stock> stockToStock;
    private int nextAvailableId;

    private StockManager() {
        articleManager = ArticleManager.getInstance();
        nextAvailableId = 1;
        stockToStock = new HashMap<>();
    }
    /**
     * Returns the only instance of the {@link StockManager} using a singleton pattern.
     * @return the only instance of the {@link StockManager} using a singleton pattern.
     */
    public static synchronized StockManager getInstance() {
        if (instance == null) {
            instance = new StockManager();
        }
        return instance;
    }
    /**
     * Checks the consistency of the {@link Stock}.
     * @param stock to be checked.
     * @return a boolean denoting whether the id is correct or not.
     */
    public synchronized boolean checkId(final Stock stock) {
        return stockToStock.containsKey(stock) && stock.getId() == stockToStock.get(stock).getId();
    }
    /**
     * Constructor of the {@link Stock}.
     * @param article linked to the {@link Stock}.
     * @param expirationDate linked to the {@link Stock}.
     * @return a {@link Result} indicating errors.
     */
    public synchronized Result<Stock> createStock(final Article article,
                                     @Nullable final Date expirationDate) {
          return Result.of(article)
                       .require(articleManager::checkId)
                       .require(this::checkNotFinishedBeer, new IllegalArgumentException(STOCK_WITH_FINISHED_BEER))
                       .map(a -> new StockImpl(nextAvailableId, a, expirationDate)) // TODO add the nextAvailableId in the id field.
                       .map(this::getStock);
    }
    /**
     * Constructor of the {@link BeerStock}.
     * @param beerArticle linked to this {@link BeerStock}.
     * @param expirationDate linked to this {@link BeerStock}.
     * @param batch linked to this {@link BeerStock}.
     * @return a {@link Result} indicating errors.
     */
    public synchronized Result<BeerStock> createBeerStock(final BeerArticle beerArticle,
                                  @Nullable final Date expirationDate,
                                  final Batch batch) { // TODO register and require it was not registered yet.
        return Result.of(beerArticle)
                     .require(articleManager::checkId)
                     .map(ba -> new BeerStockImpl(nextAvailableId, ba, expirationDate, batch)) // TODO add the nextAvailableId in the id field.
                     .map(this::getStock)
                     .map(stock -> (BeerStock) stock);
    }

    /**
     * Returns a {@link Set} of the {@link Stock} currently registered at the {@link StockManager}.
     * @return a {@link Set} of the {@link Stock} currently registered at the {@link StockManager}.
     */
    public Set<Stock> getStocks() {
        return new HashSet<>(stockToStock.values());
    }
    /**
     * Return a boolean denoting whether the article is not a {@link BeerArticle}.
     * @param article to be checked.
     * @return a boolean denoting whether the article is not a {@link BeerArticle}.
     */
    private boolean checkNotFinishedBeer(final Article article) {
        return article.getArticleType() != ArticleType.FINISHED_BEER;
    }
    /**
     * It checks whether the {@link Stock} is already in the map. If it is, it returns the one in the map.
     * If it is not, it adds it to the map and returns itself.
     * @param stock to be checked.
     * @return the parameter if it is not in the map. Otherwise the corresponding into the map.
     */
    public synchronized Stock getStock(final Stock stock) {
        if (!stockToStock.containsKey(stock)) {
            nextAvailableId++;
            stockToStock.put(stock, stock);
            return stock;
        }
        return stockToStock.get(stock);
    }

}
