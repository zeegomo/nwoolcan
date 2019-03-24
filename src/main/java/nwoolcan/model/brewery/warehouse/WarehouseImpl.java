package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;
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
    private final Map<Stock, Stock> stocks;
    private final Set<Article> articles;

    /**
     * Constructor of the Warehouse.
     */
    // Package protected
    WarehouseImpl() {
        this.stocks = new HashMap<>();
        this.articles = new HashSet<>();
    }

    @Override
    public Result<Set<Stock>> getStocks(final QueryStock queryStock) {
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
                               .collect(Collectors.toSet()));
    }

    @Override
    public Result<Set<Article>> getArticles(final QueryArticle queryArticle) {
        return Result.of(articles); //TODO once queryArticle will be implemented.
    }

    @Override
    public Result<Empty> addArticle(final Article newArticle) {
        return Result.ofEmpty()
                     .require(() -> !this.articles.contains(newArticle),
                                    new IllegalArgumentException(ARTICLE_ALREADY_REGISTERED))
                     .peek(res -> updateArticles(newArticle));
    }

    @Override
    public Result<Empty> addRecord(final Article article,
                                   @Nullable final Date expirationDate,
                                   final Record record) {
        return Result.of(new StockImpl(article, expirationDate))
                     .peek(stock -> updateArticles(article))
                     .flatMap(stock -> Result.of(this.getStock(stock)))
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
     */
    private void updateArticles(final Article article) {
        if (!this.articles.contains(article)) {
            this.articles.add(article);
        }
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

}
