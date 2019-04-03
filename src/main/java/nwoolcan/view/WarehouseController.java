package nwoolcan.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Handles the Warehouse view.
 */
@SuppressWarnings("NullAway")
public class WarehouseController implements InitializableController<WarehouseViewModel> {

    @FXML
    public Label lblName;

    @Override
    public void initData(WarehouseViewModel data) {
        this.lblName.setText(data.getName());
    }
}
