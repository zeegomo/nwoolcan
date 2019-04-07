package nwoolcan.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.view.subview.SubViewContainer;

/**
 * Handles the Main view.
 */
@SuppressWarnings("NullAway") // TODO
public final class MainController {

    @FXML
    private SubViewContainer contentPane;

    /**
     * Shows the Dashboard.
     * @param event The occurred event
     */
    public void menuViewDashboardClick(final ActionEvent event) {
        ViewManager.getView(ViewType.DASHBOARD).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Shows the Warehouse view.
     * @param event The occurred event
     */
    public void menuViewWarehouseClick(final ActionEvent event) {
        ViewManager.getView(ViewType.WAREHOUSE, new WarehouseViewModel("ciccio")).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Quits the application.
     * @param event The occurred event
     */
    public void menuFileQuitClick(final ActionEvent event) {
        Platform.exit();
    }

    /**
     * Print on the stdout the current number of overlays.
     * @param event The occurred event
     */
    public void menuFileCountOverlaysClick(final ActionEvent event) {
        System.out.println(contentPane.getOverlaysCount());
    }
}