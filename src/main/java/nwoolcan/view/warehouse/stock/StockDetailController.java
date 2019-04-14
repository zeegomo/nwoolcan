package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nwoolcan.controller.Controller;
import nwoolcan.view.utils.subview.SubView;
import nwoolcan.view.utils.InitializableController;
import nwoolcan.view.utils.SubViewController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.viewmodel.brewery.warehouse.stock.StockViewModel;

/**
 * Controller for the Stock detail view.
 */
@SuppressWarnings("NullAway")
public final class StockDetailController extends SubViewController implements InitializableController<StockViewModel> {
    @FXML
    private Label lblName;

    @FXML
    private Label lblId;

    @FXML
    private SubView subView;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public StockDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final StockViewModel data) {
        lblName.setText("DummyStock");
        lblId.setText(Integer.toString(data.getId()));
    }

    @FXML
    private void btnBackClick(final ActionEvent event) {
        this.previousView();
    }

    @Override
    protected SubView getSubView() {
        return this.subView;
    }
}
