package nwoolcan.view.production;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * View controller of go next step modal view.
 */
@SuppressWarnings("NullAway")
public final class GoNextStepModalController
    extends AbstractViewController
    implements InitializableController<GoNextStepViewModel> {

    @FXML
    private TextArea notesTextArea;

    @FXML
    private TextField endSizeValueTextField;
    @FXML
    private Label endSizeUnitOfMeasureSymbolLabel;
    @FXML
    private ComboBox<UnitOfMeasure> endSizeUnitOfMeasureComboBox;

    @FXML
    private CheckBox chooseFinalizeNextStepCheckBox;
    @FXML
    private ComboBox<StepType> nextStepTypesComboBox;

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public GoNextStepModalController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final GoNextStepViewModel data) {
        this.nextStepTypesComboBox.setItems(FXCollections.observableList(
            new ArrayList<>(data.getNextPossibleStepTypes())
        ));
    }
}
