package nwoolcan.view.review;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.view.InitializableController;

@SuppressWarnings("NullAway")
public class EvaluationTypeController implements InitializableController<EvaluationType> {
    @FXML
    private TextField score;
    @FXML
    private Label maxScore;
    @FXML
    private TextArea notes;
    @FXML
    private TitledPane title;

    @Override
    public void initData(final EvaluationType data) {
        this.maxScore.setText(String.valueOf(data.getMaxScore()));
        this.title.setText(data.getName());
    }
}
