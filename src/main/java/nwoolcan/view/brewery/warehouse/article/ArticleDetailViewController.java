package nwoolcan.view.brewery.warehouse.article;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;

/**
 * Controller class for article detail view.
 */
@SuppressWarnings("NullAway")
public class ArticleDetailViewController extends SubViewController implements InitializableController<AbstractArticleViewModel> {
    @FXML
    private SubViewContainer articleDetailContainer;
    @FXML
    private TextField newArticleName;
    @FXML
    private ComboBox newArticleUnitOfMeasure;
    @FXML
    private ComboBox newArticleType;
    @FXML
    private FlowPane ingredientTypeFlowPane;
    @FXML
    private Label articleIngredientType;
    @FXML
    private Button createArticleButton;
    @FXML
    private SubView articleDetailSubView;

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

    }

    @Override
    protected SubView getSubView() {
        return articleDetailSubView;
    }

    /**
     * Back button click.
     * @param actionEvent event occurred.
     */
    public void BackButtonClick(final ActionEvent actionEvent) {
        this.previousView();
    }
}
