package nwoolcan.view.warehouse.article;

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
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.view.InitializableController;
import nwoolcan.view.SubViewController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.mastertable.MasterTableViewModel;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticlesInfoViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for articles view.
 */
@SuppressWarnings("NullAway")
public final class ArticlesInfoViewController extends SubViewController implements InitializableController<ArticlesInfoViewModel> {

    @FXML
    private Label lblTotalNumberArticles;
    @FXML
    private Label lblNumberBeerArticles;
    @FXML
    private Label lblNumberMiscArticles;
    @FXML
    private Label lblNumberIngredientArticles;

    @FXML
    private PieChart pieChartArticlesStatus;

    @FXML
    private SubView articlesSubView;
    @FXML
    private SubViewContainer masterTableContainer;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public ArticlesInfoViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final ArticlesInfoViewModel data) {
        lblTotalNumberArticles.setText(Long.toString(data.getArticles().size()));
        lblNumberBeerArticles.setText(Long.toString(data.getnBeerArticles()));
        lblNumberMiscArticles.setText(Long.toString(data.getnMiscArticles()));
        lblNumberIngredientArticles.setText(Long.toString(data.getnIngredientArticles()));
        pieChartArticlesStatus.setData(
            FXCollections.observableArrayList(
                new PieChart.Data("Beer", data.getnBeerArticles()),
                new PieChart.Data("Misc", data.getnMiscArticles()),
                new PieChart.Data("Ingredient", data.getnIngredientArticles())
            )
        );

        setTable(data.getArticles());
    }

    private void setTable(final List<AbstractArticleViewModel> articles) {
        final MasterTableViewModel<AbstractArticleViewModel, AbstractArticleViewModel> masterViewModel =
            new MasterTableViewModel<>(Arrays.asList(
                                            new ColumnDescriptor("ID", "id"),
                                            new ColumnDescriptor("Name", "name"),
                                            new ColumnDescriptor("UOM", "unitOfMeasure"),
                                            new ColumnDescriptor("Article Type", "articleType")
                                        ),
                                        articles,
                                        ViewType.ARTICLE_DETAIL,
                                        article -> article
            );
        this.getViewManager().getView(ViewType.MASTER_TABLE, masterViewModel).peek(masterTableContainer::substitute);
    }

    @Override
    protected SubView getSubView() {
        return this.articlesSubView;
    }

    @FXML
    private void createNewArticleClick(final ActionEvent event) {
        final Stage modal =  new Stage();
        final Window window = this.getSubView().getScene().getWindow();

        modal.initOwner(window);
        modal.initModality(Modality.WINDOW_MODAL);

        final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_ARTICLE_MODAL).orElse(new AnchorPane()),
            600, 400);

        modal.setScene(scene);
        modal.setY(window.getY() + window.getHeight() / 2 - scene.getHeight() / 2);
        modal.setX(window.getX() + window.getWidth() / 2 - scene.getWidth() / 2);
        modal.setResizable(false);
        modal.showAndWait();

        this.substituteView(ViewType.ARTICLES, this.getController().getWarehouseController().getArticlesViewModel());
    }

    private void updateArticlesTable(final QueryArticle queryArticle) {
        final List<AbstractArticleViewModel> articles = this.getController()
                                                            .getWarehouseController()
                                                            .getArticles(queryArticle);
        setTable(articles);
    }

    @FXML
    private void backButtonClick(final ActionEvent actionEvent) {
        this.previousView();
    }
}
