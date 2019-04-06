package nwoolcan.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Handles the Warehouse view.
 */
@SuppressWarnings("NullAway")
public final class WarehouseController implements InitializableController<WarehouseViewModel> {

    @FXML
    private Label lblName;

    @Override
    public void initData(final WarehouseViewModel data) {
        this.lblName.setText(data.getName());
    }
}
