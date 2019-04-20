package nwoolcan.view.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.utils.PersistencyUtils;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubViewContainer;

import java.io.File;
import java.util.logging.Logger;

/**
 * Handles the Main view.
 */
@SuppressWarnings("NullAway")
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
        this.getViewManager().getView(ViewType.DASHBOARD).peek(view -> this.contentPane.substitute(view));
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
        ((Stage) this.contentPane.getScene().getWindow()).close();
    }

    @FXML
    private void menuViewWelcomeClick(final ActionEvent event) {
        this.getViewManager().getView(ViewType.WELCOME).peek(view -> this.contentPane.substitute(view));
    }

    @FXML
    private void menuFileSaveClick(final ActionEvent event) {
        final PersistencyUtils utils = new PersistencyUtils(this.contentPane.getScene().getWindow(), this.getController().getFileController());
        utils.showSaveFile().ifPresent(target -> {
            this.getController().saveTo(target)
                .peek(e -> utils.showSaveSuccessAlert())
                .peekError(err -> {
                    Logger.getLogger(this.getClass().getName()).severe(err.toString());
                    utils.showErrorAlert();
                });
        });
    }

    @FXML
    private void menuFileLoadClick(final ActionEvent event) {
        final PersistencyUtils utils = new PersistencyUtils(this.contentPane.getScene().getWindow(), this.getController().getFileController());
        utils.showOpenFile().ifPresent(target -> {
            this.getController().loadFrom(target)
                .peek(e -> this.getViewManager().getView(ViewType.DASHBOARD).peek(this.contentPane::substitute))
                .peekError(err -> {
                    Logger.getLogger(this.getClass().getName()).severe(err.toString());
                    utils.showErrorAlert();
                });
        });
    }
}
