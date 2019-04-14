package nwoolcan.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.view.subview.SubViewContainer;

import java.util.Date;

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

        //final int id1 = getController().getWarehouseController().createBeerArticle("beer", UnitOfMeasure.BOTTLE_33_CL).getId();
        final int id2 = getController().getWarehouseController().createIngredientArticle("ing", UnitOfMeasure.GRAM, IngredientType.FERMENTABLE).getId();
        final int id3 = getController().getWarehouseController().createMiscArticle("misc", UnitOfMeasure.UNIT).getId();
        getController().getWarehouseController().createStock(id2);
        getController().getWarehouseController().createStock(id3, new Date());
        getController().getWarehouseController().addRecord(id3, 3.0, Record.Action.ADDING);
        this.getViewManager().getView(ViewType.WAREHOUSE, getController().getWarehouseController().getWarehouseViewModel()).peek(view -> this.contentPane.substitute(view));
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
            .getView(ViewType.ARTICLES,
                     this.getController()
                         .getWarehouseController()
                         .getArticlesViewModel())
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
