package nwoolcan.view.production;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.viewmodel.brewery.production.batch.BatchInfoViewModel;

/**
 * Controller for Batch Info view.
 */
@SuppressWarnings("NullAway")
public class BatchInfoController extends SubViewController implements InitializableController<BatchInfoViewModel> {
    @FXML
    private Label nameLabel;
    @FXML
    private Label styleLabel;
    @FXML
    private Label styleCategoryLabel;
    @FXML
    private Label ogLabel;
    @FXML
    private Label fgLabel;
    @FXML
    private Label abvLabel;
    @FXML
    private Label ebcLabel;
    @FXML
    private Label ibuLabel;
    @FXML
    private Label batchSizeLabel;
    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    protected BatchInfoController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final BatchInfoViewModel data) {
        this.nameLabel.setText(data.getBeerName());
        this.styleLabel.setText(data.getBeerStyle());
        this.styleCategoryLabel.setText(data.getBeerStyleCategory().orElse(""));
        this.ogLabel.setText(data.ge);
    }

    @Override
    protected SubView getSubView() {
        return null;
    }
}
