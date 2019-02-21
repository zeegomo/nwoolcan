package nwoolcan.model.brewery.production.batch.review;

import java.util.Objects;
import java.util.Optional;

/**
 * CategoryEvaluation.
 */
public final class EvaluationImpl implements Evaluation {
    private final EvaluationType category;
    private final int score;
    private final Optional<String> notes;

    EvaluationImpl(final EvaluationType evaluationType, final int score, final Optional<String> notes) {
        this.category = evaluationType;
        this.score = score;
        this.notes = notes;
    }

    @Override
    public EvaluationType getEvaluationType() {
        return this.category;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public Optional<String> getNotes() {
        return notes;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(category, score);
    }
}
