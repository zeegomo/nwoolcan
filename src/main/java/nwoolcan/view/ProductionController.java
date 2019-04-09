package nwoolcan.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
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

    @Override
    public void initData(final ProductionViewModel data) {
        lblTotalNumberBatches.setText(Long.toString(data.getNBatches()));
        lblNumberProductionBatches.setText(Long.toString(data.getNInProgressBatches()));
        lblNumberEndedBatches.setText(Long.toString(data.getNEndedBatches()));

        pieChartBatchesStatus.setData(
            FXCollections.observableArrayList(
                new PieChart.Data("Progress", data.getNInProgressBatches()),
                new PieChart.Data("Ended", data.getNEndedBatches())
            )
        );

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

        final MasterTableViewModel<MasterBatchViewModel> masterViewModel = new MasterTableViewModel<>(
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
            ViewType.BATCH_DETAIL
        );

        ViewManager.getView(ViewType.MASTER_TABLE, masterViewModel).peek(p -> masterTableContainer.substitute(p));

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
}
