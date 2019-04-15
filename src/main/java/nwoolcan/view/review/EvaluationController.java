package nwoolcan.view.review;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import nwoolcan.view.InitializableController;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;
/**
 * Controller for single evaluation displayed in {@link BatchEvaluationDetailController}.
 */
@SuppressWarnings("NullAway")
public final class EvaluationController implements InitializableController<EvaluationViewModel> {
    @FXML
    private TitledPane title;
    @FXML
    private Label score;
    @FXML
    private Text notes;

    @Override
    public void initData(final EvaluationViewModel data) {
        title.setText(data.getType());
        title.setCollapsible(false);
        score.setText(formatScore(data));
        notes.setText(formatNotes(data));
    }

    private String formatScore(final EvaluationViewModel data) {
        return data.getScore() + "/" + data.getMaxScore();
    }

    private String formatNotes(final EvaluationViewModel data) {
        return data.getNotes().orElse("");
    }
}
