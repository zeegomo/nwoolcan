package nwoolcan.model.brewery.production.batch.review;

import java.util.Set;
import java.util.Optional;

/**
 * A review for a production batch.
 */
public interface BatchEvaluation extends Evaluation {
    /**
     * Return an identifier of the review type.
     * @return an identifier of the review type
     */
    @Override
    BatchEvaluationType getEvaluationType();
    /**
     * Return the name of the reviewer, like "John Doe".
     * @return an identifier of the reviewer.
     */
    Optional<String> getReviewer();
    /**
     * Returns the categories of this {@link BatchEvaluation}.
     * @return the categories of this {@link BatchEvaluation}.
     */
    Set<Evaluation> getCategoryEvaluations();
}
