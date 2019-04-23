package nwoolcan.model.brewery.warehouse.article;

import javax.annotation.Nullable;

/**
 * Builder of the QueryArticle class.
 */
public final class QueryArticleBuilder {

    @Nullable private String fitName;
    @Nullable private ArticleType includeArticleType;
    @Nullable private ArticleType excludeArticleType;
    private QueryArticle.SortParameter sortParameter = QueryArticle.SortParameter.ID;
    private Boolean sortDescending = false;

    /**
     * Set the fit name.
     * @param name which has to be contained in the name of the article.
     * @return this for fluency.
     */
    public QueryArticleBuilder setFitName(final String name) {
        this.fitName = name;
        return this;
    }
    /**
     * Set the only {@link ArticleType} which will be included in the query.
     * @param articleType to be set.
     * @return this for fluency.
     */
    public QueryArticleBuilder setIncludeArticleType(final ArticleType articleType) {
        this.includeArticleType = articleType;
        return this;
    }
    /**
     * Set the only {@link ArticleType} which will be excluded in the query.
     * @param articleType to be set.
     * @return this for fluency.
     */
    public QueryArticleBuilder setExcludeArticleType(final ArticleType articleType) {
        this.excludeArticleType = articleType;
        return this;
    }
    /**
     * Set the parameter on which the sort comparator will work.
     * @param sortParameter on which the sort comparator will work.
     * @return this for fluency.
     */
    public QueryArticleBuilder sortyBy(final QueryArticle.SortParameter sortParameter) {
        this.sortParameter = sortParameter;
        return this;
    }
    /**
     * Set the order on which the sorted query will be displayed.
     * @param sortDescending is true when the order has to be descending.
     * @return this for fluency.
     */
    public QueryArticleBuilder setSortDescending(final Boolean sortDescending) {
        this.sortDescending = sortDescending;
        return this;
    }
    /**
     * Returns a new {@link QueryArticle} build accordingly with the specified fields.
     * @return a new {@link QueryArticle} build accordingly with the specified fields.
     */
    // It does not return a Result because it has no reasons to fail.
    public QueryArticle build() {
        return new QueryArticle(fitName, includeArticleType, excludeArticleType, sortParameter, sortDescending);
    }

}
