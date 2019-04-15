package nwoolcan.view.production;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.StockBatchViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;

import java.util.List;

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
    private ComboBox<BeerArticleViewModel> possibileBeerArticlesComboBox;

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
        final List<BeerArticleViewModel> articles = data.getPossibleArticles();

        if (articles.size() == 0) {
            this.useExistentArticleVBox.getChildren().clear();
            this.useExistentArticleVBox.getChildren().add(new Label("No matching beer articles for this batch"));
        } else {
            this.possibileBeerArticlesComboBox.setItems(FXCollections.observableList(articles));
        }

        this.newArticleTitledPane.disableProperty().bind(
            this.createNewBeerArticleCheckBox.selectedProperty().not()
        );
    }
}
