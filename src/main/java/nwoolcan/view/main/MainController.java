package nwoolcan.view.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubViewContainer;
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
        this.getViewManager().getView(ViewType.PRODUCTION).peek(view -> this.contentPane.substitute(view));
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
}
