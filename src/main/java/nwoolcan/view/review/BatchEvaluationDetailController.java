package nwoolcan.view.review;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
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

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Controller for BatchEvaluationDetail.
 */
@SuppressWarnings("NullAway")
public final class BatchEvaluationDetailController extends SubViewController
    implements InitializableController<BatchEvaluationDetailViewModel> {

    private static final double VIEW_TO_COMPONENTS_RATIO = 3;

    private static final String LOAD_FAILED = "Load failed";
    @FXML
    private SubViewContainer container;
    @FXML
    private SubView batchEvaluationDetailSubView;
    @FXML
    private VBox categories;
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
        Result<Parent> view = this.getViewManager().getView(ViewType.BATCH_EVALUATION, data.getInfo());
        view.peek(container::substitute)
            .peekError(err -> Logger.getGlobal().severe(err::toString));
        List<TitledPane> panes = data.getCategories()
                                     .stream()
                                     .map(cat -> new TitledPane(cat.getType(), evaluationNode(cat)))
                                     .collect(Collectors.toList());

        Accordion acc = new Accordion(panes.stream().toArray(TitledPane[]::new));
        panes.stream().findFirst().ifPresent(acc::setExpandedPane);

        this.categories.getChildren().add(acc);
        this.notes.setText(data.getInfo().getNotes().orElse(""));
        this.notes.wrappingWidthProperty()
                  .bind(this.batchEvaluationDetailSubView.widthProperty().divide(VIEW_TO_COMPONENTS_RATIO));
    }

    private Node evaluationNode(final EvaluationViewModel data) {
        return EvaluationView.builder().bindWidth(this.batchEvaluationDetailSubView.widthProperty()
                                                                                   .divide(VIEW_TO_COMPONENTS_RATIO))
                                          .displayValues(data)
                                          .enableInput(false)
                                          .build(this.getViewManager());
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
