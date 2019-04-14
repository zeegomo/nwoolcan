package nwoolcan.view.production;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.mastertable.MasterTableViewModel;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.MasterStepViewModel;

import java.util.Arrays;

/**
 * View controller for the batch detail view.
 */
@SuppressWarnings("NullAway")
public final class BatchDetailController
    extends SubViewController
    implements InitializableController<DetailBatchViewModel> {

    @FXML
    private SubViewContainer masterTableContainer;
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
        //TODO init batch info sub view

        final MasterTableViewModel<MasterStepViewModel, Object> masterViewModel = new MasterTableViewModel<>(
            Arrays.asList(
                new ColumnDescriptor("Step name", "type"),
                new ColumnDescriptor("Start date", "startDate"),
                new ColumnDescriptor("End date", "endDate"),
                new ColumnDescriptor("End size", "endSize"),
                new ColumnDescriptor("Finalized", "finalized")
            ),
            data.getSteps(),
            ViewType.STEP_DETAIL,
            mbvm -> null //TODO convert into step detail view model
        );

        this.getViewManager().getView(ViewType.MASTER_TABLE, masterViewModel).peek(p -> masterTableContainer.substitute(p));

//        if (data.getReview() != null) {
//            //TODO init review sub view
//        }
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
