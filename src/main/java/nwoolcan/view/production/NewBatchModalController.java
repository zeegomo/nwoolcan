package nwoolcan.view.production;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.production.batch.BatchMethod;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.CreateBatchDTO;
import nwoolcan.viewmodel.brewery.production.batch.NewBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * View controller for new batch modal.
 */
@SuppressWarnings("NullAway")
public final class NewBatchModalController
    extends AbstractViewController implements InitializableController<NewBatchViewModel> {

    @FXML
    private TextField beerNameTextField;
    @FXML
    private TextField beerStyleTextField;
    @FXML
    private TextField beerCategoryTextField;
    @FXML
    private Label initialSizeUnitOfMeasureLabel;
    @FXML
    private ComboBox<BatchMethod> batchMethodsComboBox;
    @FXML
    private TextField initialSizeTextField;
    @FXML
    private ComboBox<UnitOfMeasure> possibleUnisOfMeasureComboBox;

    private static final class IngredientArticleProperty {
        private IngredientArticleViewModel article;

        private IngredientArticleProperty(final IngredientArticleViewModel article) {
            this.article = article;
        }

        private IngredientArticleViewModel getArticle() {
            return this.article;
        }

        @Override
        public String toString() {
            return article.getName();
        }
    }

    @FXML
    private TextField registrationValueTextField;
    @FXML
    private TableView<Pair<Number, WaterMeasurement.Element>> elementsTableView;
    @FXML
    private ComboBox<WaterMeasurement.Element> elementsComboBox;

    @FXML
    private Label ingredientUnitOfMeasureLabel;
    @FXML
    private TextField quantityIngredientTextField;
    @FXML
    private TableView<Pair<Number, IngredientArticleProperty>> ingredientsTableView;
    @FXML
    private ComboBox<IngredientArticleProperty> ingredientsComboBox;

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewBatchModalController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final NewBatchViewModel data) {
        this.batchMethodsComboBox.setItems(FXCollections.observableList(data.getBatchMethods()));

        this.possibleUnisOfMeasureComboBox.setItems(FXCollections.observableList(data.getUnitsOfMeasure()));
        this.possibleUnisOfMeasureComboBox.getSelectionModel().selectedItemProperty().addListener((opt, oldV, newV) ->
            this.initialSizeUnitOfMeasureLabel.setText(newV.getSymbol())
        );

        final TableColumn<Pair<Number, WaterMeasurement.Element>, Button> removeElementColumn = new TableColumn<>();
        removeElementColumn.setCellValueFactory(obj -> {
            final Button btn = new Button("Remove");
            btn.setOnAction(event -> this.elementsTableView.getItems().removeIf(p -> p.getRight().equals(obj.getValue().getRight())));
            return new SimpleObjectProperty<>(btn);
        });
        this.elementsTableView.getColumns().add(removeElementColumn);

        final TableColumn<Pair<Number, IngredientArticleProperty>, Button> removeIngredientColumn = new TableColumn<>();
        removeIngredientColumn.setCellValueFactory(obj -> {
            final Button btn = new Button("Remove");
            btn.setOnAction(event -> this.ingredientsTableView.getItems().removeIf(p ->
                p.getRight().getArticle().getId() == obj.getValue().getRight().getArticle().getId()));
            return new SimpleObjectProperty<>(btn);
        });
        this.ingredientsTableView.getColumns().add(removeIngredientColumn);

        this.elementsComboBox.setItems(FXCollections.observableList(
            new ArrayList<>(data.getWaterMeasurementElements())
        ));

        this.ingredientsComboBox.getSelectionModel().selectedItemProperty().addListener((opt, oldV, newV) ->
            this.ingredientUnitOfMeasureLabel.setText(newV.getArticle().getUnitOfMeasure().getSymbol())
        );

        this.ingredientsComboBox.setItems(FXCollections.observableList(
            data.getIngredients()
                .stream()
                .map(IngredientArticleProperty::new)
                .collect(Collectors.toList()))
        );
    }

    /**
     * Adds a new water measurement registration element to the water measurement table view.
     * @param event the occurred event.
     */
    public void addElementRegistrationClick(final ActionEvent event) {
        final WaterMeasurement.Element selectedElement = this.elementsComboBox.getSelectionModel().getSelectedItem();
        if (selectedElement == null) {
            return;
        }

        Number registrationValue;
        try {
            registrationValue = Double.parseDouble(this.registrationValueTextField.getText());
        } catch (NumberFormatException ex) {
            return;
        }

        this.elementsTableView.getItems().removeIf(p -> p.getRight().equals(selectedElement));
        this.elementsTableView.getItems().add(Pair.of(registrationValue, selectedElement));
    }

    /**
     * Adds an ingredient to the ingredient table view.
     * @param event the occurred event.
     */
    public void addIngredientClick(final ActionEvent event) {
        final IngredientArticleProperty selectedElement = this.ingredientsComboBox.getSelectionModel().getSelectedItem();
        if (selectedElement == null) {
            return;
        }

        Number quantity;
        try {
            quantity = Double.parseDouble(this.quantityIngredientTextField.getText());
        } catch (NumberFormatException ex) {
            return;
        }

        this.ingredientsTableView.getItems().removeIf(p -> p.getRight().getArticle().getId() == selectedElement.getArticle().getId());
        this.ingredientsTableView.getItems().add(Pair.of(quantity, selectedElement));
    }

    /**
     * Creates the actual batch.
     * @param event the occurred event.
     */
    public void createBatchClick(final ActionEvent event) {
        //all possible checks before calling controller
        if (this.beerNameTextField.getText().isEmpty()) {
            this.showAlertAndWait("There must be a beer name!");
            return;
        }

        if (this.beerStyleTextField.getText().isEmpty()) {
            this.showAlertAndWait("There must be a beer style!");
            return;
        }

        if (this.batchMethodsComboBox.getSelectionModel().getSelectedItem() == null) {
            this.showAlertAndWait("Must select a batch method!");
            return;
        }

        if (this.initialSizeTextField.getText().isEmpty()) {
            this.showAlertAndWait("There must be an initial size!");
            return;
        }

        final double size;
        try {
            size = Double.parseDouble(this.initialSizeTextField.getText());
        } catch (NumberFormatException ex) {
            this.showAlertAndWait("Initial size must be a number!");
            return;
        }

        if (this.possibleUnisOfMeasureComboBox.getSelectionModel().getSelectedItem() == null) {
            this.showAlertAndWait("Must select a quantity unit of measure!");
            return;
        }

        //refactor maybe with a DTO
        final Result<Quantity> res = Quantity.of(size, this.possibleUnisOfMeasureComboBox.getSelectionModel().getSelectedItem())
                                                     .peekError(e -> this.showAlertAndWait(e.getMessage()));

        final Quantity initialSize;
        if (res.isPresent()) {
            initialSize = res.getValue();
        } else {
            return;
        }

        this.getController().createNewBatch(new CreateBatchDTO(
            this.beerNameTextField.getText(),
            this.beerStyleTextField.getText(),
            this.beerCategoryTextField.getText().isEmpty() ? null : this.beerCategoryTextField.getText(),
            this.batchMethodsComboBox.getSelectionModel().getSelectedItem(),
            initialSize,
            StepTypeEnum.MASHING,
            this.ingredientsTableView.getItems()
                                     .stream()
                                     .map(p -> Pair.of(p.getRight().getArticle().getId(), p.getLeft().doubleValue()))
                                     .collect(Collectors.toList()),
            this.elementsTableView.getItems()
                                  .stream()
                                  .map(p -> Triple.of(p.getRight(), p.getLeft().doubleValue(), new Date()))
                                  .collect(Collectors.toList())))
            .peekError(e -> this.showAlertAndWait(e.getMessage()))
            .peek(e -> {
                final Stage stage = ((Stage) this.elementsTableView.getScene().getWindow());
                //just for saying that i added batch to the caller
                stage.setUserData(new Object());
                stage.close();
            });
    }

    private void showAlertAndWait(final String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred while creating the batch.\n" + message, ButtonType.CLOSE);
        a.showAndWait();
    }
}
