package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.utils.Result;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.PlainStockViewModel;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Modal of the new stock view used to create a stock.
 */
@SuppressWarnings("NullAway")
public final class NewStockModalViewController extends AbstractViewController {

    private static final int FIRST_HOUR = 0;
    private static final int LAST_HOUR = 23;
    private static final int MIDDLE_HOUR_INDEX = 13;
    private static final int FIRST_MINUTE = 0;
    private static final int MIDDLE_MINUTE_INDEX = 31;
    private static final int LAST_MINUTE = 60;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<AbstractArticleViewModel> comboBoxArticle;
    @FXML
    private CheckBox checkBoxDate;
    @FXML
    private ComboBox<Integer> comboMinuteSelection;
    @FXML
    private ComboBox<Integer> comboHourSelection;
    @FXML
    private VBox boxDateTimePicker;

    /**
     * Creates itself and inject the controller and the view manager.
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewStockModalViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @FXML
    private void initialize() {
        final QueryArticle queryArticle = new QueryArticleBuilder().setExcludeArticleType(ArticleType.FINISHED_BEER).build();
        final List<AbstractArticleViewModel> articles = getController().getWarehouseController().getArticles(queryArticle);
        comboBoxArticle.getItems().setAll(articles);
        comboBoxArticle.getSelectionModel().selectFirst();
        comboHourSelection.getItems().setAll(IntStream.rangeClosed(FIRST_HOUR, LAST_HOUR)
                                                      .boxed()
                                                      .collect(Collectors.toList()));
        comboHourSelection.getSelectionModel().select(MIDDLE_HOUR_INDEX);
        comboMinuteSelection.getItems().setAll(IntStream.rangeClosed(FIRST_MINUTE, LAST_MINUTE)
                                                        .boxed()
                                                        .collect(Collectors.toList()));
        comboMinuteSelection.getSelectionModel().select(MIDDLE_MINUTE_INDEX);
    }

    @FXML
    private void specifyDateClick(final ActionEvent actionEvent) {
        boxDateTimePicker.setManaged(checkBoxDate.isSelected());
        boxDateTimePicker.setVisible(checkBoxDate.isSelected());
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void createStockClick(final ActionEvent actionEvent) {
        Result<PlainStockViewModel> stockResult;
        if (checkBoxDate.isSelected()) {
            final Date date =  Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            DateUtils.truncate(date, Calendar.DATE);
            DateUtils.setHours(date, comboHourSelection.getValue());
            DateUtils.setMinutes(date, comboHourSelection.getValue());
            stockResult = getController().getWarehouseController()
                                         .createStock(comboBoxArticle.getValue().getId(), date);
        } else {
            stockResult = getController().getWarehouseController()
                                         .createStock(comboBoxArticle.getValue().getId());
        }
        if (stockResult.isError()) {
            new Alert(
                Alert.AlertType.ERROR,
                "Internal Error: " + stockResult.getError().getMessage(),
                ButtonType.CLOSE
            ).showAndWait();
        } else {
            ((Stage) this.datePicker.getScene().getWindow()).close();
        }
    }
}
