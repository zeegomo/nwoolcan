package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.brewery.warehouse.stock.StockManager;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Warehouse implementation.
 */
public final class WarehouseImpl implements Warehouse {

    private static final ArticleManager ARTICLE_MANAGER = ArticleManager.getInstance();
    private static final StockManager STOCK_MANAGER = StockManager.getInstance();

    @Override
    public List<Stock> getStocks(final QueryStock queryStock) {
        final Set<Stock> stocks = STOCK_MANAGER.getStocks();
        return stocks.stream()
                     // remove when article is present in queryStock but the article of
                     // the current stock is different from the one of the query.
                     .filter(stock -> !(queryStock.getArticle().isPresent()
                         && !queryStock.getArticle().get().equals(stock.getArticle())))
                     // remove those without expiration date if expiresBefore is present.
                     .filter(stock -> !(queryStock.getExpiresBefore().isPresent()
                         && !stock.getExpirationDate().isPresent()))
                     // remove those with expiration date after upper limit of
                     // expiration date
                     .filter(stock -> !(queryStock.getExpiresBefore().isPresent()
                                     && stock.getExpirationDate().get()
                                             .after(queryStock.getExpiresBefore().get())))
                     // remove those with expiration date before the lower limit
                     // Stocks with no expiration date are left, since it is like they
                     // won't ever expire.
                     .filter(stock -> !(queryStock.getExpiresAfter().isPresent()
                                     && stock.getExpirationDate().isPresent()
                                     && stock.getExpirationDate().get()
                                             .before(queryStock.getExpiresAfter().get())))
                     // remove those where querystock.minRemainingQuantity is present and
                     // stock.getremainingquantity is less than the one required. The UOM is not
                     // checked because the query has to be consistent. It is checked in
                     // the queryStock builder.
                     .filter(stock -> !(queryStock.getMinRemainingQuantity().isPresent()
                         && stock.getRemainingQuantity().lessThan(queryStock.getMinRemainingQuantity().get())))
                     // remove those where queryStock.maxRemainingQuantity is present and
                     // stock.getremainingQuantitity is more than the one required. The UOM is not
                     // checked because the query has to be consistent. It is checked in
                     // the queryStock builder.
                     .filter(stock -> !(queryStock.getMaxRemainingQuantity().isPresent()
                         && stock.getRemainingQuantity().moreThan(queryStock.getMaxRemainingQuantity().get())))
                     // remove those where querystock.minUsedQuantity is present and
                     // stock.getUsedQuantity is less than the one required. The UOM is not
                     // checked because the query has to be consistent. It is checked in
                     // the queryStock builder.
                     .filter(stock -> !(queryStock.getMinUsedQuantity().isPresent()
                         && stock.getUsedQuantity().lessThan(queryStock.getMinUsedQuantity().get())))
                     // remove those where queryStock.maxUsedQuantity is present and
                     // stock.getUsedQuantitity is more than the one required. The UOM is not
                     // checked because the query has to be consistent. It is checked in
                     // the queryStock builder.
                     .filter(stock -> !(queryStock.getMaxUsedQuantity().isPresent()
                         && stock.getRemainingQuantity().moreThan(queryStock.getMaxUsedQuantity().get())))
                     // remove those stock which state is not the one to be included.
                     .filter(stock -> !(queryStock.getIncludeStockState().isPresent()
                                     && !stock.getState()
                                              .equals(queryStock.getIncludeStockState()
                                                                .get())))
                     // remove those stock which state is the one to be excluded
                     .filter(stock -> !(queryStock.getExcludeStockState().isPresent()
                                     && stock.getState()
                                             .equals(queryStock.getExcludeStockState()
                                                               .get())))
                     .sorted((s1, s2) -> compareBy(s1,
                                                   s2,
                                                   queryStock.getSortBy(),
                                                   queryStock.isSortDescending()))
                     .collect(Collectors.toList());
    }

    @Override
    public List<Article> getArticles(final QueryArticle queryArticle) {
        final Set<Article> articles = ARTICLE_MANAGER.getArticles();
        return articles.stream()
                       // remove those article where query article specifies a min ID and
                       // where the id of the article is less than it.
                       .filter(article -> !(queryArticle.getMinID().isPresent()
                            && article.getId() < queryArticle.getMinID().get()))
                       // remove those article where query article specifies a max ID and
                       // where the id of the article is greater than it.
                       .filter(article -> !(queryArticle.getMaxID().isPresent()
                            && article.getId() > queryArticle.getMaxID().get()))
                       // remove those article where query article specifies the first
                       // lexicographical name and where the name of the article is
                       // lexicographically before it.
                       .filter(article -> !(queryArticle.getMinName().isPresent()
                            && article.getName().compareTo(queryArticle.getMinName().get()) > 0))
                       // remove those article where query article specifies the last
                       // lexicographical name and where the name of the article is
                       // lexicographically after it.
                       .filter(article -> !(queryArticle.getMaxName().isPresent()
                            && article.getName().compareTo(queryArticle.getMaxName().get()) < 0))
                       // remove those article which type is not the one to be included.
                       .filter(article -> !(queryArticle.getIncludeArticleType().isPresent()
                           && !article.getArticleType()
                                      .equals(queryArticle.getIncludeArticleType()
                                      .get())))
                       // remove those article which type is to be excluded.
                       .filter(article -> !(queryArticle.getExcludeArticleType().isPresent()
                            && article.getArticleType()
                                      .equals(queryArticle.getExcludeArticleType()
                                      .get())))
                       .sorted((a1, a2) -> compareBy(a1,
                                                     a2,
                                                     queryArticle.getSortBy(),
                                                     queryArticle.getSortDescending()))
                       .collect(Collectors.toList());
    }

    @Override
    public Result<Empty> addRecord(final Stock stock,
                                   final Record record) {
        return Result.of(stock)
                     .require(() -> record.getQuantity()
                                          .getUnitOfMeasure()
                                          .equals(stock.getArticle()
                                                       .getUnitOfMeasure()))
                     .require(STOCK_MANAGER::checkId)
                     .map(STOCK_MANAGER::getStock)
                     .flatMap(stock1 -> stock.addRecord(record));
    }

    @Override
    public Article createMiscArticle(final String name, final UnitOfMeasure unitOfMeasure) {
        return ARTICLE_MANAGER.createMiscArticle(name, unitOfMeasure);
    }

    @Override
    public BeerArticle createBeerArticle(final String name, final UnitOfMeasure unitOfMeasure) {
        return ARTICLE_MANAGER.createBeerArticle(name, unitOfMeasure);
    }

    @Override
    public IngredientArticle createIngredientArticle(final String name,
                                                     final UnitOfMeasure unitOfMeasure,
                                                     final IngredientType ingredientType) {
        return ARTICLE_MANAGER.createIngredientArticle(name, unitOfMeasure, ingredientType);
    }

    @Override
    public Result<Stock> createStock(final Article article, final Date expirationDate) {
        return STOCK_MANAGER.createStock(article, expirationDate);
    }

    @Override
    public Result<Stock> createStock(final Article article) {
        return STOCK_MANAGER.createStock(article, null);
    }

    @Override
    public Result<BeerStock> createBeerStock(final BeerArticle beerArticle,
                                             @Nullable final Date expirationDate,
                                             final Batch batch) {
        return STOCK_MANAGER.createBeerStock(beerArticle, expirationDate, batch);
    }

    @Override
    public Result<Article> setName(final Article article, final String newName) {
        return ARTICLE_MANAGER.setName(article, newName);
    }
    /**
     * Comparator which selects the correct comparator accordingly with the
     * {@link QueryStock.SortParameter} and returns its return value.
     * @param s1 the first {@link Stock} to compare.
     * @param s2 the second {@link Stock} to compare.
     * @param by the parameter used to compare the two {@link Stock}.
     * @param descending defines the order of the sorting. If true returns the value returned by
     *                   the comparator multiplied by -1.
     * @return the return value of the selected comparator. An {@link Integer} less than 0 if the
     * first element is less than the second, 0 if the two elements are equal or an {@link Integer}
     * greater than 0 if the first element is greater than the second.
     */
    private int compareBy(final Stock s1,
                          final Stock s2,
                          final QueryStock.SortParameter by,
                          final boolean descending) {
        final int des = descending ? -1 : 1;
        switch (by) {
            case ARTICLE_NAME:
                return compareBy(s1.getArticle(),
                                 s2.getArticle(),
                                 QueryArticle.SortParameter.NAME,
                                 descending);
            case EXPIRATION_DATE:
                if (!s1.getExpirationDate().isPresent()) {
                    return des; // If the first doesn't have the expiration date,
                }                // it is considered to be greater.
                if (!s2.getExpirationDate().isPresent()) {
                    return -des; // If the second doesn't have the expiration date,
                }                // the first is considered to be smaller.
                return des * s1.getExpirationDate().get().compareTo(s2.getExpirationDate().get());
            case REMAINING_QUANTITY:
                return des * s1.getRemainingQuantity().compareTo(s2.getRemainingQuantity());
            case USED_QUANTITY:
                return des * s1.getUsedQuantity().compareTo(s2.getUsedQuantity());
            case NONE:
            default:
                return 0;
        }
    }
    /**
     * Comparator which selects the correct comparator accordingly with the
     * {@link QueryArticle.SortParameter} and returns its return value.
     * @param a1 the first {@link Article} to compare.
     * @param a2 the second {@link Article} to compare.
     * @param by the {@link QueryArticle.SortParameter} used to compare the two {@link Article}.
     * @param descending defines the order of the sorting. If true returns the value returned by
     *                   the comparator multiplied by -1.
     * @return the return value of the selected comparator. An {@link Integer} less than 0 if the
     * first element is less than the second, 0 if the two elements are equal or an {@link Integer}
     * greater than 0 if the first element is greater than the second.
     */
    private int compareBy(final Article a1,
                              final Article a2,
                              final QueryArticle.SortParameter by,
                              final boolean descending) {
        final int des = descending ? -1 : 1;
        switch (by) {
            case NAME:
                return des * a1.getName().compareTo(a2.getName());
            case ID:
                return des * Integer.compare(a1.getId(), a2.getId());
            case NONE:
            default:
                return 0;
        }
    }

}
