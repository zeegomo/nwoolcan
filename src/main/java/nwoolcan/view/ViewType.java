package nwoolcan.view;

/**
 * Types of views of the application.
 * For each type must exist a resource with the same name, lowercased and with ".fxml" at the end
 */
public enum ViewType {

    /**
     * The main view, the one that is always visible and contains all the others.
     */
    MAIN,
    /**
     * The dashboard scene.
     */
    DASHBOARD,
    /**
     * The warehouse view.
     */
    WAREHOUSE,
    /**
<<<<<<< HEAD
     * The review view.
     */
    BATCHEVALUATION,
    /**
     * The review view.
     */
    BATCHEVALUATIONDETAIL,
    /**
     * eval.
     */
    EVALUATION,
    /**
     * Insertion for categories.
     */
    EVALUATION_TYPE,
    /**
     * Modal for creating a new evaluation.
     */
    NEW_BATCH_EVALUATION_MODAL,
    /**
     *
=======
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    STOCK_DETAIL,
    /**
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    ARTICLE_DETAIL,
    /**
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    NEW_ARTICLE_MODAL,
    /**
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    ARTICLES,
    /**
>>>>>>> cb2b85b7bb241cc005a58785ade7034b70b666c3
     * The main production view.
     */
    PRODUCTION,
    /**
     * The master table view.
     */
    MASTER_TABLE,
    /**
     * Detail view to show a batch.
     */
    BATCH_DETAIL,
    /**
     * Modal for creating a new batch.
     */
    NEW_BATCH_MODAL;
    /**
     * Name of the FXML files associated with this type of view.
     * @return The resource name, relative to this package.
     */
    public String getResourceName() {
        return this.name().toLowerCase() + ".fxml";
    }
}
