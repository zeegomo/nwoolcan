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
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationViewModel;
import nwoolcan.viewmodel.brewery.production.batch.review.NewBatchEvaluationViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * View controller for the batch detail view.
 */
@SuppressWarnings("NullAway")
public final class BatchDetailController
    extends SubViewController
    implements InitializableController<DetailBatchViewModel> {
    private static final String TYPES_LOAD_FAILED = "Could not load types";
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
    @FXML
    private Button viewReviewButton;
    @FXML
    private Button addReviewButton;

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
            this.viewReviewButton.setDisable(false);
        } else {
            this.viewReviewButton.setDisable(true);
        }

        if (data.isEnded()) {
            this.addReviewButton.setDisable(false);
        } else {
            this.addReviewButton.setDisable(true);
        }
    }
    /**
     * Opens review detail view.
     * @param event the recorded event.
     */
    public void viewReviewClick(final ActionEvent event) {
        this.getController()
            .getBatchController()
            .getBatchEvaluation(this.data.getId())
            .peek(review -> review.ifPresent(val -> this.overlayView(ViewType.BATCHEVALUATIONDETAIL, val)));
    }
    /**
     * Opens a modal that let the user go to the next production step.
     * @param event the recorded action.
     */
    public void addReviewClick(final ActionEvent event) {
            final Stage modal = new Stage();
            final Window window = this.getSubView().getScene().getWindow();

            modal.initOwner(window);
            modal.initModality(Modality.WINDOW_MODAL);

            NewBatchEvaluationViewModel data = new NewBatchEvaluationViewModel(
                this.getController()
                    .getBatchController()
                    .getAvailableBatchEvaluationTypes()
                    .peekError(err -> this.showAlertAndWait(TYPES_LOAD_FAILED + ": " + err.getMessage()))
                    .peekError(err -> Logger.getGlobal().severe(TYPES_LOAD_FAILED + ": " + err.getMessage()))
                    .orElse(HashSet::new),
                this.data.getId()
            );

            final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_BATCH_EVALUATION_MODAL,
                data).orElse(new AnchorPane()));

            modal.setScene(scene);
            modal.centerOnScreen();
            modal.showAndWait();

            if (modal.getUserData() != null) {
                this.substituteView(ViewType.BATCH_DETAIL,
                    this.getController().getBatchController().getDetailBatchViewModelById(this.data.getId()).getValue());
            }
    }

    @Override
    protected SubView getSubView() {
        return this.batchDetailSubView;
    }

    /**
     * Goes back to the production view.
        *
        * @param event the occurred event.
        */
    public void goBackButtonClicked(final ActionEvent event) {
        this.substituteView(ViewType.PRODUCTION, this.getController().getProductionViewModel());
    }

    /**
     * Opens a modal that let the user go to the next production step.
     *
     * @param event the occurred event.
     */
    public void goToNextStepButtonClicked(final ActionEvent event) {
        final Stage modal = new Stage();
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
