package nwoolcan.viewmodel.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.EvaluationType;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

/**
 * Mockup evaluation created from the view.
 * No controls on data integrity.
 */
public class BatchEvaluationDTO {

    private final BatchEvaluationType type;
    @Nullable
    private final String notes;
    private final Collection<Triple<EvaluationType, Integer, Optional<String>>> categories;

    /**
     * Constructs a new {@link BatchEvaluationDTO}.
     * @param type the type of the evaluation.
     * @param categories the categories for the evaluation
     * @param notes the notes for this evaluation.
     * */
    public BatchEvaluationDTO(final BatchEvaluationType type,
                              final Collection<Triple<EvaluationType, Integer, Optional<String>>> categories,
                              @Nullable final String notes) {
        this.categories = categories;
        this.type = type;
        this.notes = notes;
    }
    /**
     * Return the {@link BatchEvaluationType} for this evaluation.
     * @return the {@link BatchEvaluationType} for this evaluation.
     */
    public BatchEvaluationType getBatchEvaluationType() {
        return this.type;
    }
    /**
     * Returns the notes for this evaluation, if available.
     * @return the notes for this evaluation, if available.
     */
    public Optional<String> getNotes() {
        return Optional.ofNullable(this.notes);
    }
    /**
     * Return the evaluations for the categories.
     * @return the evaluations for the categories.
     */
    public Collection<Triple<EvaluationType, Integer, Optional<String>>> getCategories() {
        return this.categories;
    }
}
