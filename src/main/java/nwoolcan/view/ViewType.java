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
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    STOCK_DETAIL,
    /**
     * Modal to create a new {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    NEW_STOCK_MODAL,
    /**
     * Modal to create a new {@link nwoolcan.model.brewery.warehouse.stock.Record}.
     */
    NEW_RECORD_MODAL,
    /**
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    ARTICLE_DETAIL,
    /**
     * Modal for creating a new {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    NEW_ARTICLE_MODAL,
    /**
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    ARTICLES,
    /**
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
     * Detail view to show a step.
     */
    STEP_DETAIL,
    /**
     * Modal for creating a new batch.
     */
    NEW_BATCH_MODAL,
    /**
     * Modal for going to the next step in a batch in production.
     */
    GO_NEXT_STEP_MODAL,
    /**
     * View visible at the start.
     */
    WELCOME,
    /**
     * Modal to create a new brewery.
     */
    NEW_BREWERY_MODAL;

    /**
     * Name of the FXML files associated with this type of view.
     * @return The resource name, relative to this package.
     */
    public String getResourceName() {
        return this.name().toLowerCase() + ".fxml";
    }
}
