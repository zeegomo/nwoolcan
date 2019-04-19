package nwoolcan.model.brewery.warehouse.stock;

/**
 * A particular type of {@link Stock} used with {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
 */
public interface BeerStock extends Stock {
    /**
     * Return the {@link nwoolcan.model.brewery.batch.Batch} related to the {@link BeerStock}.
     * @return the {@link nwoolcan.model.brewery.batch.Batch} related to the {@link BeerStock}.
     */
    int getBatchId();
}
