package nwoolcan.view.production;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.step.parameter.ParameterType;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.viewmodel.brewery.production.step.DetailStepViewModel;
import nwoolcan.viewmodel.brewery.production.step.RegisterParameterDTO;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * View controller for step detail view.
 */
@SuppressWarnings("NullAway")
public final class StepDetailController
    extends SubViewController
    implements InitializableController<DetailStepViewModel> {

    @FXML
    private Label unitOfMeasureSymbolLabel;
    @FXML
    private ComboBox<ParameterType> parameterTypesComboBox;
    @FXML
    private TextField newParameterValueTextField;
    @FXML
    private VBox parametersGraphicsVBox;
    @FXML
    private Label durationLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label initialDateLabel;
    @FXML
    private Label finalizedLabel;
    @FXML
    private Label stepTypeNameLabel;
    @FXML
    private Button registerParameterButton;
    @FXML
    private SubView stepDetailSubView;

    private DetailStepViewModel data;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public StepDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final DetailStepViewModel data) {
        this.data = data;
        this.registerParameterButton.setDisable(data.isFinalized() || data.getPossibleParametersToRegister().size() == 0);

        this.stepTypeNameLabel.setText(data.getTypeName());
        this.initialDateLabel.setText(data.getStartDate().toString());
        this.endDateLabel.setText(data.getEndDate() == null ? "" : data.getEndDate().toString());

        final long durationMillis = data.getEndDate() == null ? 0 : data.getEndDate().getTime() - data.getStartDate().getTime();
        this.durationLabel.setText(durationMillis == 0 ? "" : this.getDurationBreakdown(durationMillis));
        this.finalizedLabel.setText(data.isFinalized() ? "Yes" : "No");

        this.parameterTypesComboBox.setItems(FXCollections.observableList(data.getPossibleParametersToRegister()));
        this.parameterTypesComboBox.setConverter(new StringConverter<ParameterType>() {
            @Override
            public String toString(final ParameterType object) {
                return object.getName();
            }

            @Override
            public ParameterType fromString(final String string) {
                return null;
            }
        });
        this.parameterTypesComboBox.getSelectionModel().selectedItemProperty().addListener((opt, oldV, newV) ->
            this.unitOfMeasureSymbolLabel.setText(newV.getUnitOfMeasure().getSymbol())
        );
        this.parameterTypesComboBox.getSelectionModel().selectFirst();

        data.getMapTypeToRegistrations().forEach((paramName, params) -> {
            final BorderPane pane = new BorderPane();
            this.parametersGraphicsVBox.getChildren().add(pane);

            final Label title = new Label(paramName);
            BorderPane.setAlignment(title, Pos.CENTER);
            pane.setTop(title);

            final VBox generalInfoVBox = new VBox();

            final VBox initialValueVBox = new VBox();
            initialValueVBox.getChildren().add(new Label("Initial value: "));
            initialValueVBox.getChildren().add(new Label(params.get(params.size() - 1).getValue().toString()));

            final VBox currentValueVBox = new VBox();
            currentValueVBox.getChildren().add(new Label("Current value : "));
            currentValueVBox.getChildren().add(new Label(params.get(0).getValue().toString()));

            final VBox mediumValueVBox = new VBox();
            mediumValueVBox.getChildren().add(new Label("Medium value: "));
            mediumValueVBox.getChildren().add(new Label(String.format("%.2f", params.stream()
                                                                             .mapToDouble(p -> p.getValue()
                                                                                                .doubleValue())
                                                                             .average()
                                                                             .getAsDouble())));

            generalInfoVBox.getChildren().add(initialValueVBox);
            generalInfoVBox.getChildren().add(currentValueVBox);
            generalInfoVBox.getChildren().add(mediumValueVBox);

            pane.setLeft(generalInfoVBox);
        });
    }

    @Override
    protected SubView getSubView() {
        return this.stepDetailSubView;
    }

    /**
     * Goes back to batch detail.
     * @param event the occurred event.
     */
    public void goBackButtonClicked(final ActionEvent event) {
        //TODO refactor with reload
        this.substituteView(ViewType.BATCH_DETAIL,
            this.getController().getBatchController().getDetailBatchViewModelById(data.getBatchId()).getValue());
    }

    /**
     * Registers a parameter in this step.
     * @param event the occurred event.
     */
    public void registerParameterButtonClicked(final ActionEvent event) {
        double value;
        try {
            value = Double.parseDouble(this.newParameterValueTextField.getText());
        } catch (NumberFormatException ex) {
            this.showAlertAndWait("Parameter value must be a number!");
            return;
        }

        this.getController().getBatchController().getStepController().registerParameter(
            new RegisterParameterDTO(
                data.getBatchId(),
                value,
                this.parameterTypesComboBox.getSelectionModel().getSelectedItem(),
                new Date())
        );

        this.substituteView(ViewType.STEP_DETAIL,
            this.getController().getBatchController().getStepController().getDetailStepViewModel(
                data.getBatchId(),
                data.getTypeName()
            ).getValue());
    }

    /* Utils function from stackoverflow.
       https://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
     */
    private String getDurationBreakdown(final long millis) {
        long cMillis = millis;
        long days = TimeUnit.MILLISECONDS.toDays(cMillis);
        cMillis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(cMillis);
        cMillis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(cMillis);
        cMillis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(cMillis);

        final StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return sb.toString();
    }

    private void showAlertAndWait(final String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred while registering the parameter.\n" + message, ButtonType.CLOSE);
        a.showAndWait();
    }
}
