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

/**
 * View controller of stock new batch modal view.
 */
@SuppressWarnings("NullAway")
public final class StockBatchModalController
    extends AbstractViewController
    implements InitializableController<StockBatchViewModel> {

    private static final String NO_ARTICLES_FOR_BATCH_MESSAGE = "No matching beer articles for this batch";
    private static final String ARTICLE_NAME_CANNOT_BE_EMPTY_MESSAGE = "New beer article name cannot be empty!";
    private static final String MUST_SELECT_ARTICLE_MESSAGE = "Must select a beer article!";
    private static final String ERROR_STOCKING_MESSAGE = "An error occurred while stocking the batch.";

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
    private ComboBox<BeerArticleViewModel> possibileBeerArticlesComboBox;

    private StockBatchViewModel data;

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
            this.useExistentArticleVBox.getChildren().add(new Label(NO_ARTICLES_FOR_BATCH_MESSAGE));
        } else {
            this.possibileBeerArticlesComboBox.setItems(FXCollections.observableList(articles));
        }

        this.newArticleTitledPane.visibleProperty().bind(
            this.createNewBeerArticleCheckBox.selectedProperty()
        );

        this.possibileBeerArticlesComboBox.disableProperty().bind(
            this.createNewBeerArticleCheckBox.selectedProperty()
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
                this.showAlertAndWait(ARTICLE_NAME_CANNOT_BE_EMPTY_MESSAGE);
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
                this.showAlertAndWait(MUST_SELECT_ARTICLE_MESSAGE);
                return;
            }

            articleId = this.possibileBeerArticlesComboBox.getSelectionModel().getSelectedItem().getId();
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
        this.showErrorAndWait(ERROR_STOCKING_MESSAGE + "\n" + message,
            this.beerArticlesVBox.getScene().getWindow()); // You can use any other control
    }
}
