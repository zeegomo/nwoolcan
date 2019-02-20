package nwoolcan.model.brewery.production.batch.review;

import java.util.Optional;

/**
 * Evaluation.
 */
public interface Evaluation {
    /**
     * Returns the score.
     * @return the score.
     */
    int getScore();
    /**
     * Returns the maximum possible score.
     * @return the maximum possible score.
     */
    int getMaxScore();
    /**
     * Returns evaluation notes, if any.
     * @return evaluation notes, if any.
     */
    Optional<String> getNotes();
}
