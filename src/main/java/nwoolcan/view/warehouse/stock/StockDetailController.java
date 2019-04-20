package nwoolcan.view.warehouse.stock;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.stock.Record;
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
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for the Stock detail view.
 */
@SuppressWarnings("NullAway")
public final class StockDetailController extends SubViewController implements InitializableController<ViewModelCallback<Integer>> {

    private static final class ActualAvailability {

        private double value;
        private Date date;

        private ActualAvailability(final double value, final Date date) {
            this.date = date;
            this.value = value;
        }

        private double getValue() {
            return value;
        }
        private Date getDate() {
            return new Date(date.getTime());
        }
    }

    @FXML
    private Label lblTextExpDate;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private Label lblExpirationDate;
    @FXML
    private Label lblState;
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
    private LineChart<Number, Number> chart;

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
        lblCreationDate.setText(data.getFormattedCreationDate());
        lblLastModified.setText(data.getFormattedLastModified());
        lblUsedQt.setText(data.getUsedQuantity().toString());
        lblId.setText(Integer.toString(data.getId()));
        lblState.setText(data.getStockState().toString());
        lblExpirationDate.setText(data.getExpirationDate());
        if (data.getArticle().getArticleType() == ArticleType.FINISHED_BEER) {
            this.batchId = ((BeerStockViewModel) data).getBatchId();
            this.buttonGoToBatch.setVisible(true);
            this.buttonGoToBatch.setManaged(true);
        }
        if (data.getExpirationDate().isEmpty()) {
            lblExpirationDate.setManaged(false);
            lblExpirationDate.setVisible(false);
            lblTextExpDate.setManaged(false);
            lblTextExpDate.setVisible(false);
        }

        final List<RecordViewModel> records = data.getRecords();
        recordTable.setItems(FXCollections.observableArrayList(records));
        if (records.size() > 0) {
            setChart(records);
        }
    }

    private void setChart(final List<RecordViewModel> records) {
        final List<ActualAvailability> availabilities = prefixSum(records);
        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>("Amount",
            FXCollections.observableList(availabilities.stream()
                                                .map(a -> new XYChart.Data<Number, Number>(a.getDate().getTime(), a.getValue()))
                                                .collect(Collectors.toList())
            ));
        final long firstTime = availabilities.get(0).getDate().getTime();
        final long lastTime = availabilities.get(availabilities.size() - 1).getDate().getTime();
        final NumberAxis dateAxis = new NumberAxis(
            firstTime - (lastTime - firstTime) / 16.0,
            lastTime + (lastTime - firstTime) / 16.0,
            (lastTime - firstTime) / 8.0);
        dateAxis.setTickLabelFont(new Font(10));
        dateAxis.setTickLabelRotation(90);
        dateAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public Number fromString(final String string) {
                return null;
            }

            @Override
            public String toString(final Number object) {
                return DateFormatUtils.format(new Date(object.longValue()), "dd-MM-yyyy HH:mm");
            }
        });

        mainGridPane.getChildren().removeAll(chart);
        chart = new LineChart<>(dateAxis, new NumberAxis(),
            FXCollections.observableList(Collections.singletonList(series))
        );
        mainGridPane.add(chart, 2, 0, 4, 8);
    }

    private List<ActualAvailability> prefixSum(final List<RecordViewModel> records) {
        final List<ActualAvailability> prefSum = new ArrayList<>();
        double currValue = 0;
        for (final RecordViewModel r : records) {
            double nextValue = r.getQuantity().getValue();
            if (r.getAction() == Record.Action.REMOVING) {
                nextValue *= -1;
            }
            currValue += nextValue;
            prefSum.add(new ActualAvailability(currValue, r.getDate()));
        }
        return prefSum;
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
            this.showErrorAndWait("Internal Error: " + articleResult.getError().getMessage());
        }
    }

    @FXML
    private void backButtonClick(final ActionEvent actionEvent) {
        updateFather.run();
        this.previousView();
    }

    @FXML
    private void addRecordButtonClick(final ActionEvent actionEvent) {
        final Stage modal =  new Stage();
        final Window window = this.getSubView().getScene().getWindow();

        modal.initOwner(window);
        modal.initModality(Modality.WINDOW_MODAL);

        final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_RECORD_MODAL, stockId).orElse(new AnchorPane()));

        modal.setScene(scene);
        modal.showAndWait();
        this.loadData();

    }
    @FXML
    private void goToBatchButtonClick(final ActionEvent actionEvent) {
        final Result<DetailBatchViewModel> batchResult = getController().getBatchController().getDetailBatchViewModelById(batchId);
        if (batchResult.isPresent()) {
            overlayView(ViewType.BATCH_DETAIL, new ViewModelCallback<DetailBatchViewModel>(batchResult.getValue(), this::loadData));
        } else {
            this.showErrorAndWait("Internal Error: " + batchResult.getError().getMessage());
        }
    }
}
