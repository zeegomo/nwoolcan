package nwoolcan.view.review;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;

@SuppressWarnings("NullAway")
public class EvaluationTypeController extends AbstractViewController implements InitializableController<EvaluationType> {
    @FXML
    private TextField score;
    @FXML
    private Label maxScore;
    @FXML
    private TextArea notes;
    @FXML
    private TitledPane title;

    private EvaluationType type;

    public class EvaluationTypeControllerProperty {
        public String getNotes() {
            return notes.getText();
        }
        public Result<Integer> getScore() {
            return Results.ofChecked(() -> Integer.parseInt(score.getText())).;
        }
    }

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public EvaluationTypeController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final EvaluationType data) {
        this.type = data;
        this.maxScore.setText(String.valueOf(data.getMaxScore()));
        this.title.setText(data.getName());
    }

    public TextField getScore() {
        return this.score;
    }

    public TextArea getNotes() {
        return this.notes;
    }
}
