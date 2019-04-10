package nwoolcan.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nwoolcan.application.ViewManager;
import nwoolcan.controller.Controller;
import nwoolcan.controller.viewmodel.StockViewModel;
import nwoolcan.view.subview.SubView;

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

    public StockDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final StockViewModel data) {
        lblName.setText(data.getName());
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
