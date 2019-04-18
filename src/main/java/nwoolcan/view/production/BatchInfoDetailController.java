package nwoolcan.view.production;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.viewmodel.brewery.production.batch.BatchInfoViewModel;
import nwoolcan.viewmodel.brewery.production.step.ParameterViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import org.apache.commons.lang3.tuple.Pair;
import java.util.stream.Collectors;

/**
 * Controller for Batch Info detail view.
 */
@SuppressWarnings("NullAway")
public final class BatchInfoDetailController extends SubViewController implements InitializableController<BatchInfoViewModel> {
    private static final String INGREDIENT_NAME_COLUMN = "Ingredient";
    private static final String INGREDIENT_QUANTITY_COLUMN = "Quantity";
    private static final String PARAMETER_NAME_COLUMN = "Name";
    private static final String PARAMETER_VALUE_COLUMN = "Value";
    private static final String PARAMETER_DATE_COLUMN = "Date";

    @FXML
    private SubView batchInfoDetailSubview;
    @FXML
    private SubViewContainer batchInfoContainer;
    @FXML
    private TableView<Pair<IngredientArticleViewModel, Double>> ingredients;
    @FXML
    private TableView<Pair<ParameterViewModel, String>> waterMeasurements;
    @FXML
    private PieChart ingredientsTypeChart;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public BatchInfoDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final BatchInfoViewModel data) {
        this.getViewManager().getView(ViewType.BATCH_INFO, data).peek(this.batchInfoContainer::substitute);
        this.ingredients.setItems(FXCollections.observableList(data.getIngredients()));

        TableColumn<Pair<IngredientArticleViewModel, Double>, String> ingredientNameCol = new TableColumn<>(INGREDIENT_NAME_COLUMN);
        ingredientNameCol.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getLeft().getName()));
        this.ingredients.getColumns().add(ingredientNameCol);

        TableColumn<Pair<IngredientArticleViewModel, Double>, String> ingredientQuantityCol = new TableColumn<>(INGREDIENT_QUANTITY_COLUMN);
        ingredientQuantityCol.setCellValueFactory(pair ->
            new SimpleStringProperty(
                pair.getValue().getRight()
                + " "
                + pair.getValue().getLeft().getUnitOfMeasure())
        );
        this.ingredients.getColumns().add(ingredientQuantityCol);

        TableColumn<Pair<ParameterViewModel, String>, String> parameterNameCol = new TableColumn<>(PARAMETER_NAME_COLUMN);
        parameterNameCol.setCellValueFactory(parameter -> new SimpleStringProperty(parameter.getValue().getRight()));

        TableColumn<Pair<ParameterViewModel, String>, String> parameterValueCol = new TableColumn<>(PARAMETER_VALUE_COLUMN);
        parameterValueCol.setCellValueFactory(parameter ->
            new SimpleStringProperty(parameter.getValue().getLeft().getValueRepresentation())
        );

        TableColumn<Pair<ParameterViewModel, String>, String> parameterDateCol = new TableColumn<>(PARAMETER_DATE_COLUMN);
        parameterDateCol.setCellValueFactory(parameter ->
            new SimpleStringProperty(parameter.getValue().getLeft().getRegistrationDate().toString())
        );

        this.waterMeasurements.getColumns().add(parameterNameCol);
        this.waterMeasurements.getColumns().add(parameterValueCol);
        this.waterMeasurements.getColumns().add(parameterDateCol);

        data.getWaterMeasurements().ifPresent(waterMeasurements ->
            this.waterMeasurements.setItems(FXCollections.observableList(waterMeasurements))
        );

        this.ingredientsTypeChart.setData(FXCollections.observableList(data.getIngredients()
                                                            .stream()
                                                            .map(p -> new PieChart.Data(p.getLeft().getName(), p.getRight()))
                                                            .collect(Collectors.toList())));
    }
    /**
     * Return to previous view.
     * @param event the recorded event.
     */
    public void goBackButtonClicked(final ActionEvent event) {
        this.previousView();
    }
    @Override
    protected SubView getSubView() {
        return this.batchInfoDetailSubview;
    }
}
