package nwoolcan.model.brewery.production.batch.review;

import java.util.Collection;

/**
 * BatchReview.
 */
public interface BatchReview extends Evaluation {
    /**
     * Return an identifier of the review type, like "Official BJCP Review".
      * @return an identifier of the review type
     */
    String getName();
    /**
     * Return an identifier of the reviewer, like "John Doe".
     * @return an identifier of the reviewer.
     */
    String getReviewer();
    /**
     * Returns the category evaluations.
     * @return the category evaluations.
     */
    Collection<CategoryEvaluation> getCategoryEvaluations();
    /**
     * List all categories.
     * @return the category list.
     */
    Collection<CategoryEvaluationType> getCategories();
}
