package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.utils.ViewManager;
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
    private Label lblMinute;
    @FXML
    private Label lblHour;
    @FXML
    private ComboBox<Integer> recordMinute;
    @FXML
    private ComboBox<Integer> recordHour;
    @FXML
    private DatePicker recordDatePicker;
    @FXML
    private CheckBox checkSelectDate;
    @FXML
    private ComboBox<Record.Action> recordAction;
    @FXML
    private TextField recordAmount;
    @FXML
    private Label lblUom;

    /**
     * Creates itself and inject the controller and the view manager.
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewRecordModalViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final Integer stockId) {
        this.stockId = stockId;
        recordAction.getItems().setAll(Record.Action.values());
        recordAction.getSelectionModel().selectLast();
        recordHour.getItems().setAll(IntStream.rangeClosed(FIRST_HOUR, LAST_HOUR)
                                              .boxed()
                                              .collect(Collectors.toList()));
        recordHour.getSelectionModel().select(MIDDLE_HOUR_INDEX);
        recordMinute.getItems().setAll(IntStream.rangeClosed(FIRST_MINUTE, LAST_MINUTE)
                                                .boxed()
                                                .collect(Collectors.toList()));
        recordMinute.getSelectionModel().select(MIDDLE_MINUTE_INDEX);
        checkSelectDate.setSelected(false);
        lblUom.setText(getController().getWarehouseController()
                                      .getViewStockById(stockId)
                                      .getValue()
                                      .getArticle()
                                      .getUnitOfMeasure()
                                      .getSymbol());
        specifyDateClick(new ActionEvent());
    }

    @FXML
    private void specifyDateClick(final ActionEvent actionEvent) {
        recordDatePicker.setDisable(!checkSelectDate.isSelected());
        recordHour.setDisable(!checkSelectDate.isSelected());
        recordMinute.setDisable(!checkSelectDate.isSelected());
        lblMinute.setDisable(!checkSelectDate.isSelected());
        lblHour.setDisable(!checkSelectDate.isSelected());
        recordDatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void addRecordClick(final ActionEvent actionEvent) {
        final double recordDoubleAmount;
        try {
            recordDoubleAmount = Double.parseDouble(recordAmount.getText());
        } catch (final NumberFormatException ex) {
            this.showErrorAndWait("The amount must be a number.",
                this.lblUom.getScene().getWindow()); // You can use any other control
            return;
        }
        final Result<Empty> addRecordResult;
        if (checkSelectDate.isSelected()) {
            Date date =  Date.from(recordDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            date = DateUtils.truncate(date, Calendar.DATE);
            date = DateUtils.setHours(date, recordHour.getValue());
            date = DateUtils.setMinutes(date, recordMinute.getValue());
            addRecordResult = getController().getWarehouseController().addRecord(stockId, recordDoubleAmount, recordAction.getValue(), date);
        } else {
            addRecordResult = getController().getWarehouseController().addRecord(stockId, recordDoubleAmount, recordAction.getValue());
        }
        if (addRecordResult.isError()) {
            this.showErrorAndWait("Error: " + addRecordResult.getError().getMessage(),
                this.lblUom.getScene().getWindow()); // You can use any other control
        } else {
            ((Stage) this.recordDatePicker.getScene().getWindow()).close();
        }
    }
}
