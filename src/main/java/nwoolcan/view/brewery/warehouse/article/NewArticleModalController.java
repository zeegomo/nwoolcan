package nwoolcan.view.brewery.warehouse.article;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.controller.warehouse.WarehouseController;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.ViewManager;

/**
 * View controller for new article modal.
 */
@SuppressWarnings("NullAway")
public final class NewArticleModalController extends AbstractViewController {

    @FXML
    private Button createArticleButton;
    @FXML
    private FlowPane ingredientTypeFlowPane;
    @FXML
    private TextField newArticleName;
    @FXML
    private ComboBox<UnitOfMeasure> newArticleUnitOfMeasure;
    @FXML
    private ComboBox<ArticleType> newArticleType;
    @FXML
    private ComboBox<IngredientType> newArticleIngredientType;

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewArticleModalController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    /**
     * Initialize the view create article.
     */
    @FXML
    public void initialize() {
        newArticleUnitOfMeasure.getItems().setAll(UnitOfMeasure.values());
        newArticleUnitOfMeasure.getSelectionModel().selectFirst();
        newArticleType.getItems().setAll(ArticleType.values());
        newArticleType.getSelectionModel().selectFirst();
        newArticleIngredientType.getItems().setAll(IngredientType.values());
        newArticleIngredientType.getSelectionModel().selectFirst();
        updateClicks();
    }

    /**
     * Creates or deletes combo boxes in the view accordingly with the currently selected {@link ArticleType}.
     * @param event the occurred event.
     */
    public void selectArticleTypeClick(final ActionEvent event) {
        updateClicks();
    }

    private void updateClicks() {
        ingredientTypeFlowPane.setDisable(newArticleType.getValue() != ArticleType.INGREDIENT);
        createArticleButton.setDisable(newArticleName.getText().isEmpty());
    }

    /**
     * Creates the actual article.
     * @param event the occurred event.
     */
    public void createArticleClick(final ActionEvent event) {
        updateClicks();
        if (newArticleName.getText().isEmpty()) {
            return;
        }
        final WarehouseController warehouseController = getController().getWarehouseController();

        switch (newArticleType.getValue()) {
            case FINISHED_BEER:
                warehouseController.createBeerArticle(newArticleName.getText(), newArticleUnitOfMeasure.getValue());
                break;
            case MISC:
                warehouseController.createMiscArticle(newArticleName.getText(), newArticleUnitOfMeasure.getValue());
                break;
            case INGREDIENT:
                warehouseController.createIngredientArticle(newArticleName.getText(),
                                                            newArticleUnitOfMeasure.getValue(),
                                                            newArticleIngredientType.getValue());
                break;
            default:
                break;
        }

        ((Stage) this.ingredientTypeFlowPane.getScene().getWindow()).close();
    }

    /**
     * When name field is empty, disable the create button.
     * @param keyEvent the event occurred.
     */
    public void newNameFieldChanged(final KeyEvent keyEvent) {
        updateClicks();
    }
}
