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
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.view.filters.SelectFilter;
import nwoolcan.view.filters.TextFilter;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.mastertable.ColumnDescriptor;
import nwoolcan.view.mastertable.MasterTableViewModel;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;
import nwoolcan.view.utils.ViewModelCallback;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticlesInfoViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for articles view.
 */
@SuppressWarnings("NullAway")
public final class ArticlesInfoViewController extends SubViewController {

    private static final String BEER = "Beer";
    private static final String INGREDIENT = "Ingredient";
    private static final String MISC = "Misc";
    private static final String ID_COLUMN_NAME = "ID";
    private static final String NAME_COLUMN_NAME = "Name";
    private static final String UOM_COLUMN_NAME = "UOM";
    private static final String ARTICLE_TYPE_COLUMN_NAME = "Article Type";
    private static final String ID_FIELD_NAME = "id";
    private static final String NAME_FIELD_NAME = "name";
    private static final String UOM_FIELD_NAME = "unitOfMeasure";
    private static final String ARTICLE_TYPE_FIELD_NAME = "articleTypeSummary";


    @FXML
    private SelectFilter<ArticleType> excludeTypeFilter;
    @FXML
    private SelectFilter<ArticleType> includeTypeFilter;
    @FXML
    private TextFilter nameFilter;
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

    @FXML
    private void initialize() {
        final ArticlesInfoViewModel data = getController().getWarehouseController().getArticlesViewModel();
        lblTotalNumberArticles.setText(Long.toString(data.getArticles().size()));
        lblNumberBeerArticles.setText(Long.toString(data.getnBeerArticles()));
        lblNumberMiscArticles.setText(Long.toString(data.getnMiscArticles()));
        lblNumberIngredientArticles.setText(Long.toString(data.getnIngredientArticles()));
        pieChartArticlesStatus.setData(
            FXCollections.observableArrayList(
                new PieChart.Data(BEER, data.getnBeerArticles()),
                new PieChart.Data(MISC, data.getnMiscArticles()),
                new PieChart.Data(INGREDIENT, data.getnIngredientArticles())
            )
        );

        setTable(data.getArticles());
    }

    private void setTable(final List<AbstractArticleViewModel> articles) {
        final MasterTableViewModel<AbstractArticleViewModel, ViewModelCallback<AbstractArticleViewModel>> masterViewModel =
            new MasterTableViewModel<>(Arrays.asList(
                                            new ColumnDescriptor(ID_COLUMN_NAME, ID_FIELD_NAME),
                                            new ColumnDescriptor(NAME_COLUMN_NAME, NAME_FIELD_NAME),
                                            new ColumnDescriptor(UOM_COLUMN_NAME, UOM_FIELD_NAME),
                                            new ColumnDescriptor(ARTICLE_TYPE_COLUMN_NAME, ARTICLE_TYPE_FIELD_NAME)
                                        ),
                                        articles,
                                        ViewType.ARTICLE_DETAIL,
                                        article -> new ViewModelCallback<>(article, this::initialize)
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

        final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_ARTICLE_MODAL).orElse(new AnchorPane()));

        modal.setScene(scene);
        modal.centerOnScreen();
        modal.showAndWait();

        this.substituteView(ViewType.ARTICLES);
    }

    private void updateArticlesTable(final QueryArticle queryArticle) {
        final List<AbstractArticleViewModel> articles = this.getController()
                                                            .getWarehouseController()
                                                            .getArticles(queryArticle);
        setTable(articles);
    }

    @FXML
    private void backButtonClick(final ActionEvent actionEvent) {
        this.substituteView(ViewType.DASHBOARD);
    }

    @FXML
    private void applyFiltersClicked(final ActionEvent event) {
        final QueryArticleBuilder builder = new QueryArticleBuilder();
        this.excludeTypeFilter.getValue().ifPresent(builder::setExcludeArticleType);
        this.includeTypeFilter.getValue().ifPresent(builder::setIncludeArticleType);
        this.nameFilter.getValue().ifPresent(v -> {
            builder.setMaxName(v);
            builder.setMinName(v + "~");
        });
        this.updateArticlesTable(builder.build());
    }
}
