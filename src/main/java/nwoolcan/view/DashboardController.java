package nwoolcan.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;

/**
 * Handles the Dashboard view.
 */
@SuppressWarnings("NullAway")
public class DashboardController {

    @FXML
    public SubView content;

    @FXML
    public void btnBackClicked(ActionEvent event) {
        this.content.getContainer().ifPresent(SubViewContainer::previous);
    }

    public void btnNewClicked(ActionEvent event) {
        this.content.getContainer().ifPresent(c -> ViewManager.getView(ViewType.DASHBOARD).peek(c::overlay));
    }

    public void btnToWarehouseClicked(ActionEvent event) {
        this.content.getContainer().ifPresent(c -> ViewManager.getView(ViewType.WAREHOUSE).peek(c::substitute));
    }
}
