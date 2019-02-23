package nwoolcan.model.brewery.production.batch.review;

import java.util.Collection;

/**
 * Batch review type.
 */
public interface BatchReviewType extends EvaluationType {
    /**
     * List all categories for this scoresheet.
     * @return the category list.
     */
    Collection<EvaluationType> getCategories();
}
