package nwoolcan.view.production;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.viewmodel.brewery.production.step.DetailStepViewModel;
import nwoolcan.viewmodel.brewery.production.step.ParameterViewModel;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("NullAway")
public class StepDetailController
    extends SubViewController
    implements InitializableController<DetailStepViewModel> {

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
        this.registerParameterButton.setDisable(data.isFinalized());

        this.stepTypeNameLabel.setText(data.getTypeName());
        this.initialDateLabel.setText(data.getStartDate().toString());
        this.endDateLabel.setText(data.getEndDate() == null ? "" : data.getEndDate().toString());

        final long durationMillis = data.getEndDate() == null ? 0 : data.getEndDate().getTime() - data.getStartDate().getTime();
        this.durationLabel.setText(durationMillis == 0 ? "" : this.getDurationBreakdown(durationMillis));
        this.finalizedLabel.setText(data.isFinalized() ? "Yes" : "No");

        data.getMapTypeToRegistrations().forEach((paramName, params) -> {
            final BorderPane pane = new BorderPane();
            this.parametersGraphicsVBox.getChildren().add(pane);

            final Label title = new Label(paramName);
            BorderPane.setAlignment(title, Pos.CENTER);
            pane.setTop(title);

            final VBox generalInfoVBox = new VBox();

            final VBox initialValueVBox = new VBox();
            initialValueVBox.getChildren().add(new Label("Initial value: "));
            initialValueVBox.getChildren().add(new Label(params.get(0).getValue().toString()));

            final VBox currentValueVBox = new VBox();
            currentValueVBox.getChildren().add(new Label("Current value : "));
            currentValueVBox.getChildren().add(new Label(params.get(params.size() - 1).getValue().toString()));

            final VBox mediumValueVBox = new VBox();
            mediumValueVBox.getChildren().add(new Label("Medium value: "));
            mediumValueVBox.getChildren().add(new Label(params.stream()
                                                              .mapToDouble(p -> p.getValue().doubleValue())
                                                              .average().toString()));

            generalInfoVBox.getChildren().add(initialValueVBox);
            generalInfoVBox.getChildren().add(currentValueVBox);
            generalInfoVBox.getChildren().add(mediumValueVBox);

            pane.setRight(generalInfoVBox);
        });
    }

    @Override
    protected SubView getSubView() {
        return this.stepDetailSubView;
    }

    public void goBackButtonClicked(final ActionEvent event) {
        this.previousView(); //TODO refactor with reload
    }

    public void registerParameterButtonClicked(final ActionEvent event) {

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
}
