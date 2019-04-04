package nwoolcan.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.view.subview.SubView;

/**
 * Handles the Dashboard view.
 */
@SuppressWarnings("NullAway")
public class DashboardController extends SubViewController {

    @FXML
    public SubView content;

    @FXML
    public void btnBackClicked(ActionEvent event) {
        this.previousView(content);
    }

    public void btnNewClicked(ActionEvent event) {
        this.overlayView(content, ViewType.DASHBOARD);
    }

    public void btnToWarehouseClicked(ActionEvent event) {
        this.substituteView(content, ViewType.WAREHOUSE, new Object());
    }
}
