package nwoolcan.model.brewery.production.batch.review;

import java.util.Collection;
import java.util.Optional;

/**
 * BatchReview.
 */
public interface BatchReview extends Evaluation {
    /**
     * Return an identifier of the review type.
      * @return an identifier of the review type
     */
    @Override
    BatchReviewType getEvaluationType();
    /**
     * Return an identifier of the reviewer, like "John Doe".
     * @return an identifier of the reviewer.
     */
    Optional<String> getReviewer();
    /**
     * Returns the category evaluations.
     * @return the category evaluations.
     */
    Collection<Evaluation> getCategoryEvaluations();
}
