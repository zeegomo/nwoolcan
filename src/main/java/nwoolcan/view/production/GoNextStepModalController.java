package nwoolcan.view.production;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepDTO;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;

import java.util.ArrayList;

/**
 * View controller of go next step modal view.
 */
@SuppressWarnings("NullAway")
public final class GoNextStepModalController
    extends AbstractViewController
    implements InitializableController<GoNextStepViewModel> {

    private static final String NO_NEXT_STEP_TYPE_SELECTED_MESSAGE = "No next step type selected!";
    private static final String NO_UNIT_OF_MEASURE_SELECTED_MESSAGE = "No unit of measure selected for end size!";
    private static final String END_SIZE_MUST_BE_A_NUMBER_MESSAGE = "End size must be a number!";
    private static final String ERROR_GOING_TO_NEXT_STEP_MESSAGE = "An error occurred while going to the next step.";

    @FXML
    private VBox notesAndSizeVBox;
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

    private GoNextStepViewModel data;

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
        this.data = data;

        this.nextStepTypesComboBox.setItems(FXCollections.observableList(
            new ArrayList<>(data.getNextPossibleStepTypes())
        ));
        this.nextStepTypesComboBox.getSelectionModel().selectFirst();

        this.notesAndSizeVBox.visibleProperty().bind(
            this.chooseFinalizeNextStepCheckBox.selectedProperty()
        );

        this.endSizeUnitOfMeasureComboBox.setItems(FXCollections.observableList(
            data.getPossibleUnitsOfMeasure()
        ));

        this.endSizeUnitOfMeasureComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                this.endSizeUnitOfMeasureSymbolLabel.setText(newV.getSymbol());
            }
        });

        this.endSizeUnitOfMeasureComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Goes to next step for this batch.
     * @param event the occurred event.
     */
    public void goButtonClicked(final ActionEvent event) {
        if (this.nextStepTypesComboBox.getSelectionModel().getSelectedItem() == null) {
            this.showAlertAndWait(NO_NEXT_STEP_TYPE_SELECTED_MESSAGE);
            return;
        }

        Quantity endSize = null;

        if (this.chooseFinalizeNextStepCheckBox.isSelected()) {
            if (this.endSizeUnitOfMeasureComboBox.getSelectionModel().getSelectedItem() == null) {
                this.showAlertAndWait(NO_UNIT_OF_MEASURE_SELECTED_MESSAGE);
                return;
            }

            double endSizeValue;

            try {
                endSizeValue = Double.parseDouble(this.endSizeValueTextField.getText().trim());
            } catch (NumberFormatException ex) {
                this.showAlertAndWait(END_SIZE_MUST_BE_A_NUMBER_MESSAGE);
                return;
            }

            Result<Quantity> res = Quantity.of(endSizeValue, this.endSizeUnitOfMeasureComboBox.getSelectionModel().getSelectedItem());

            if (res.isPresent()) {
                endSize = res.getValue();
            } else {
                this.showAlertAndWait(res.getError().getMessage());
                return;
            }
        }

        this.getController().getBatchController().goToNextStep(data.getBatchId(), new GoNextStepDTO(
            this.nextStepTypesComboBox.getSelectionModel().getSelectedItem(),
            this.chooseFinalizeNextStepCheckBox.isSelected(),
            this.notesTextArea.getText().trim(),
            endSize))
            .peekError(e -> this.showAlertAndWait(e.getMessage()))
            .peek(e -> {
                final Stage stage = ((Stage) this.nextStepTypesComboBox.getScene().getWindow());
                //just for saying that i went to the next step to the caller
                stage.setUserData(new Object());
                stage.close();
            });
    }

    private void showAlertAndWait(final String message) {
        this.showErrorAndWait(ERROR_GOING_TO_NEXT_STEP_MESSAGE + "\n" + message,
            this.notesAndSizeVBox.getScene().getWindow());
    }
}
