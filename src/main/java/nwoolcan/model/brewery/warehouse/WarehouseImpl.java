package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleIdManager;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.brewery.warehouse.stock.StockImpl;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;


/**
 * Warehouse implementation.
 */
public final class WarehouseImpl implements Warehouse {

    private static final String ARTICLE_ALREADY_REGISTERED = "The article was already registered.";
    private static final String ARTICLE_NOT_REGISTERED = "The article was not registered yet.";
    private static final String ARTICLE_NOT_REGISTERED_AT_ID_MANAGER
                                            = "The article was not registered at the id manager. "
                                            + "Build it with ArticleImpl or its subclass.";
    private final Map<Stock, Stock> stocks = new HashMap<>();
    private final Set<Article> articles = new HashSet<>();
    private static final ArticleIdManager ID_MANAGER = ArticleIdManager.getInstance();


    @Override
    public Result<List<Stock>> getStocks(final QueryStock queryStock) {
        return Result.of(stocks.values()
                               .stream()
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
                               .filter(stock -> !(queryStock.getMinRemainingQuantity().isPresent() //TODO change with the new functionality of Quantity
                                   && stock.getRemainingQuantity()
                                           .getValue()
                                           .doubleValue()
                                   < queryStock.getMinRemainingQuantity()
                                               .get()
                                               .getValue()
                                               .doubleValue()))
                               // remove those where queryStock.maxRemainingQuantity is present and
                               // stock.getremainingQuantitity is more than the one required. The UOM is not
                               // checked because the query has to be consistent. It is checked in
                               // the queryStock builder.
                               .filter(stock -> !(queryStock.getMaxRemainingQuantity().isPresent() //TODO change with the new functionality of Quantity
                                   && stock.getRemainingQuantity()
                                           .getValue()
                                           .doubleValue()
                                   > queryStock.getMaxRemainingQuantity()
                                               .get()
                                               .getValue()
                                               .doubleValue()))
                               // remove those where querystock.minUsedQuantity is present and
                               // stock.getUsedQuantity is less than the one required. The UOM is not
                               // checked because the query has to be consistent. It is checked in
                               // the queryStock builder.
                               .filter(stock -> !(queryStock.getMinUsedQuantity().isPresent() //TODO change with the new functionality of Quantity
                                   && stock.getUsedQuantity()
                                           .getValue()
                                           .doubleValue()
                                   < queryStock.getMinUsedQuantity()
                                               .get()
                                               .getValue()
                                               .doubleValue()))
                               // remove those where queryStock.maxUsedQuantity is present and
                               // stock.getUsedQuantitity is more than the one required. The UOM is not
                               // checked because the query has to be consistent. It is checked in
                               // the queryStock builder.
                               .filter(stock -> !(queryStock.getMaxUsedQuantity().isPresent() //TODO change with the new functionality of Quantity
                                   && stock.getRemainingQuantity()
                                           .getValue()
                                           .doubleValue()
                                   > queryStock.getMaxUsedQuantity()
                                               .get()
                                               .getValue()
                                               .doubleValue()))
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
                               .collect(Collectors.toList()));
    }

    @Override
    public Result<List<Article>> getArticles(final QueryArticle queryArticle) {
        return Result.of(articles.stream()
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
                                     && article.getName().compareTo(queryArticle.getMinName().get()) < 0))
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
                                 .collect(Collectors.toList()));
    }

    @Override
    public Result<Empty> addArticle(final Article newArticle) {
        return Result.of(newArticle)
                     .require(this::requireArticleNotRegistered,
                                new IllegalArgumentException(ARTICLE_ALREADY_REGISTERED))
                     .flatMap(this::updateArticles)
                     .toEmpty();
    }

    @Override
    public Result<Empty> addRecord(final Article article,
                                   @Nullable final Date expirationDate,
                                   final Record record) {
        return Result.of(new StockImpl(article, expirationDate))
                     .map(this::getStock)
                     .require(() -> !requireArticleNotRegistered(article),
                         new IllegalArgumentException(ARTICLE_NOT_REGISTERED))
                     .flatMap(stock -> stock.addRecord(record))
                     .toEmpty();
    }

    @Override
    public Result<Empty> addRecord(final Article article, final Record record) {
        return addRecord(article, null, record);
    }
    /**
     * Adds an {@link Article} to the {@link Set} of articles if not present yet.
     * @param article the article to be added.
     * @return a {@link Result} reporting whether errors occurred.
     */
    private Result<Article> updateArticles(final Article article) {
        return Result.of(article)
                     .require(ID_MANAGER::checkId,
                                    new IllegalArgumentException(ARTICLE_NOT_REGISTERED_AT_ID_MANAGER))
                     .peek(articles::add);
    }

    /**
     * Return a boolean which specifies whether the article is already registered or not.
     * @param article to check.
     * @return a boolean which specifies whether the article is already registered or not.
     */
    private boolean requireArticleNotRegistered(final Article article) {
        return !this.articles.contains(article);
    }
    /**
     * Given a {@link Stock}, if present it will return the {@link Stock} present in the
     * {@link Warehouse}, otherwise it adds it to the {@link Warehouse}.
     * @param stock to check and return.
     * @return the {@link Stock} in the {@link Warehouse} if present, otherwise it returns the given
     * {@link Stock} adding it to the {@link Warehouse}.
     */
    private Stock getStock(final Stock stock) {
        if (!this.stocks.containsKey(stock)) {
            this.stocks.put(stock, stock);
        }
        return this.stocks.get(stock);
    }
    /**
     * temp shitty method to be removed once the official is ready.
     * @param q1 .
     * @param q2 .
     * @return .
     */
    private Integer tempCmpQt(final Quantity q1, final Quantity q2) { // it is shit on purpose so that I will remember to change it.
        //return q1.getValue().doubleValue() == q2.getValue().doubleValue() ? 0 : q1.getValue().doubleValue() > q2.getValue().doubleValue() ? 1 : -1;
        return Double.compare(q1.getValue().doubleValue(), q2.getValue().doubleValue()); //TODO remove
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
    private Integer compareBy(final Stock s1,
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
                return des * tempCmpQt(s1.getRemainingQuantity(), s2.getRemainingQuantity()); //TODO change
            case USED_QUANTITY:
                return des * tempCmpQt(s1.getUsedQuantity(), s2.getUsedQuantity()); //TODO change
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
    private Integer compareBy(final Article a1,
                              final Article a2,
                              final QueryArticle.SortParameter by,
                              final boolean descending) {
        final int des = descending ? -1 : 1;
        switch (by) {
            case NAME:
                return des * a1.getName().compareTo(a2.getName());
            case ID:
                return des * a1.getId().compareTo(a2.getId());
            case NONE:
            default:
                return 0;
        }
    }

}
