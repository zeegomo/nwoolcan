package nwoolcan.view.review;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Controller for BatchEvaluationDetail.
 */
@SuppressWarnings("NullAway")
public final class BatchEvaluationDetailController extends SubViewController
    implements InitializableController<BatchEvaluationDetailViewModel> {

    private static final String LOAD_FAILED = "Load failed";
    @FXML
    private SubViewContainer container;
    @FXML
    private SubView batchEvaluationDetailSubView;
    @FXML
    private VBox categories;
    @FXML
    private Text notes;

    private final Logger logger;
    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public BatchEvaluationDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @Override
    public void initData(final BatchEvaluationDetailViewModel data) {
        Result<Parent> view = this.getViewManager().getView(ViewType.BATCH_EVALUATION, data.getInfo());
        view.peek(container::substitute)
            .peekError(err -> Logger.getGlobal().severe(err::toString));
        List<TitledPane> panes = data.getCategories()
                                     .stream()
                                     .map(cat -> new TitledPane(cat.getType(), evaluationNode(cat)))
                                     .collect(Collectors.toList());
        //data.getCategories()
        //    .forEach(cat -> categories.getChildren().add(evaluationNode(cat)));

        Accordion acc = new Accordion(panes.stream().toArray(TitledPane[]::new));
        panes.stream().findFirst().ifPresent(acc::setExpandedPane);
        this.categories.getChildren().add(acc);
        this.notes.setText(data.getInfo().getNotes().orElse(""));
        this.notes.wrappingWidthProperty().bind(this.batchEvaluationDetailSubView.widthProperty().divide(3));
    }

    private Node evaluationNode(final EvaluationViewModel data) {
        return this.getViewManager().<EvaluationViewModel, EvaluationController>getViewAndController(ViewType.EVALUATION, data)
                   .peekError(err -> logger.warning(err.toString() + "\n" + err.getCause()))
                   .peek(pair -> pair.getRight().widthProperty().bind(this.batchEvaluationDetailSubView.widthProperty().divide(3)))
                   .map(Pair::getLeft)
                   .orElse(new Label(LOAD_FAILED));
    }

    @Override
    protected SubView getSubView() {
        return this.batchEvaluationDetailSubView;
    }
    /**
     * Go back to the previous view.
     * @param event the recorded event.
     */
    public void goBackButtonClicked(final ActionEvent event) {
        this.previousView();
    }
}
