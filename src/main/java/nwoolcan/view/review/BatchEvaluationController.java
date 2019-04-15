package nwoolcan.view.review;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationViewModel;

/**
 * Controller for BatchEvaluation.
 */
@SuppressWarnings("NullAway")
public final class BatchEvaluationController
    extends SubViewController
    implements InitializableController<BatchEvaluationViewModel> {

    private static final String UNSPECIFIED_REVIEWER = "Unavailable";
    @FXML
    private SubView batchEvaluationSubView;
    @FXML
    private Label lblEvaluationTypeName;
    @FXML
    private Label lblEvaluationScore;
    @FXML
    private Label lblEvaluationMaxScore;
    @FXML
    private Label lblReviewer;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public BatchEvaluationController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }


    @Override
    public void initData(final BatchEvaluationViewModel data) {
        this.lblEvaluationTypeName.setText(data.getType());
        this.lblEvaluationMaxScore.setText(Integer.toString(data.getMaxScore()));
        this.lblEvaluationScore.setText(Integer.toString(data.getScore()));
        this.lblReviewer.setText(data.getReviewer().orElse(UNSPECIFIED_REVIEWER));
     }

    @Override
    protected SubView getSubView() {
        return this.batchEvaluationSubView;
    }
}
