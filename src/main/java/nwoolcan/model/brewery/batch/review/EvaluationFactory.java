package nwoolcan.model.brewery.batch.review;

import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Static methods for EvaluationFactory creation.
 */
public final class EvaluationFactory {
    private static final String INVALID_SCORE = "Invalid score";

    private EvaluationFactory() { }
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

}
