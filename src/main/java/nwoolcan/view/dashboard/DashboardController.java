package nwoolcan.view.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
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
    private Label lblNumberExpiring;

    @FXML
    private PieChart pieChartStockTypes;

    @FXML
    private Label lblAvailableMisc;

    @FXML
    private Label lblAvailableIngredient;

    @FXML
    private Label lblAvailableBeer;

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

        // Production
        this.lblTotalNumberBatches.setText(Long.toString(data.getNBatches()));
        this.lblNumberProductionBatches.setText(Long.toString(data.getInProgressBatches()));
        this.lblNumberEndedBatches.setText(Long.toString(data.getEndedBatches()));
        this.lblNumberStockedBatches.setText(Long.toString(data.getStockedBatches()));

        if (data.getNBatches() > 0) {
            this.pieChartBatchesStatus.setVisible(true);
            pieChartBatchesStatus.getData().clear();
            if (data.getInProgressBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("In progress", data.getInProgressBatches()));
            }
            if (data.getEndedNotStockedBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("Ended not stocked", data.getEndedNotStockedBatches()));
            }
            if (data.getStockedBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("Stocked", data.getStockedBatches()));
            }
        } else {
            this.pieChartBatchesStatus.setVisible(false);
        }

        // Warehouse
        this.lblAvailableBeer.setText(Integer.toString(data.getBeerAvailable()));
        this.lblAvailableIngredient.setText(Integer.toString(data.getIngredientAvailable()));
        this.lblAvailableMisc.setText(Integer.toString(data.getMiscAvailable()));
        this.lblNumberExpiring.setText(Integer.toString(data.getExpiringSoonStocks()));
        this.lblNumberExpiring.setTextFill(Paint.valueOf(data.getExpiringSoonStocks() > 0 ? "darkred" : "darkgreen"));

        if (data.getTotalAvailable() > 0) {
            this.pieChartStockTypes.setVisible(true);
            pieChartStockTypes.getData().clear();
            if (data.getBeerAvailable() > 0) {
                pieChartStockTypes.getData().add(new PieChart.Data("Beer", data.getBeerAvailable()));
            }
            if (data.getIngredientAvailable() > 0) {
                pieChartStockTypes.getData().add(new PieChart.Data("Ingredients", data.getIngredientAvailable()));
            }
            if (data.getMiscAvailable() > 0) {
                pieChartStockTypes.getData().add(new PieChart.Data("Misc", data.getMiscAvailable()));
            }
        } else {
            this.pieChartStockTypes.setVisible(false);
        }
    }

    @FXML
    private void toWarehouseClicked(final ActionEvent event) {
        this.substituteView(ViewType.WAREHOUSE);
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
