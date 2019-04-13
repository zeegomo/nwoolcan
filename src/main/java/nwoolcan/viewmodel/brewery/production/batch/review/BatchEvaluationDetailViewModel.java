package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.batch.review.BatchEvaluation;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * View detail of a batch evaluation.
 */
public class BatchEvaluationDetailViewModel {

    private final BatchEvaluationViewModel info;
    private final Collection<EvaluationViewModel> categories;

    /**
     * Constructs a new {@link BatchEvaluationDetailViewModel}.
     * @param eval the batch evaluation.
     */
    public BatchEvaluationDetailViewModel(final BatchEvaluation eval) {
        this.info = new BatchEvaluationViewModel(eval);
        this.categories = eval.getCategoryEvaluations()
                              .stream()
                              .map(EvaluationViewModel::new)
                              .collect(Collectors.toList());
    }
    /**
     * Returns the general info for this evaluation.
     * @return the general info for this evaluation.
     */
    public BatchEvaluationViewModel getInfo() {
        return this.info;
    }
    /**
     * Returns the evaluations for the categories.
     * @return the evaluations for the categories.
     */
    public Collection<EvaluationViewModel> getCategories() {
        return Collections.unmodifiableCollection(this.categories);
    }
}
