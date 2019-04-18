package nwoolcan.view.warehouse.stock;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.view.utils.ViewModelCallback;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.BeerStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.DetailStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.RecordViewModel;

import java.util.List;

/**
 * Controller for the Stock detail view.
 */
@SuppressWarnings("NullAway")
public final class StockDetailController extends SubViewController implements InitializableController<ViewModelCallback<Integer>> {

    @FXML
    private Button buttonGoToBatch;
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
    private int stockId;
    private int batchId;
    private Runnable updateFather;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public StockDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final ViewModelCallback<Integer> dataAndRunner) {
        stockId = dataAndRunner.getViewModel();
        loadData();
        updateFather = dataAndRunner.getCallback();
    }

    private void loadData() {
        final DetailStockViewModel data = getController().getWarehouseController().getViewStockById(stockId).getValue();
        stockId = data.getId();
        articleId = data.getArticle().getId();
        lblArticle.setText(data.getArticle().toString());
        lblAvailableQt.setText(data.getRemainingQuantity().toString());
        lblCreationDate.setText(data.getCreationDate());
        lblLastModified.setText(data.getLastModified());
        lblUsedQt.setText(data.getUsedQuantity().toString());
        lblId.setText(Integer.toString(data.getId()));
        if (data.getArticle().getArticleType() == ArticleType.FINISHED_BEER) {
            this.batchId = ((BeerStockViewModel) data).getBatchId();
            this.buttonGoToBatch.setVisible(true);
            this.buttonGoToBatch.setManaged(true);
        }

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
            overlayView(ViewType.ARTICLE_DETAIL, new ViewModelCallback<>(articleResult.getValue(), this::loadData));
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
        updateFather.run();
        this.previousView();
    }

    @FXML
    private void addRecordButtonClick(final ActionEvent actionEvent) {
        //overlayView(ViewType.NEW_RECORD_MODAL, stockId);
        final Stage modal =  new Stage();
        final Window window = this.getSubView().getScene().getWindow();

        modal.initOwner(window);
        modal.initModality(Modality.WINDOW_MODAL);

        final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_RECORD_MODAL, stockId).orElse(new AnchorPane()),
            600, 400);

        modal.setResizable(false);
        modal.setScene(scene);
        modal.setY(window.getY() + window.getHeight() / 2 - scene.getHeight() / 2);
        modal.setX(window.getX() + window.getWidth() / 2 - scene.getWidth() / 2);
        modal.showAndWait();

        this.loadData();

    }
    @FXML
    private void goToBatchButtonClick(final ActionEvent actionEvent) {
        final Result<DetailBatchViewModel> batchResult = getController().getBatchController().getDetailBatchViewModelById(batchId);
        if (batchResult.isPresent()) {
            overlayView(ViewType.BATCH_DETAIL, batchResult.getValue());
        } else {
            new Alert(
                Alert.AlertType.ERROR,
                "Internal Error: " + batchResult.getError().getMessage(),
                ButtonType.CLOSE
            ).showAndWait();
        }
    }
}
