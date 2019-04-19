package nwoolcan.view.production;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.StockBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View controller of stock new batch modal view.
 */
@SuppressWarnings("NullAway")
public final class StockBatchModalController
    extends AbstractViewController
    implements InitializableController<StockBatchViewModel> {

    @FXML
    private TitledPane newArticleTitledPane;
    @FXML
    private VBox useExistentArticleVBox;
    @FXML
    private VBox beerArticlesVBox;
    @FXML
    private Button stockBatchButton;
    @FXML
    private DatePicker expirationDatePicker;
    @FXML
    private TextField newBeerArticleNameTextField;
    @FXML
    private CheckBox createNewBeerArticleCheckBox;
    @FXML
    private ComboBox<BeerArticleProprety> possibileBeerArticlesComboBox;

    private StockBatchViewModel data;

    private static class BeerArticleProprety {
        private final int id;
        private final String name;

        BeerArticleProprety(final int id, final String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public StockBatchModalController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final StockBatchViewModel data) {
        this.data = data;
        final List<BeerArticleViewModel> articles = data.getPossibleArticles();

        if (articles.size() == 0) {
            this.useExistentArticleVBox.getChildren().clear();
            this.useExistentArticleVBox.getChildren().add(new Label("No matching beer articles for this batch"));
        } else {
            this.possibileBeerArticlesComboBox.setItems(FXCollections.observableList(articles.stream()
                                                                                             .map(a -> new BeerArticleProprety(a.getId(), a.getName()))
                                                                                             .collect(Collectors.toList())));
        }

        this.newArticleTitledPane.disableProperty().bind(
            this.createNewBeerArticleCheckBox.selectedProperty().not()
        );
    }

    /**
     * Stock the batch with beer article and possible expiration date.
     * @param event the occurred event.
     */
    public void stockBatchButtonClicked(final ActionEvent event) {
        int articleId;

        if (this.createNewBeerArticleCheckBox.isSelected()) {
            if (this.newBeerArticleNameTextField.getText().trim().isEmpty()) {
                this.showAlertAndWait("New beer article name cannot be empty!");
                return;
            }

            Result<DetailBatchViewModel> res = this.getController().getBatchController().getDetailBatchViewModelById(data.getBatchId());

            if (res.isError()) {
                this.showAlertAndWait(res.getError().getMessage());
                return;
            }

            articleId = this.getController().getWarehouseController().createBeerArticle(this.newBeerArticleNameTextField.getText().trim(),
                data.getUnitOfMeasure()).getId();
        } else {
            if (this.possibileBeerArticlesComboBox.getSelectionModel().getSelectedItem() == null) {
                this.showAlertAndWait("Must select a beer article!");
                return;
            }

            articleId = this.possibileBeerArticlesComboBox.getSelectionModel().getSelectedItem().id;
        }

        Result<Empty> res;

        if (this.expirationDatePicker.getValue() == null) {
            res = this.getController().stockBatch(data.getBatchId(), articleId);
        } else {
            //convert from LocalDate to Date
            final Date expDate = Date.from(this.expirationDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            res = this.getController().stockBatch(data.getBatchId(), articleId, expDate);
        }

        res.peekError(e -> this.showAlertAndWait(e.getMessage()))
           .peek(e -> {
               final Stage stage = ((Stage) this.newArticleTitledPane.getScene().getWindow());
               //just for saying that i stocked the batch
               stage.setUserData(new Object());
               stage.close();
           });
    }

    private void showAlertAndWait(final String message) {
        this.showErrorAndWait("An error occurred while stocking the batch.\n" + message,
            this.beerArticlesVBox.getScene().getWindow()); // You can use any other control
    }
}
