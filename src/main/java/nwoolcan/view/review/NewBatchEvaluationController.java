package nwoolcan.view.review;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SuppressWarnings("NullAway")
public class NewBatchEvaluationController extends SubViewController implements InitializableController<List<BatchEvaluationType>> {
    private static final String LOAD_FAILED = "Load failed";
    @FXML
    private SubView modalSubView;
    @FXML
    private ComboBox<BatchEvaluationTypeProperty> batchTypeComboBox;
    @FXML
    private VBox categories;


    private static final class BatchEvaluationTypeProperty {
        private final BatchEvaluationType type;

        private BatchEvaluationTypeProperty(final BatchEvaluationType type) {
            this.type = type;
        }

        private BatchEvaluationType getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return this.type.getName();
        }
    }
    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewBatchEvaluationController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    protected SubView getSubView() {
        return this.modalSubView;
    }

    @Override
    public void initData(final List<BatchEvaluationType> data) {
        batchTypeComboBox.setItems(FXCollections.observableList(data.stream()
                                                                    .map(BatchEvaluationTypeProperty::new)
                                                                    .collect(Collectors.toList())));
    }

    /**
     *
     */
    public void changeBatchTypeClick() {
        BatchEvaluationType type = this.batchTypeComboBox.getSelectionModel().getSelectedItem().getType();
        this.categories.getChildren().clear();
        type.getCategories().forEach(cat -> this.categories.getChildren().add(categoryNode(cat)));
    }


    /**
     *
     */
    public void createBatchReviewClick() {
    }

    private Node categoryNode(final EvaluationType type) {
        return this.getViewManager().getView(ViewType.EVALUATION_TYPE, type)
            .peekError(err -> Logger.getGlobal().severe(err.toString() + "\n" + err.getCause()))
            .orElse(new Label(LOAD_FAILED));
    }
}
