package nwoolcan.view.review;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import org.apache.commons.lang3.tuple.Pair;

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
        this.maxScore.setText(String.valueOf(data.getMaxScore()));
        this.title.setText(data.getName());
        this.title.setExpanded(false);
    }

    /**
     * Returns properties of this controller.
     * @return properties of this controller.
     */
    public ReadOnlyObjectWrapper<Pair<ReadOnlyStringProperty, ReadOnlyStringProperty>> getInputProperty() {
        return new ReadOnlyObjectWrapper<>(Pair.of(this.score.textProperty(), this.notes.textProperty()));
    }
}
