package nwoolcan.view.production;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.review.BatchEvaluationBuilder;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.mastertable.MasterTableViewModel;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;
import nwoolcan.viewmodel.brewery.production.batch.MasterStepViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * View controller for the batch detail view.
 */
@SuppressWarnings("NullAway")
public final class BatchDetailController
    extends SubViewController
    implements InitializableController<DetailBatchViewModel> {

    @FXML
    private Button goToNextStepButton;
    @FXML
    private Button stockBatchButton;

    @FXML
    private SubViewContainer masterTableContainer;
    @FXML
    private Button goBackButton;
    @FXML
    private SubViewContainer reviewContainer;
    @FXML
    private SubView batchDetailSubView;

    private DetailBatchViewModel data;



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
        this.data = data;

        //TODO init batch info sub view

        this.goToNextStepButton.setDisable(data.isEnded());

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
        if (data.getReview() != null) {
            this.getViewManager().getView(ViewType.BATCHEVALUATION, data.getReview())
                          .peek(this.reviewContainer::substitute)
                          .peekError(err -> Logger.getGlobal().severe("Could not load: " + err.getMessage()));
        } else {
            Button b = new Button("add review");
            b.setOnAction(actionEvent -> {
                final Stage modal =  new Stage();
                final Window window = this.getSubView().getScene().getWindow();

                modal.initOwner(window);
                modal.initModality(Modality.WINDOW_MODAL);

                final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_BATCH_EVALUATION_MODAL,
                    new ArrayList<>(BatchEvaluationBuilder.getAvailableBatchEvaluationTypes().getValue())).orElse(new AnchorPane()));

                modal.setScene(scene);
                modal.centerOnScreen();
                modal.showAndWait();

                if (modal.getUserData() != null) {
                    this.substituteView(ViewType.PRODUCTION, this.getController().getProductionViewModel());
                }
            });
            this.reviewContainer.substitute(b);
        }
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
        this.substituteView(ViewType.PRODUCTION, this.getController().getProductionViewModel());
    }

    /**
     * Opens a modal that let the user go to the next production step.
     * @param event the occurred event.
     */
    public void goToNextStepButtonClicked(final ActionEvent event) {
        final Stage modal =  new Stage();
        final Window window = this.getSubView().getScene().getWindow();

        modal.initOwner(window);
        modal.initModality(Modality.WINDOW_MODAL);

        Result<GoNextStepViewModel> res = this.getController().getBatchController().getGoNextStepViewModel(this.data.getId());

        if (res.isError()) {
            this.showAlertAndWait(res.getError().getMessage());
            return;
        }

        final Scene scene = new Scene(this.getViewManager().getView(ViewType.GO_NEXT_STEP_MODAL, res.getValue()).orElse(new AnchorPane()));

        modal.setScene(scene);
        modal.centerOnScreen();
        modal.showAndWait();

        if (modal.getUserData() != null) {
            this.substituteView(ViewType.BATCH_DETAIL,
                this.getController().getBatchController().getDetailBatchViewModelById(this.data.getId()).getValue());
        }
    }

    private void showAlertAndWait(final String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred while loading the next step modal.\n" + message, ButtonType.CLOSE);
        a.showAndWait();
    }
}
