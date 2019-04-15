package nwoolcan.view.warehouse;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nwoolcan.controller.Controller;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.view.InitializableController;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.mastertable.MasterTableViewModel;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

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

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public WarehouseController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final WarehouseViewModel data) {
        this.lblName.setText("DummyWarehouseName");
        getController().getWarehouseController().createMiscArticle("Dum", UnitOfMeasure.BOTTLE_33_CL);
        getController().getWarehouseController().createMiscArticle("Dum2", UnitOfMeasure.BOTTLE_33_CL);

        //load master table
        this.getViewManager().getView(ViewType.MASTER_TABLE, new MasterTableViewModel<>(
            new ArrayList<>(Arrays.asList(new ColumnDescriptor("Id", "id"),
                new ColumnDescriptor("Name", "name"))),
            new ArrayList<>(Arrays.asList(getController().getWarehouseController().createStock(1),
                getController().getWarehouseController().createStock(0).getValue())),
            ViewType.STOCK_DETAIL,
            Function.identity()
        )).peek(view -> this.stockTable.substitute(view));
    }

    @Override
    protected SubView getSubView() {
        return this.warehouseSubView;
    }
}
