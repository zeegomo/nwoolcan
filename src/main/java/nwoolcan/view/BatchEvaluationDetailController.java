package nwoolcan.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Result;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;

import java.util.logging.Logger;

/**
 * Controller for BatchEvaluationDetail.
 */
@SuppressWarnings("NullAway")
public final class BatchEvaluationDetailController extends SubViewController
    implements InitializableController<BatchEvaluationDetailViewModel>  {

    private static final String LOAD_FAILED = "Load failed";
    @FXML
    private SubViewContainer container;

    @FXML
    private VBox categories;

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
    }

    private Node evaluationNode(final EvaluationViewModel data) {
        return this.getViewManager().getView(ViewType.EVALUATION, data)
                   .peekError(err -> Logger.getGlobal().severe(err.toString() + "\n" + err.getCause()))
                   .orElse(new Label(LOAD_FAILED));
    }

    @Override
    protected SubView getSubView() {
        return null;
    }
}
