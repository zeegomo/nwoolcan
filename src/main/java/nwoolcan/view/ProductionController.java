package nwoolcan.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;

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
    private SubView productionSubView;

    @Override
    public void initData(final ProductionViewModel data) {
        //TODO complete initialization.

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

//        //Dummy test
//        lblTotalNumberBatches.setText(Long.toString(25));
//        lblNumberProductionBatches.setText(Long.toString(10));
//        lblNumberEndedBatches.setText(Long.toString(15));
//
//        pieChartBatchesStatus.setData(
//            FXCollections.observableArrayList(
//                new PieChart.Data("Production", 10),
//                new PieChart.Data("Ended", 15)
//            )
//        );
//
//        pieChartBatchesStyleTypes.setData(
//            FXCollections.observableArrayList(
//                new PieChart.Data("Style1", 4),
//                new PieChart.Data("Style2", 8),
//                new PieChart.Data("Style3", 2),
//                new PieChart.Data("Style4", 1)
//            )
//        );
    }

    @Override
    protected SubView getSubView() {
        return this.productionSubView;
    }
}
