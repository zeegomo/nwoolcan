package nwoolcan.view.production;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;

@SuppressWarnings("NullAway")
public class BatchDetailController
    extends SubViewController
    implements InitializableController<DetailBatchViewModel> {

    @FXML
    private SubView batchDetailSubView;
    @FXML
    private Label batchIdLabel;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public BatchDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final DetailBatchViewModel data) {
        this.batchIdLabel.setText(String.valueOf(data.getId()));
    }

    @Override
    protected SubView getSubView() {
        return this.batchDetailSubView;
    }
}
