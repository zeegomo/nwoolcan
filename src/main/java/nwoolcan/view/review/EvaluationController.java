package nwoolcan.view.review;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import nwoolcan.view.InitializableController;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;
/**
 * Controller for single evaluation displayed in {@link BatchEvaluationDetailController}.
 */
@SuppressWarnings("NullAway")
public final class EvaluationController implements InitializableController<EvaluationViewModel> {
    @FXML
    private Label score;
    @FXML
    private Text notes;

    @Override
    public void initData(final EvaluationViewModel data) {
        score.setText(formatScore(data));
        notes.setText(formatNotes(data));
    }

    private String formatScore(final EvaluationViewModel data) {
        return data.getScore() + "/" + data.getMaxScore();
    }

    private String formatNotes(final EvaluationViewModel data) {
        return data.getNotes().orElse("");
    }

    /**
     * Return the width property of this view.
     * @return the width property of this view.
     */
    public DoubleProperty widthProperty() {
        return this.notes.wrappingWidthProperty();
    }
}
