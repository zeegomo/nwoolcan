package nwoolcan.view.warehouse;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nwoolcan.application.ViewManager;
import nwoolcan.controller.Controller;
import nwoolcan.controller.viewmodel.StockViewModel;
import nwoolcan.controller.viewmodel.WarehouseViewModel;
import nwoolcan.view.ColumnDescriptor;
import nwoolcan.view.InitializableController;
import nwoolcan.view.MasterTableViewModel;
import nwoolcan.view.SubViewController;
import nwoolcan.application.ViewManagerImpl;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles the Warehouse view.
 */
@SuppressWarnings("NullAway")
public final class WarehouseController extends SubViewController implements InitializableController<WarehouseViewModel> {

    @FXML
    private SubView warehouseSubView;
    @FXML
    private SubViewContainer stockTable;
    @FXML
    private Label lblName;

    public WarehouseController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final WarehouseViewModel data) {
        this.lblName.setText(data.getName());

        //load master table
        this.getViewManager().getView(ViewType.MASTER_TABLE, new MasterTableViewModel<StockViewModel>(
            new ArrayList<>(Arrays.asList(new ColumnDescriptor("Id", "id"),
                new ColumnDescriptor("Name", "name"))),
            new ArrayList<>(Arrays.asList(new StockViewModel(1, "ciao"),
                new StockViewModel(2, "ciaone"))),
            ViewType.STOCK_DETAIL
        )).peek(view -> this.stockTable.substitute(view));
    }

    @Override
    protected SubView getSubView() {
        return this.warehouseSubView;
    }
}
