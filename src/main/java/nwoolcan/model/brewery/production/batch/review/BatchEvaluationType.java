package nwoolcan.model.brewery.production.batch.review;

import java.util.Collection;

/**
 * Batch review type, defined by its categories.
 */
public interface BatchEvaluationType extends EvaluationType {
    /**
     * List all categories for this scoresheet.
     * @return the category list.
     */
    Collection<EvaluationType> getCategories();
}
