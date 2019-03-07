package nwoolcan.model.brewery.production.batch.review;

/**
 * Evaluation category.
 */

public interface EvaluationType {
    /**
     * Return the name of the category.
     * @return the name of the category.
     */
    String getName();
    /**
     * Return the max possible score in this category.
     * @return the max possible score in this category.
     */
    int getMaxScore();
}
