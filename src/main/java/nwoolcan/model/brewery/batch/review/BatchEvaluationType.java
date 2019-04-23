package nwoolcan.model.brewery.batch.review;

import java.util.Set;
import java.util.function.Function;

/**
 * Batch review type, defined by its categories.
 */
public interface BatchEvaluationType extends EvaluationType {
    /**
     * List all categories for this scoresheet.
     * @return the category list.
     */
    Set<EvaluationType> getCategories();
    /**
     * Returns a function that specify how to calculate a score based on the score of the categories.
     * @return a {@link Function}
     */
    Function<Set<Evaluation>, Integer> calculateScore();
}
