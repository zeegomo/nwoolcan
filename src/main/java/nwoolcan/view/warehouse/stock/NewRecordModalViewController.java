package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * View Controller to add a new record.
 */
@SuppressWarnings("NullAway")
public final class NewRecordModalViewController extends AbstractViewController implements InitializableController<Integer> {

    private static final int FIRST_HOUR = 0;
    private static final int LAST_HOUR = 23;
    private static final int MIDDLE_HOUR_INDEX = 12;
    private static final int FIRST_MINUTE = 0;
    private static final int MIDDLE_MINUTE_INDEX = 30;
    private static final int LAST_MINUTE = 60;

    private int stockId;


    @FXML
    private ComboBox<Integer> recordMinute;
    @FXML
    private ComboBox<Integer> recordHour;
    @FXML
    private DatePicker recordDatePicker;
    @FXML
    private VBox dateVBox;
    @FXML
    private CheckBox checkSelectDate;
    @FXML
    private ComboBox<Record.Action> recordAction;
    @FXML
    private TextField recordAmount;

    /**
     * Creates itself and inject the controller and the view manager.
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewRecordModalViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }


    @FXML
    private void addRecordClick(final ActionEvent actionEvent) {
        final double recordDoubleAmount;
        try {
            recordDoubleAmount = Double.parseDouble(recordAmount.getText());
        } catch (final NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "The amount must be a double number.", ButtonType.CLOSE).showAndWait();
            return;
        }
        final Result<Empty> addRecordResult;
        if (checkSelectDate.isSelected()) {
            final Date date =  Date.from(recordDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            DateUtils.truncate(date, Calendar.DATE);
            DateUtils.setHours(date, recordHour.getValue());
            DateUtils.setMinutes(date, recordMinute.getValue());
            addRecordResult = getController().getWarehouseController().addRecord(stockId, recordDoubleAmount, recordAction.getValue(), date);
        } else {
            addRecordResult = getController().getWarehouseController().addRecord(stockId, recordDoubleAmount, recordAction.getValue());
        }
        if (addRecordResult.isError()) {
            new Alert(
                Alert.AlertType.ERROR,
                "Internal Error: " + addRecordResult.getError().getMessage(),
                ButtonType.CLOSE
            ).showAndWait();
        } else {
            ((Stage) this.recordDatePicker.getScene().getWindow()).close();
        }
    }

    @Override
    public void initData(final Integer stockId) {
        this.stockId = stockId;
        recordHour.getItems().setAll(IntStream.rangeClosed(FIRST_HOUR, LAST_HOUR)
                                              .boxed()
                                              .collect(Collectors.toList()));
        recordHour.getSelectionModel().select(MIDDLE_HOUR_INDEX);
        recordMinute.getItems().setAll(IntStream.rangeClosed(FIRST_MINUTE, LAST_MINUTE)
                                                .boxed()
                                                .collect(Collectors.toList()));
        recordMinute.getSelectionModel().select(MIDDLE_MINUTE_INDEX);
        checkSelectDate.setSelected(false);
    }

    @FXML
    private void specifyDateClick(final ActionEvent actionEvent) {
        dateVBox.setManaged(checkSelectDate.isSelected());
        dateVBox.setVisible(checkSelectDate.isSelected());
        recordDatePicker.setValue(LocalDate.now());
    }
}
