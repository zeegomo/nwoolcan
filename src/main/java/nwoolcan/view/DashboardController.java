package nwoolcan.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.controller.Controller;
import nwoolcan.view.subview.SubView;

/**
 * Handles the Dashboard view.
 */
@SuppressWarnings("NullAway")
public final class DashboardController extends SubViewController {

    @FXML
    private SubView content;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public DashboardController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    /**
     * Removes this Dashboard view.
     * @param event The occurred event
     */
    public void btnBackClicked(final ActionEvent event) {
        this.previousView();
    }

    /**
     * Overlay another Dashboard over this one.
     * @param event The occurred event
     */
    public void btnNewClicked(final ActionEvent event) {
        this.overlayView(ViewType.DASHBOARD);
    }

    /**
     * Substitute this view with Warehouse.
     * @param event The occurred event
     */
    public void btnToWarehouseClicked(final ActionEvent event) {
        this.substituteView(ViewType.WAREHOUSE, getController().getWarehouseController().getWarehouseViewModel());
    }

    @Override
    protected SubView getSubView() {
        return this.content;
    }
}
