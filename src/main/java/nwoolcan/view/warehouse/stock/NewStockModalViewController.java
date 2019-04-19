package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.utils.Result;
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
    }

    @FXML
    private void specifyDateClick(final ActionEvent actionEvent) {
        datePicker.setManaged(checkBoxDate.isSelected());
        datePicker.setVisible(checkBoxDate.isSelected());
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
            this.showErrorAndWait("Internal Error: " + stockResult.getError().getMessage(), ((Node) actionEvent.getTarget()).getScene().getWindow());
        } else {
            ((Stage) this.datePicker.getScene().getWindow()).close();
        }
    }
}
