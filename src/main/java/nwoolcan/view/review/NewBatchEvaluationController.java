package nwoolcan.view.review;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SuppressWarnings("NullAway")
public class NewBatchEvaluationController extends SubViewController implements InitializableController<List<BatchEvaluationType>> {
    private static final String LOAD_FAILED = "Load failed";
    private static final String PARSE_FAILED = "Parse failed";
    @FXML
    private SubView modalSubView;
    @FXML
    private ComboBox<BatchEvaluationTypeProperty> batchTypeComboBox;
    @FXML
    private VBox categories;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private TextField reviewerTextField;

    private final Map<EvaluationType, Pair<TextField, TextArea>> evaluations = new HashMap<>();

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
        type.getCategories().forEach(cat -> {
            Pair<Parent, EvaluationTypeController> res = categoryNode(cat);
            this.categories.getChildren().add(res.getLeft());
            this.evaluations.put(cat, Pair.of(res.getRight().getScore(), res.getRight().getNotes()));
        });
    }
    /**
     *
     */
    public void createBatchReviewClick() {
        BatchEvaluationType type = this.batchTypeComboBox.getSelectionModel().getSelectedItem().getType();
        Result<Set<Triple<EvaluationType,
            Integer,
            Optional<String>>>> cat = this.evaluations.entrySet()
                                                      .stream()
                                                      .map(entry -> Triple.of(
                                                          entry.getKey(),
                                                          Results.ofChecked(() -> Integer.parseInt(entry.getValue().getLeft().getText())),
                                                          Optional.ofNullable(entry.getValue().getRight().getText())))
                                                      .reduce(
                                                          Result.of(new HashSet<>()),
                                                          (res, triple) -> res.flatMap(r -> triple.getMiddle().map(rr -> {
                                                              r.add(Triple.of(
                                                                  triple.getLeft(),
                                                                  rr,
                                                                  triple.getRight()));
                                                              return r;
                                                          })),
                                                          (res1, res2) -> res1.flatMap(r1 -> res2.peek(r2 -> r2.addAll(r1))));
        cat.map(eval -> new BatchEvaluationDTO(type, eval, this.notesTextArea.getText(), this.reviewerTextField.getText()))
           .peekError(err -> this.showAlertAndWait(err.getMessage()));
        System.out.println(cat);
    }

    private Pair<Parent, EvaluationTypeController> categoryNode(final EvaluationType type) {
        return this.getViewManager().getView(ViewType.EVALUATION_TYPE, type, EvaluationTypeController.class)
            .peekError(err -> Logger.getGlobal().severe(err.toString() + "\n" + err.getCause()))
            .orElse(Pair.of(new Label(LOAD_FAILED), new EvaluationTypeController(this.getController(), this.getViewManager())));
    }

    private void showAlertAndWait(final String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred while creating the batch.\n" + message, ButtonType.CLOSE);
        a.showAndWait();
    }
}
