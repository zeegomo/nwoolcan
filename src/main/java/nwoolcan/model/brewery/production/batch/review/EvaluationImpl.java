package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation for {@link Evaluation}.
 */
public class EvaluationImpl implements Evaluation {
    private static final String INVALID_SCORE = "Invalid score";
    private final EvaluationType category;
    private final int score;
    private final Optional<String> notes;

    //package-private
    EvaluationImpl(final EvaluationType evaluationType, final int score, final Optional<String> notes) {
        this.category = evaluationType;
        this.score = score;
        this.notes = notes;
    }

    /**
     * Create a new Evaluation instance.
     * @param evaluationType the type of the evaluation.
     * @param score the score for the evaluation.
     * @param notes optional notes for the evaluation.
     * @return a new {@link Evaluation}.
     */
    public static Result<Evaluation> create(final EvaluationType evaluationType, final int score, @Nullable final String notes) {
        return Result.of(evaluationType)
                     .require(type -> score >= 0 && score <= type.getMaxScore(),
                         new IllegalArgumentException(INVALID_SCORE))
                     .map(type -> new EvaluationImpl(type, score, Optional.of(notes)));
    }
    /**
     * Create a new Evaluation instance.
     * @param evaluationType the type of the evaluation.
     * @param score the score for the evaluation.
     * @return a new {@link Evaluation}.
     */
    public static Result<Evaluation> create(final EvaluationType evaluationType, final int score) {
        return Result.of(evaluationType)
                     .require(type -> score >= 0 && score <= type.getMaxScore(),
                         new IllegalArgumentException(INVALID_SCORE))
                     .map(type -> new EvaluationImpl(type, score, Optional.empty()));
    }


    /**
     * @return the {@link EvaluationType} of this evaluation.
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
        if (o instanceof EvaluationImpl) {
            EvaluationImpl that = (EvaluationImpl) o;
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
        return "[EvaluationImpl] { "
            + "score: " + this.score + ", "
            + "maxScore: " + this.category.getMaxScore() + ", "
            + " type: " + this.category.getName() + " "
            + " }";
    }
}
