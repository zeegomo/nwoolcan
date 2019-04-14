package nwoolcan.view.production;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;

/**
 * View controller for the batch detail view.
 */
@SuppressWarnings("NullAway")
public final class BatchDetailController
    extends SubViewController
    implements InitializableController<DetailBatchViewModel> {

    @FXML
    private Button goBackButton;

    @FXML
    private SubView batchDetailSubView;

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

    }

    @Override
    protected SubView getSubView() {
        return this.batchDetailSubView;
    }

    /**
     * Goes back to the production view.
     * @param event the occurred event.
     */
    public void goBackButtonClicked(final ActionEvent event) {
        this.previousView();    //TODO this does not reload the production view
    }
}
