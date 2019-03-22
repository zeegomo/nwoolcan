package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.utils.Quantity;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

/**
 * Defines the parameters of the query on the {@link Stock} to be performed. For each getter method,
 * if the parameter is not defined, an empty {@link Optional} is returned.
 */
public final class QueryStock {

    /**
     * Enum which denotes the field on which the sort will be based.
     */
    public enum OrderParameter {
        /**
         * No order.
         */
        NONE,
        /**
         * Order by the {@link Article} name.
         */
        ARTICLE_NAME,
        /**
         * Order by the expiration {@link Date} of the {@link Stock}.
         */
        EXPIRATION_DATE,
        /**
         * Order by the remaining {@link Quantity} of the {@link Stock}.
         */
        REMAINING_QUANTITY,
        /**
         * Order by the used {@link Quantity} of the {@link Stock}.
         */
        USED_QUANTITY
    }

    @Nullable
    private final Article article;
    @Nullable
    private final Date expiresBefore;
    @Nullable
    private final Date expiresAfter;
    @Nullable
    private final Quantity minRemainingQuantity;
    @Nullable
    private final Quantity maxRemainingQuantity;
    @Nullable
    private final Quantity minUsedQuantity;
    @Nullable
    private final Quantity maxUsedQuantity;
    @Nullable
    private final StockState stockStateIncluded;
    @Nullable
    private final StockState stockStateExcluded;
    @Nullable
    private final OrderParameter orderParameter;
    private final boolean orderDecreasingly;

    // Package private
    QueryStock(@Nullable final Article article,
               @Nullable final Date expiresBefore,
               @Nullable final Date expiresAfter,
               @Nullable final Quantity minRemainingQuantity,
               @Nullable final Quantity maxRemainingQuantity,
               @Nullable final Quantity minUsedQuantity,
               @Nullable final Quantity maxUsedQuantity,
               @Nullable final StockState stockStateIncluded,
               @Nullable final StockState stockStateExcluded,
               @Nullable final OrderParameter orderParameter,
               final boolean orderDecreasingly) {
        this.article = article;
        this.expiresBefore = expiresBefore;
        this.expiresAfter = expiresAfter;
        this.minRemainingQuantity = minRemainingQuantity;
        this.maxRemainingQuantity = maxRemainingQuantity;
        this.minUsedQuantity = minUsedQuantity;
        this.maxUsedQuantity = maxUsedQuantity;
        this.stockStateIncluded = stockStateIncluded;
        this.stockStateExcluded = stockStateExcluded;
        this.orderParameter = orderParameter;
        this.orderDecreasingly = orderDecreasingly;
    }
    /**
     * Return the specific {@link Article} required by the query.
     * @return the specific {@link Article} required by the query.
     */
    public Optional<Article> getArticle() {
        return Optional.ofNullable(article);
    }
    /**
     * Return the latest {@link Date} (included) which has to be considered in the query.
     * @return the latest {@link Date} (included) which has to be considered in the query.
     */
    public Optional<Date> getExpiresBefore() {
        return Optional.ofNullable(expiresBefore);
    }
    /**
     * Return the earliest {@link Date} (included) which has to be considered in the query.
     * @return the earliest {@link Date} (included) which has to be considered in the query.
     */
    public Optional<Date> getExpiresAfter() {
        return Optional.ofNullable(expiresAfter);
    }
    /**
     * Return the minimum remaining {@link Quantity} which has to be considered in the query.
     * @return the minimum remaining {@link Quantity} which has to be considered in the query.
     */
    public Optional<Quantity> getMinRemainingQuantity() {
        return Optional.ofNullable(minRemainingQuantity);
    }
    /**
     * Return the maximum remaining {@link Quantity} which has to be considered in the query.
     * @return the maximum remaining {@link Quantity} which has to be considered in the query.
     */
    public Optional<Quantity> getMaxRemainingQuantity() {
        return Optional.ofNullable(maxRemainingQuantity);
    }
    /**
     * Return the minimum used {@link Quantity} which has to be considered in the query.
     * @return the minimum used {@link Quantity} which has to be considered in the query.
     */
    public Optional<Quantity> getMinUsedQuantity() {
        return Optional.ofNullable(minUsedQuantity);
    }
    /**
     * Return the maximum used {@link Quantity} which has to be considered in the query.
     * @return the maximum used {@link Quantity} which has to be considered in the query.
     */
    public Optional<Quantity> getMaxUsedQuantity() {
        return Optional.ofNullable(maxUsedQuantity);
    }
    /**
     * Return the only {@link StockState} which has to be considered in the query.
     * @return the only {@link StockState} which has to be considered in the query.
     */
    public Optional<StockState> getIncludeStockState() {
        return Optional.ofNullable(stockStateIncluded);
    }
    /**
     * Return the only {@link StockState} which has not to be considered in the query.
     * @return the only {@link StockState} which has not to be considered in the query.
     */
    public Optional<StockState> getExcluldeStockState() {
        return Optional.ofNullable(stockStateExcluded);
    }
    /**
     * Return a {@link OrderParameter} which denotes the field which has to be used for sorting the query.
     * @return a {@link OrderParameter} which denotes the field which has to be used for sorting the query.
     */
    public OrderParameter getSortBy() {
        return orderParameter == null ? OrderParameter.NONE : orderParameter;
    }

    /**
     * Return a boolean denoting whether the sort has to be done in a decreasing order.
     * @return a boolean denoting whether the sort has to be done in a decreasing order.
     */
    public boolean getOrderDecreasingly() {
        return orderDecreasingly;
    }

}
