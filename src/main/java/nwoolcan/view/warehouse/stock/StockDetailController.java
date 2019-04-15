package nwoolcan.view.warehouse.stock;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.DetailStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.RecordViewModel;

import java.util.List;
import java.util.stream.Stream;

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

    @FXML
    private void btnBackClick(final ActionEvent event) {
        this.previousView();
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
       Stream.of(
                    new ColumnDescriptor("Date", "date"),
                    new ColumnDescriptor("Action", "action"),
                    new ColumnDescriptor("Quantity", "quantity")
                )
             .map(cd -> {
                            final TableColumn<RecordViewModel, String> col = new TableColumn<>(cd.getColumnName());
                            col.setCellValueFactory(new PropertyValueFactory<>(cd.getFieldName()));
                            return col;
                        })
             .forEach(tc -> recordTable.getColumns().add(tc));
       recordTable.setItems(FXCollections.observableArrayList(articles));
    }

    @FXML
    private void backButtonClick(final ActionEvent actionEvent) {
        this.previousView(); // TODO call reload of the previous view before switching!
    }
}
