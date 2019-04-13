package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.batch.review.Evaluation;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Evaluation general info.
 */
public class EvaluationViewModel {

    private final String type;
    private final int score;
    private final int maxScore;
    @Nullable
    private final String notes;
    /**
     * Construct a new {@link EvaluationViewModel}.
     * @param eval the evaluation.
     */
    public EvaluationViewModel(final Evaluation eval) {
        this.type = eval.getEvaluationType().getName();
        this.score = eval.getScore();
        this.maxScore = eval.getEvaluationType().getMaxScore();
        this.notes = eval.getNotes().orElse(null);
    }
    /**
     * Returns the name of the type of the evaluation.
     * @return the name of the type of the evaluation.
     */
    public String getType() {
        return this.type;
    }
    /**
     * Returns the maximum possible score of the type of the evaluation.
     * @return the maximum possible score of the type of the evaluation.
     */
    public int getMaxScore() {
        return this.maxScore;
    }
    /**
     * Returns the score for this evaluation.
     * @return the score for this evaluation.
     */
    public int getScore() {
        return this.score;
    }
    /**
     * Returns the score for this evaluation.
     * @return the score for this evaluation.
     */
    public Optional<String> getNotes() {
        return Optional.ofNullable(this.notes);
    }
}
