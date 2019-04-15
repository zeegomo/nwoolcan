package nwoolcan.view.review;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Controller for BatchEvaluationDetail.
 */
@SuppressWarnings("NullAway")
public final class BatchEvaluationDetailController extends SubViewController
    implements InitializableController<BatchEvaluationDetailViewModel> {

    private static final String LOAD_FAILED = "Load failed";
    private static final Double WRAP = 1.50;
    @FXML
    private SubViewContainer container;
    @FXML
    private SubView batchEvaluationDetailSubView;
    @FXML
    private VBox categories;
    @FXML
    private ScrollPane categoriesScrollPane;
    @FXML
    private Text notes;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public BatchEvaluationDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final BatchEvaluationDetailViewModel data) {
        Result<Parent> view = this.getViewManager().getView(ViewType.BATCHEVALUATION, data.getInfo());
        view.peek(container::substitute)
            .peekError(err -> Logger.getGlobal().severe(err::toString));
        data.getCategories()
            .forEach(cat -> categories.getChildren().add(evaluationNode(cat)));
        this.notes.setText(data.getInfo().getNotes().orElse(""));
        this.notes.wrappingWidthProperty().bind(this.batchEvaluationDetailSubView.widthProperty().divide(2));
        this.categoriesScrollPane.prefHeightProperty().bind(this.batchEvaluationDetailSubView.heightProperty().divide(WRAP));
    }

    private Node evaluationNode(final EvaluationViewModel data) {
        return this.getViewManager().getView(ViewType.EVALUATION, data)
                   .peekError(err -> Logger.getGlobal().severe(err.toString() + "\n" + err.getCause()))
                   .orElse(new Label(LOAD_FAILED));
    }

    /**
     *
     */
    public void newEvaluationClick() {
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
    }

    @Override
    protected SubView getSubView() {
        return this.batchEvaluationDetailSubView;
    }
}
