package nwoolcan.view.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubViewContainer;

import java.io.File;
import java.util.logging.Logger;

/**
 * Handles the Main view.
 */
@SuppressWarnings("NullAway") // TODO
public final class MainController extends AbstractViewController {

    @FXML
    private SubViewContainer contentPane;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public MainController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @FXML
    private void initialize() {
        this.getViewManager().getView(ViewType.WELCOME).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Shows the Dashboard.
     * @param event The occurred event
     */
    public void menuViewDashboardClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.DASHBOARD).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Shows the Warehouse view.
     * @param event The occurred event
     */
    public void menuViewWarehouseClick(final ActionEvent event) {
        getController().setBreweryName("ciccio");
        getController().setOwnerName("ciccia");
        this.getViewManager().getView(ViewType.WAREHOUSE).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Shows the Production view.
     * @param event The occurred event
     */
    public void menuViewProductionClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.PRODUCTION, this.getController().getProductionViewModel()).peek(view -> this.contentPane.substitute(view));
    }

    /**
     * Shows the Articles view.
     * @param event The occurred event
     */
    public void menuViewArticlesClick(final ActionEvent event) {
        this.getViewManager()
            .getView(ViewType.ARTICLES)
            .peek(view -> this.contentPane.substitute(view));
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

    @FXML
    private void menuViewWelcomeClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.WELCOME).peek(view -> this.contentPane.substitute(view));
    }

    @FXML
    private void menuFileSaveClick(final ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        final File target = fileChooser.showSaveDialog(this.contentPane.getScene().getWindow());
        if (target != null) {
            this.getController().saveTo(target)
                .peek(e -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Saving completed");
                    alert.showAndWait();
                }).peekError(err -> {
                    Logger.getLogger(this.getClass().getName()).severe(err.toString());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There was an error!");
                    alert.showAndWait();
                });
        }
    }
}
