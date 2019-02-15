package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;

/**
 * BeerArticle.
 */
public interface BeerArticle extends Article {
    /**
     * Returns the Batch linked to this BeerArticle.
     * @return the Batch linked to this BeerArticle.
     */
    Batch getBatch();
}
