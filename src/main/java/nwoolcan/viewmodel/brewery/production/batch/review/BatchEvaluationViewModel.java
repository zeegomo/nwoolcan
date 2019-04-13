package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.batch.review.BatchEvaluation;

import java.util.Optional;

/**
 * Evaluation general info.
 */
public class BatchEvaluationViewModel extends EvaluationViewModel {

    private final Optional<String> reviewer;
    /**
     * Construct a new {@link BatchEvaluationViewModel}.
     * @param evaluation the review.
     */
    public BatchEvaluationViewModel(final BatchEvaluation evaluation) {
        super(evaluation);
        this.reviewer = evaluation.getReviewer();
    }
    /**
     * Returns the reviewer for this evaluation, if available.
     * @return the reviewer for this evaluation, if available.
     */
    public Optional<String> getReviewer() {
        return this.reviewer;
    }

}
