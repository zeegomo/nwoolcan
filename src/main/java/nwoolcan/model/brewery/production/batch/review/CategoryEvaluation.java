package nwoolcan.model.brewery.production.batch.review;

/**
 * Evaluation.
 */
public interface CategoryEvaluation extends Evaluation {
    /**
     * Returns the category of this evaluation.
     * @return the category of this evaluation.
     */
    CategoryEvaluationType getCategory();
}
