package nwoolcan.model.brewery.production.batch.review;

import java.util.Collection;
import java.util.Optional;

/**
 * A review for a production batch.
 */
public interface BatchReview extends Evaluation {
    /**
     * Return an identifier of the review type.
      * @return an identifier of the review type
     */
    @Override
    BatchReviewType getEvaluationType();
    /**
     * Return the name of the reviewer, like "John Doe".
     * @return an identifier of the reviewer.
     */
    Optional<String> getReviewer();
    /**
     * Returns the categories of this {@link BatchReview}.
     * @return the categories of this {@link BatchReview}.
     */
    Collection<Evaluation> getCategoryEvaluations();
}
