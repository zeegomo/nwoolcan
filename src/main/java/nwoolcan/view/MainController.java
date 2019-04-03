package nwoolcan.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

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
    @FXML
    public void menuViewDashboardClick(final ActionEvent event) {
        this.contentPane.substitute(ViewImpl.getViews().get(ViewType.DASHBOARD));
    }

    /**
     * Shows the Warehouse view.
     * @param event The occurred event
     */
    @FXML
    public void menuViewWarehouseClick(final ActionEvent event) {
        this.contentPane.substitute(ViewImpl.getViews().get(ViewType.WAREHOUSE));
    }

    /**
     * Quits the application.
     * @param event The occurred event
     */
    @FXML
    public void menuFileQuitClick(final ActionEvent event) {
        Platform.exit();
    }

}
