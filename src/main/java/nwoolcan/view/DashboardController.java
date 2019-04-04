package nwoolcan.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.view.subview.SubView;

/**
 * Handles the Dashboard view.
 */
@SuppressWarnings("NullAway")
public final class DashboardController extends SubViewController {

    @FXML
    private SubView content;

    /**
     * Removes this Dashboard view.
     * @param event The occurred event
     */
    public void btnBackClicked(final ActionEvent event) {
        this.previousView(content);
    }

    /**
     * Overlay another Dashboard over this one.
     * @param event The occurred event
     */
    public void btnNewClicked(final ActionEvent event) {
        this.overlayView(content, ViewType.DASHBOARD);
    }

    /**
     * Substitute this view with Warehouse.
     * @param event The occurred event
     */
    public void btnToWarehouseClicked(final ActionEvent event) {
        this.substituteView(content, ViewType.WAREHOUSE, new Object());
    }
}
