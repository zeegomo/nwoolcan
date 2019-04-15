package nwoolcan.view.warehouse.article;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;

/**
 * Controller class for article detail view.
 */
@SuppressWarnings("NullAway")
public final class ArticleDetailViewController extends SubViewController implements InitializableController<AbstractArticleViewModel> {

    private static final String NEW_NAME_EMPTY = "The name can not be empty.";
    @FXML
    private TextField newNameTextField;
    @FXML
    private Label articleId;
    @FXML
    private Label articleName;
    @FXML
    private Label articleUnitOfMeasure;
    @FXML
    private Label articleType;
    @FXML
    private Label articleIngredientType;
    @FXML
    private FlowPane ingredientTypeFlowPane;
    @FXML
    private SubView articleDetailSubView;

    private int articleIdInt;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public ArticleDetailViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final AbstractArticleViewModel data) {
        articleIdInt = data.getId();
        articleId.setText(Integer.toString(data.getId()));
        articleName.setText(data.getName());
        articleUnitOfMeasure.setText(data.getUnitOfMeasure().toString());
        articleType.setText(data.getArticleType().toString());
        if (data.getArticleType() == ArticleType.INGREDIENT) {
            final IngredientArticleViewModel ingredientArticleViewModel = (IngredientArticleViewModel) data;
            articleIngredientType.setText(ingredientArticleViewModel.getIngredientType().toString());
        } else {
            ingredientTypeFlowPane.setVisible(false);
            ingredientTypeFlowPane.setManaged(false);
        }
    }

    @Override
    protected SubView getSubView() {
        return articleDetailSubView;
    }

    /**
     * Back button click.
     * @param actionEvent event occurred.
     */
    public void backButtonClick(final ActionEvent actionEvent) {
        this.previousView(); // TODO call reload of the previous view before switching!
    }

    /**
     * Change the name with the name provided in the new name text field.
     * @param actionEvent that occurred.
     */
    public void changeNameClicked(final ActionEvent actionEvent) {
        if (newNameTextField.getText().isEmpty()) {
            showAlertAndWait(NEW_NAME_EMPTY);
            return;
        }
        final Result<AbstractArticleViewModel> changeNameResult = getController().getWarehouseController()
                                                              .setName(articleIdInt, newNameTextField.getText());
        if (changeNameResult.isError()) {
            showAlertAndWait(changeNameResult.getError().getMessage());
            return;
        }
        this.initData(changeNameResult.getValue());
        this.newNameTextField.clear();
    }

    private void showAlertAndWait(final String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occurred while changing the article name.\n" + message, ButtonType.CLOSE);
        a.showAndWait();
    }
}
