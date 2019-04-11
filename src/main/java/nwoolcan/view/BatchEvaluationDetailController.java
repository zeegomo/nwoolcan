package nwoolcan.view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Result;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;

import java.util.logging.Logger;

@SuppressWarnings("NullAway")
public final class BatchEvaluationDetailController extends SubViewController
    implements InitializableController<BatchEvaluationDetailViewModel>  {

    @FXML
    private SubViewContainer container;

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
    }

    @Override
    protected SubView getSubView() {
        return null;
    }
}
