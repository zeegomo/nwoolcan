package nwoolcan.view.production;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.batch.BatchMethod;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.filters.SelectFilter;
import nwoolcan.view.filters.TextFilter;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.mastertable.MasterTableViewModel;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.view.utils.ViewModelCallback;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for production view.
 */
@SuppressWarnings("NullAway")
public final class ProductionController
    extends SubViewController
    implements InitializableController<ProductionViewModel> {

    @FXML
    private SelectFilter<BatchMethod> batchMethodFilter;
    @FXML
    private TextFilter batchIdFilter;
    @FXML
    private Label lblNumberStockedBatches;
    @FXML
    private Label lblTotalNumberBatches;
    @FXML
    private Label lblNumberEndedBatches;
    @FXML
    private Label lblNumberProductionBatches;

    @FXML
    private PieChart pieChartBatchesStatus;
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
        lblNumberStockedBatches.setText(Long.toString(data.getNStockedBatches()));

        pieChartBatchesStatus.getData().clear();
        pieChartBatchesMethods.getData().clear();

        if (data.getNBatches() > 0) {
            if (data.getNInProgressBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("In progress", data.getNInProgressBatches()));
            }
            if (data.getNEndedNotStockedBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("Ended not stocked", data.getNEndedNotStockedBatches()));
            }
            if (data.getNStockedBatches() > 0) {
                pieChartBatchesStatus.getData().add(new PieChart.Data("Stocked", data.getNStockedBatches()));
            }
        }

        pieChartBatchesMethods.setData(
            FXCollections.observableList(
                data.getMethodsFrequency()
                    .entrySet()
                    .stream()
                    .map(es -> new PieChart.Data(es.getKey(), es.getValue()))
                    .collect(Collectors.toList())
            )
        );

        this.buildMasterTable(data.getBatches());
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
            this.initData(this.getController().getProductionViewModel());
        }
    }

    /**
     * Click event for the application of filters in the master table.
     * @param event the occurred event.
     */
    public void applyBatchesFilters(final ActionEvent event) {
        final QueryBatchBuilder builder = new QueryBatchBuilder();
        if (this.batchIdFilter.getValue().isPresent()) {
            int batchId;
            try {
                batchId = Integer.parseInt(this.batchIdFilter.getValue().get());
            } catch (NumberFormatException ex) {
                this.showErrorAndWait("Batch Id filter must be a number!", this.lblNumberProductionBatches.getScene().getWindow());
                return;
            }
            builder.setBatchId(batchId);
        }

        if (this.batchMethodFilter.getValue().isPresent()) {
            builder.setBatchMethod(this.batchMethodFilter.getValue().get());
        }

        builder.build()
               .peek(qb -> this.buildMasterTable(this.getController().getBatches(qb)))
               .peekError(e -> this.showErrorAndWait("An error occurred while filtering batches:\n" + e.getMessage(),
                   this.lblNumberProductionBatches.getScene().getWindow()));
    }

    private void buildMasterTable(final List<MasterBatchViewModel> batchesToShow) {
        final MasterTableViewModel<MasterBatchViewModel, ViewModelCallback<DetailBatchViewModel>> masterViewModel = new MasterTableViewModel<>(
            Arrays.asList(
                new ColumnDescriptor("ID", "id"),
                new ColumnDescriptor("Beer name", "beerDescriptionName"),
                new ColumnDescriptor("Style", "beerStyleName"),
                new ColumnDescriptor("Batch method", "batchMethodName"),
                new ColumnDescriptor("Current step", "currentStepName"),
                new ColumnDescriptor("Start date", "startDate"),
                new ColumnDescriptor("Initial size", "initialBatchSize"),
                new ColumnDescriptor("Current size", "currentBatchSize"),
                new ColumnDescriptor("Ended", "ended"),
                new ColumnDescriptor("Stocked", "stocked")
            ),
            batchesToShow,
            ViewType.BATCH_DETAIL,
            mbvm -> {
                Result<DetailBatchViewModel> res = this.getController().getBatchController().getDetailBatchViewModelById(mbvm.getId());
                if (res.isPresent()) {
                    return new ViewModelCallback<>(res.getValue(), () -> this.initData(this.getController().getProductionViewModel()));
                }

                this.showErrorAndWait("Batch id not found!", this.lblNumberProductionBatches.getScene().getWindow());
                return null;
            }
        );

        this.getViewManager().getView(ViewType.MASTER_TABLE, masterViewModel).peek(p -> masterTableContainer.substitute(p));
    }

    @FXML
    private void gotoDashboardClick(final ActionEvent event) {
        this.substituteView(ViewType.DASHBOARD);
    }
}
