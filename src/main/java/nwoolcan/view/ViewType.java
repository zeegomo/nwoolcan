package nwoolcan.view;

import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;

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
     *
     */
    MASTER_TABLE,
    /**
     * Detail view to show a {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    STOCK_DETAIL;
    /**
     * Name of the FXML files associated with this type of view.
     * @return The resource name, relative to this package.
     */
    public String getResourceName() {
        return this.name().toLowerCase() + ".fxml";
    }
}
