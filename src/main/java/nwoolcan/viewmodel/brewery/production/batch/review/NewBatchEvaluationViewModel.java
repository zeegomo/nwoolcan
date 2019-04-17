package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.batch.review.BatchEvaluationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * View model for the creation of a new Batch Evaluation.
 */
public class NewBatchEvaluationViewModel {

    private final List<BatchEvaluationType> types;
    private final int id;
    /**
     * Creates a new {@link NewBatchEvaluationViewModel}.
     * @param types the list of {@link BatchEvaluationType}.
     * @param id the id of the batch
     */
    public NewBatchEvaluationViewModel(final Set<BatchEvaluationType> types, final int id) {
        this.types = new ArrayList<>(types);
        this.id = id;
    }
    /**
     * Returns all the available {@link BatchEvaluationType}.
     * @return all the available {@link BatchEvaluationType}.
     */
    public List<BatchEvaluationType> getTypes() {
        return Collections.unmodifiableList(this.types);
    }
    /**
     * Returns the id of the batch.
     * @return the id of the batch.
     */
    public int getID() {
        return this.id;
    }
}
