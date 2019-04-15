package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.warehouse.stock.DetailStockViewModel;

/**
 * Controller for the Stock detail view.
 */
@SuppressWarnings("NullAway")
public final class StockDetailController extends SubViewController implements InitializableController<DetailStockViewModel> {

    @FXML
    private Label lblArticle;
    @FXML
    private Label lblAvailableQt;
    @FXML
    private Label lblCreationDate;
    @FXML
    private Label lblLastModified;
    @FXML
    private Label lblUsedQt;
    @FXML
    private SubViewContainer masterTableContainer;
    @FXML
    private SubView stockDetailSubView;

    @FXML
    private Label lblId;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public StockDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final DetailStockViewModel data) {
        lblArticle.setText(data.getArticle().toString());
        lblAvailableQt.setText(data.getRemainingQuantity().toString());
        lblCreationDate.setText(data.getCreationDate());
        lblLastModified.setText(data.getLastModified());
        lblUsedQt.setText(data.getUsedQuantity().toString());
        lblId.setText(Integer.toString(data.getId()));
    }

    @FXML
    private void btnBackClick(final ActionEvent event) {
        this.previousView();
    }

    @Override
    protected SubView getSubView() {
        return this.stockDetailSubView;
    }

    /**
     * Creates an overlay and loads the article page.
     * @param actionEvent that occurred.
     */
    public void goToAricleButtonClick(final ActionEvent actionEvent) {
    }
}
