package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.EvaluationType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;

/**
 * Mockup evaluation created from the view.
 * No controls on data integrity.
 */
public class BatchEvaluationDTO {

    private final BatchEvaluationType type;
    private final Collection<Pair<EvaluationType, Integer>> categories;

    /**
     * Constructs a new {@link BatchEvaluationDTO}.
     * @param type the type of the evaluation.
     * @param categories the categories for the evaluation.1
     */
    public BatchEvaluationDTO(final BatchEvaluationType type,
                              final Collection<Pair<EvaluationType, Integer>> categories) {
        this.categories = categories;
        this.type = type;
    }
    /**
     * Return the {@link BatchEvaluationType} for this evaluation.
     * @return the {@link BatchEvaluationType} for this evaluation.
     */
    public BatchEvaluationType getBatchEvaluationType() {
        return this.type;
    }
    /**
     * Return the evaluations for the categories.
     * @return the evaluations for the categories.
     */
    public Collection<Pair<EvaluationType, Integer>> getCategories() {
        return this.categories;
    }
}
