package nwoolcan.view.review;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDTO;
import nwoolcan.viewmodel.brewery.production.batch.review.NewBatchEvaluationViewModel;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * Controller for new Batch Evaluation modal.
 */
@SuppressWarnings("NullAway")
public final class NewBatchEvaluationController extends SubViewController
    implements InitializableController<NewBatchEvaluationViewModel> {
    private static final String LOAD_FAILED = "Load failed";
    private static final String SCORE_PARSE_FAILED = "Invalid score";
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

    private final Map<EvaluationType, Pair<ReadOnlyStringProperty, ReadOnlyStringProperty>> evaluations = new HashMap<>();
    private int id;

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
    public void initData(final NewBatchEvaluationViewModel data) {
        batchTypeComboBox.setItems(FXCollections.observableList(data.getTypes().stream()
                                                                    .map(BatchEvaluationTypeProperty::new)
                                                                    .collect(Collectors.toList())));
        this.id = data.getID();
        batchTypeComboBox.getSelectionModel().selectFirst();
        changeBatchTypeClick();
    }

    /**
     * Change categories based on review type.
     */
    public void changeBatchTypeClick() {
        BatchEvaluationType type = this.batchTypeComboBox.getSelectionModel().getSelectedItem().getType();
        this.categories.getChildren().clear();
        type.getCategories().forEach(cat -> {
            Pair<Parent, EvaluationInputController> res = categoryNode(cat);
            this.categories.getChildren().add(res.getLeft());
            this.evaluations.put(cat, Pair.of(
                res.getRight().getInputProperty().get().getLeft(),
                res.getRight().getInputProperty().get().getRight())
            );
        });
    }
    /**
     * Create new batch.
     */
    public void createBatchReviewClick() {
        BatchEvaluationType type = this.batchTypeComboBox.getSelectionModel().getSelectedItem().getType();
        Result<Set<Triple<EvaluationType, Integer, Optional<String>>>> cat
            = this.evaluations.entrySet()
                              .stream()
                              .map(entry -> Triple.of(
                                  entry.getKey(),
                                  Results.ofChecked(() -> {
                                      //throw exception if there no value
                                      if (entry.getValue().getLeft().get().isEmpty()) {
                                          throw new IllegalArgumentException("Empty value in " + entry.getKey()
                                                                                                      .getName());
                                      }
                                      return Integer.parseInt(entry.getValue().getLeft().get());
                                  }).mapError(err ->
                                      //give more understandable error
                                      new IllegalArgumentException(SCORE_PARSE_FAILED + "\n" + err.getMessage())),
                                  Optional.ofNullable(entry.getValue().getRight().get())))
                              .reduce(
                                  Result.of(new HashSet<>()),
                                  //propagate error in set
                                  (res, triple) -> res.flatMap(r -> triple.getMiddle()
                                                                          .map(rr -> {
                                                                              r.add(Triple.of(
                                                                                  triple.getLeft(),
                                                                                  rr,
                                                                                  triple.getRight()));
                                                                              return r;
                                                                          })),
                                  (res1, res2) -> res1.require(res2::isPresent, res2::getError)
                                                      .peek(col -> col.addAll(res2.getValue())));
        cat.map(eval -> new BatchEvaluationDTO(type, eval, this.notesTextArea.getText(), this.reviewerTextField.getText()))
           .map(dto -> this.getController().getBatchController().addBatchEvaluation(this.id, dto))
           .peekError(err -> this.showAlertAndWait(err.getMessage()));
        System.out.println(cat);
    }

    private Pair<Parent, EvaluationInputController> categoryNode(final EvaluationType type) {
        return this.getViewManager().getView(ViewType.EVALUATION_TYPE, type, EvaluationInputController.class)
                   .peekError(err -> Logger.getGlobal().severe(err.toString() + "\n" + err.getCause()))
                   .orElse(Pair.of(new Label(LOAD_FAILED), new EvaluationInputController(this.getController(), this.getViewManager())));
    }

    private void showAlertAndWait(final String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred while creating the review.\n" + message, ButtonType.CLOSE);
        a.showAndWait();
    }
}
