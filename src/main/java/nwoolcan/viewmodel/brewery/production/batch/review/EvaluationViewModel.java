package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.Evaluation;

/**
 * Evaluation general info.
 */
public class EvaluationViewModel {

    private final Evaluation evaluation;
    /**
     * Construct a new {@link EvaluationViewModel}.
     * @param eval the evaluation.
     */
    public EvaluationViewModel(final Evaluation eval) {
        this.evaluation = eval;
    }
    /**
     * Returns the name of the type of the evaluation.
     * @return the name of the type of the evaluation.
     */
    public String getType() {
        return this.evaluation.getEvaluationType().getName();
    }
    /**
     * Returns the maximum possible score of the type of the evaluation.
     * @return the maximum possible score of the type of the evaluation.
     */
    public int getMaxScore() {
        return this.evaluation.getEvaluationType().getMaxScore();
    }
    /**
     * Returns the score for this evaluation.
     * @return the score for this evaluation.
     */
    public int getScore() {
        return this.evaluation.getScore();
    }
}
