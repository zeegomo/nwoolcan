package nwoolcan.view.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import nwoolcan.controller.Controller;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.viewmodel.brewery.DashboardViewModel;

/**
 * Handles the Dashboard view.
 */
@SuppressWarnings("NullAway")
public final class DashboardController extends SubViewController {

    @FXML
    private Label lblTotalNumberBatches;

    @FXML
    private Label lblNumberProductionBatches;

    @FXML
    private Label lblNumberEndedBatches;

    @FXML
    private Label lblNumberStockedBatches;

    @FXML
    private PieChart pieChartBatchesStatus;

    @FXML
    private Label lblTitle;

    @FXML
    private SubView content;

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public DashboardController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @FXML
    private void initialize() {
        final DashboardViewModel data = this.getController().getDashboardViewModel();
        this.lblTitle.setText(data.getBreweryName());

        this.lblTotalNumberBatches.setText(Long.toString(data.getProduction().getNBatches()));
        this.lblNumberProductionBatches.setText(Long.toString(data.getProduction().getNInProgressBatches()));
        this.lblNumberEndedBatches.setText(Long.toString(data.getProduction().getNEndedBatches()));
        this.lblNumberStockedBatches.setText(Long.toString(data.getProduction().getNStockedBatches()));

        if (data.getProduction().getNBatches() > 0) {
            this.pieChartBatchesStatus.setVisible(true);
            pieChartBatchesStatus.getData().clear();
            if (data.getProduction().getNInProgressBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("In progress", data.getProduction().getNInProgressBatches()));
            }
            if (data.getProduction().getNEndedNotStockedBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("Ended not stocked", data.getProduction().getNEndedNotStockedBatches()));
            }
            if (data.getProduction().getNStockedBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("Stocked", data.getProduction().getNStockedBatches()));
            }
        } else {
            this.pieChartBatchesStatus.setVisible(false);
        }
    }

    @FXML
    private void toProductionClicked(final ActionEvent event) {
        this.substituteView(ViewType.PRODUCTION, this.getController().getProductionViewModel());
    }

    @Override
    protected SubView getSubView() {
        return this.content;
    }
}
