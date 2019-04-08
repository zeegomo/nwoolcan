package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * View detail of a batch evaluation.
 */
public class BatchEvaluationDetailViewModel {

    private final BatchEvaluation evaluation;

    /**
     * Constructs a new {@link BatchEvaluationDetailViewModel}.
     * @param eval the batch evaluation.
     */
    public BatchEvaluationDetailViewModel(final BatchEvaluation eval) {
        this.evaluation = eval;
    }
    /**
     * Returns the general info for this evaluation.
     * @return the general info for this evaluation.
     */
    public BatchEvaluationViewModel getInfo() {
        return new BatchEvaluationViewModel(this.evaluation);
    }
    /**
     * Returns the evaluations for the categories.
     * @return the evaluations for the categories.
     */
    public Collection<EvaluationViewModel> getCategories() {
        return Collections.unmodifiableCollection(this.evaluation.getCategoryEvaluations()
                                                                 .stream()
                                                                 .map(EvaluationViewModel::new)
                                                                 .collect(Collectors.toList()));
    }
}
