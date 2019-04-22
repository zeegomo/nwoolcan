package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.PlainStockViewModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Modal of the new stock view used to create a stock.
 */
@SuppressWarnings("NullAway")
public final class NewStockModalViewController extends AbstractViewController {

    @FXML
    private Label uomInitialQuantity;
    @FXML
    private TextField textFieldInitialQuantity;
    @FXML
    private CheckBox checkBoxInitialQuantity;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<AbstractArticleViewModel> comboBoxArticle;
    @FXML
    private CheckBox checkBoxDate;

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
        specifyDateClick(new ActionEvent());
        specifyInitialQuantityClick(new ActionEvent());
        setUomInitialQuantity();
    }

    @FXML
    private void specifyDateClick(final ActionEvent actionEvent) {
        datePicker.setDisable(!checkBoxDate.isSelected());
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void createStockClick(final ActionEvent actionEvent) {
        Result<PlainStockViewModel> stockResult;
        if (checkBoxDate.isSelected()) {
            final Date date =  Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            stockResult = getController().getWarehouseController()
                                         .createStock(comboBoxArticle.getValue().getId(), date);
        } else {
            stockResult = getController().getWarehouseController()
                                         .createStock(comboBoxArticle.getValue().getId());
        }
        if (stockResult.isError()) {
            this.showErrorAndWait("Create stock internal error: " + stockResult.getError().getMessage(),
                                  ((Node) actionEvent.getTarget()).getScene().getWindow());
            return;
        }

        final int stockId = stockResult.getValue().getId();
        if (checkBoxInitialQuantity.isSelected()) {
            final Result<Double> recordDoubleAmountResult = Results.ofChecked(
                                                        () -> Double.parseDouble(textFieldInitialQuantity.getText().trim()));
            if (recordDoubleAmountResult.isError()) {
                this.showErrorAndWait("The amount must be a number.",
                                      this.textFieldInitialQuantity.getScene().getWindow()); // You can use any other control
                return;
            }
            final double recordDoubleAmount = recordDoubleAmountResult.getValue();
            final Result<Empty> addRecordResult = getController().getWarehouseController()
                                                                 .addRecord(stockId,
                                                                            recordDoubleAmount,
                                                                            Record.Action.ADDING);
            if (addRecordResult.isError()) {
                this.showErrorAndWait("Add Record internal error: " + addRecordResult.getError().getMessage(),
                    this.textFieldInitialQuantity.getScene().getWindow()); // You can use any other control
                return;
            }
        }
        ((Stage) this.datePicker.getScene().getWindow()).close();
    }

    @FXML
    private void specifyInitialQuantityClick(final ActionEvent actionEvent) {
        textFieldInitialQuantity.setDisable(!checkBoxInitialQuantity.isSelected());
    }

    @FXML
    private void setUomInitialQuantity() {
        uomInitialQuantity.setText(comboBoxArticle.getValue().getUnitOfMeasure().getSymbol());
    }
}
