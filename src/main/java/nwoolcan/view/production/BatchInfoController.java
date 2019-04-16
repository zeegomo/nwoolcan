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
public final class BatchInfoController extends SubViewController implements InitializableController<BatchInfoViewModel> {
    @FXML
    private SubView batchInfoSubView;
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
    public BatchInfoController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final BatchInfoViewModel data) {
        this.nameLabel.setText(data.getBeerName());
        this.styleLabel.setText(data.getBeerStyle());
        this.styleCategoryLabel.setText(data.getBeerStyleCategory().orElse(""));
        data.getOg().ifPresent(val -> this.ogLabel.setText(val.getValueRepresentation()));
        data.getFg().ifPresent(val -> this.fgLabel.setText(val.getValueRepresentation()));
        data.getAbv().ifPresent(val -> this.abvLabel.setText(val.getValueRepresentation()));
        data.getEbc().ifPresent(val -> this.ebcLabel.setText(val.getValueRepresentation()));
        data.getIbu().ifPresent(val -> this.ibuLabel.setText(val.getValueRepresentation()));
        this.batchSizeLabel.setText(data.getBatchSize().toString());
    }

    @Override
    protected SubView getSubView() {
        return this.batchInfoSubView;
    }
}
