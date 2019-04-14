package nwoolcan.view.review;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.Evaluation;
import nwoolcan.model.brewery.production.batch.review.EvaluationType;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;

import java.util.List;
import java.util.logging.Logger;
@SuppressWarnings("NullAway")
public class NewBatchEvaluationController extends AbstractViewController implements InitializableController<List<BatchEvaluationType>> {
    private static final String LOAD_FAILED = "Load failed";
    @FXML
    private ComboBox<BatchEvaluationType> batchTypeComboBox;
    @FXML
    private VBox categories;
    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    protected NewBatchEvaluationController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final List<BatchEvaluationType> data) {
        batchTypeComboBox.setItems(FXCollections.observableList(data));
    }

    /**
     *
     */
    public void changeBatchTypeClick() {
        BatchEvaluationType type = this.batchTypeComboBox.getSelectionModel().getSelectedItem();
        this.categories.getChildren().clear();
        type.getCategories().forEach(cat -> this.categories.getChildren().add(categoryNode(cat)));
    }

    private Node categoryNode(final EvaluationType type) {
        return this.getViewManager().getView(ViewType.EVALUATION_TYPE, type)
            .peekError(err -> Logger.getGlobal().severe(err.toString() + "\n" + err.getCause()))
            .orElse(new Label(LOAD_FAILED));
    }
}
