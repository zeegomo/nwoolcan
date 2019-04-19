package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.batch.Batch;

/**
 * A particular type of {@link Stock} used with {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
 */
public interface BeerStock extends Stock {
    /**
     * Return the {@link Batch} related to the {@link BeerStock}.
     * @return the {@link Batch} related to the {@link BeerStock}.
     */
    int getBatchId();
}
