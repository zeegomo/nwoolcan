package nwoolcan.model.brewery.production.batch.review;

import java.util.Objects;
import java.util.Optional;

/**
 * CategoryEvaluation.
 */
public class EvaluationImpl implements Evaluation {
    private final EvaluationType category;
    private final int score;
    private final Optional<String> notes;

    EvaluationImpl(final EvaluationType evaluationType, final int score, final Optional<String> notes) {
        this.category = evaluationType;
        this.score = score;
        this.notes = notes;
    }

    /**
     * @return an EvaluationType.
     */
    @Override
    public EvaluationType getEvaluationType() {
        return this.category;
    }

    /**
     * @return the score of this evaluation.
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * @return the notes, if any, for this evaluation.
     */
    @Override
    public Optional<String> getNotes() {
        return notes;
    }

    /**
     * Test for equality.
     *
     * @param o the other object
     * @return if o is equal to this.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Evaluation) {
            Evaluation that = (Evaluation) o;
            return score == that.getScore()
                && Objects.equals(category, that.getEvaluationType());
        } else {
            return false;
        }

    }

    /**
     * @return the hashcode for this class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(category, score);
    }

    /**
     * @return the string representation for this class.
     */
    @Override
    public String toString() {
        return "[Evaluation] score: " + this.score + "/" + this.category.getMaxScore() + " type: " + this.category.getName();
    }
}
