package nwoolcan.view.production;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.mastertable.MasterTableViewModel;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Controller class for production view.
 */
@SuppressWarnings("NullAway")
public final class ProductionController
    extends SubViewController
    implements InitializableController<ProductionViewModel> {

    @FXML
    private Label lblTotalNumberBatches;
    @FXML
    private Label lblNumberEndedBatches;
    @FXML
    private Label lblNumberProductionBatches;

    @FXML
    private PieChart pieChartBatchesStatus;
    @FXML
    private PieChart pieChartBatchesStyleTypes;
    @FXML
    private PieChart pieChartBatchesMethods;

    @FXML
    private SubView productionSubView;
    @FXML
    private SubViewContainer masterTableContainer;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public ProductionController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final ProductionViewModel data) {
        lblTotalNumberBatches.setText(Long.toString(data.getNBatches()));
        lblNumberProductionBatches.setText(Long.toString(data.getNInProgressBatches()));
        lblNumberEndedBatches.setText(Long.toString(data.getNEndedBatches()));

        if (data.getNBatches() > 0) {
            pieChartBatchesStatus.setData(
                FXCollections.observableArrayList(
                    new PieChart.Data("Progress", data.getNInProgressBatches()),
                    new PieChart.Data("Ended", data.getNEndedBatches())
                )
            );
        }

        pieChartBatchesStyleTypes.setData(
            FXCollections.observableList(
                data.getStylesFrequency()
                    .entrySet()
                    .stream()
                    .map(es -> new PieChart.Data(es.getKey(), es.getValue()))
                    .collect(Collectors.toList())
            )
        );

        pieChartBatchesMethods.setData(
            FXCollections.observableList(
                data.getMethodsFrequency()
                    .entrySet()
                    .stream()
                    .map(es -> new PieChart.Data(es.getKey(), es.getValue()))
                    .collect(Collectors.toList())
            )
        );

        final MasterTableViewModel<MasterBatchViewModel, DetailBatchViewModel> masterViewModel = new MasterTableViewModel<>(
            Arrays.asList(
                new ColumnDescriptor("ID", "id"),
                new ColumnDescriptor("Beer name", "beerDescriptionName"),
                new ColumnDescriptor("Style", "beerStyleName"),
                new ColumnDescriptor("Batch method", "batchMethodName"),
                new ColumnDescriptor("Current step", "currentStepName"),
                new ColumnDescriptor("Start date", "startDate"),
                new ColumnDescriptor("Initial size", "initialBatchSize"),
                new ColumnDescriptor("Current size", "currentBatchSize"),
                new ColumnDescriptor("Ended", "isEnded")
            ),
            data.getBatches(),
            ViewType.BATCH_DETAIL,
            mbvm -> {
                Result<DetailBatchViewModel> res = this.getController().getBatchController().getDetailBatchViewModelById(mbvm.getId());
                if (res.isPresent()) {
                    return res.getValue();
                }

                this.showAlertAndWait("Batch id not found!");
                return null; //TODO fix with unified error management in master table
            }
        );

        this.getViewManager().getView(ViewType.MASTER_TABLE, masterViewModel).peek(p -> masterTableContainer.substitute(p));

        //Experiment with events in pie chart.
//        final Label tooltipPieChart = new Label("");
//        tooltipPieChart.setTextFill(Color.WHITE);
//        tooltipPieChart.setStyle("-fx-font: 24 arial;");
//
//        pieChartBatchesStyleTypes.getData().forEach(d -> {
//            d.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(final MouseEvent event) {
//                    tooltipPieChart.setTranslateX(event.getSceneX());
//                    tooltipPieChart.setTranslateY(event.getSceneY());
//                    tooltipPieChart.setText(String.valueOf(d.getPieValue()));
//                }
//            });
//        });

    }

    @Override
    protected SubView getSubView() {
        return this.productionSubView;
    }

    /**
     * Triggered when the button "create new batch" is clicked.
     * Opens a modal to retrieve data about the new batch to create.
     * @param event the occurred event.
     */
    public void createNewBatchClick(final ActionEvent event) {
        final Stage modal =  new Stage();
        final Window window = this.getSubView().getScene().getWindow();

        modal.initOwner(window);
        modal.initModality(Modality.WINDOW_MODAL);

        final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_BATCH_MODAL,
            this.getController().getNewBatchViewModel()).orElse(new AnchorPane()));

        modal.setScene(scene);
        modal.centerOnScreen();
        modal.showAndWait();

        if (modal.getUserData() != null) {
            this.substituteView(ViewType.PRODUCTION, this.getController().getProductionViewModel());
        }
    }

    private void showAlertAndWait(final String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred while loading the batch detail.\n" + message, ButtonType.CLOSE);
        a.showAndWait();
    }
}
