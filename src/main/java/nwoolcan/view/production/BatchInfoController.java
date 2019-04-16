package nwoolcan.view.production;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.viewmodel.brewery.production.batch.BatchInfoViewModel;
import nwoolcan.viewmodel.brewery.production.step.ParameterViewModel;

/**
 * Controller for Batch Info view.
 */
@SuppressWarnings("NullAway")
public class BatchInfoController extends SubViewController implements InitializableController<BatchInfoViewModel> {
    private static final double WRAP = 1.2;
    private static final int DATA_NUMBER = 6;
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
    @FXML
    private GridPane statsGridPane;
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
        this.fgLabel.setText("1.020");
        this.ogLabel.setText("1.080");
        this.abvLabel.setText("11%");
        this.ebcLabel.setText("23");
        this.ibuLabel.setText("11");
        this.batchSizeLabel.setText(data.getBatchSize().toString());
    }

    @Override
    protected SubView getSubView() {
        return this.batchInfoSubView;
    }
}
