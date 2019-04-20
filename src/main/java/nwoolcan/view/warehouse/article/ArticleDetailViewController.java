package nwoolcan.view.warehouse.article;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import nwoolcan.controller.Controller;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.utils.Result;
import nwoolcan.view.InitializableController;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.utils.ViewModelCallback;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;

/**
 * Controller class for article detail view.
 */
@SuppressWarnings("NullAway")
public final class ArticleDetailViewController extends SubViewController implements InitializableController<ViewModelCallback<AbstractArticleViewModel>> {

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
    private Runnable updateFather;

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
    public void initData(final ViewModelCallback<AbstractArticleViewModel> dataAndRunner) {
        this.updateFather = dataAndRunner.getCallback();
        loadData(dataAndRunner.getViewModel());
    }

    private void loadData(final AbstractArticleViewModel data) {
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

    @FXML
    private void backButtonClick(final ActionEvent actionEvent) {
        updateFather.run();
        this.previousView();
    }

    @FXML
    private void changeNameClicked(final ActionEvent actionEvent) {
        if (newNameTextField.getText().trim().isEmpty()) {
            showAlertAndWait(NEW_NAME_EMPTY);
            return;
        }
        final Result<AbstractArticleViewModel> changeNameResult = getController().getWarehouseController()
                                                              .setName(articleIdInt, newNameTextField.getText().trim());
        if (changeNameResult.isError()) {
            showAlertAndWait(changeNameResult.getError().getMessage());
            return;
        }
        this.loadData(changeNameResult.getValue());
        this.newNameTextField.clear();
    }

    private void showAlertAndWait(final String message) {
        this.showErrorAndWait("An error occurred while changing the article name.\n" + message,
            this.articleUnitOfMeasure.getScene().getWindow());
    }
}
