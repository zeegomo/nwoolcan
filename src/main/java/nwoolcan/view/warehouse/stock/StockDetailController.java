package nwoolcan.view.warehouse.stock;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import nwoolcan.controller.Controller;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.DetailStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.RecordViewModel;

import java.util.List;

/**
 * Controller for the Stock detail view.
 */
@SuppressWarnings("NullAway")
public final class StockDetailController extends SubViewController implements InitializableController<DetailStockViewModel> {

    @FXML
    private Label lblArticle;
    @FXML
    private Label lblAvailableQt;
    @FXML
    private Label lblCreationDate;
    @FXML
    private Label lblLastModified;
    @FXML
    private Label lblUsedQt;
    @FXML
    private SubView stockDetailSubView;
    @FXML
    private TableView<RecordViewModel> recordTable;
    @FXML
    private Label lblId;

    private int articleId;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public StockDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final DetailStockViewModel data) {
        articleId = data.getArticle().getId();
        lblArticle.setText(data.getArticle().toString());
        lblAvailableQt.setText(data.getRemainingQuantity().toString());
        lblCreationDate.setText(data.getCreationDate());
        lblLastModified.setText(data.getLastModified());
        lblUsedQt.setText(data.getUsedQuantity().toString());
        lblId.setText(Integer.toString(data.getId()));

        setTable(data.getRecords());
    }

    @Override
    protected SubView getSubView() {
        return this.stockDetailSubView;
    }

    @FXML
    private void goToArticleButtonClick(final ActionEvent actionEvent) {
        final Result<AbstractArticleViewModel> articleResult = getController().getWarehouseController()
                                                                              .getViewArticleById(articleId);
        if (articleResult.isPresent()) {
            overlayView(ViewType.ARTICLE_DETAIL, articleResult.getValue());
        } else {
            new Alert(
                        Alert.AlertType.ERROR,
                        "Internal Error: " + articleResult.getError().getMessage(),
                        ButtonType.CLOSE
                     ).showAndWait();
        }
    }

    private void setTable(final List<RecordViewModel> articles) {
       recordTable.setItems(FXCollections.observableArrayList(articles));
    }

    @FXML
    private void backButtonClick(final ActionEvent actionEvent) {
        this.previousView(); // TODO call reload of the previous view before switching!
    }
}
