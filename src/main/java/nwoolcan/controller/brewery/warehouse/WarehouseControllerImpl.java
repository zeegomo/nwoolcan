package nwoolcan.controller.brewery.warehouse;

import nwoolcan.model.brewery.BreweryContext;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.MiscArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.AbstractStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.StockViewModel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller of the warehouse.
 */
public final class WarehouseControllerImpl implements WarehouseController {

    private final Warehouse warehouse;
    private static final String ARTICLE_NOT_FOUND = "Article not found.";

    /**
     * Constructor.
     * @param warehouse to be used as warehouse.
     */
    // TODO remove the brewery singleton in order to use warehouse.
    public WarehouseControllerImpl(final Warehouse warehouse) {
         this.warehouse = BreweryContext.getInstance().getWarehouse();
    }

    @Override
    public List<AbstractArticleViewModel> getArticles(final QueryArticle queryArticle) {
        return warehouse.getArticles(queryArticle)
                        .stream()
                        .map(AbstractArticleViewModel::getViewArticle)
                        .collect(Collectors.toList());
    }

    @Override
    public List<AbstractStockViewModel> getStocks(final QueryStock queryStock) {
        return warehouse.getStocks(queryStock)
                        .stream()
                        .map(AbstractStockViewModel::getViewStock)
                        .collect(Collectors.toList());
    }

    @Override
    public WarehouseViewModel getWarehouseViewModel() {
        return new WarehouseViewModel(warehouse);
    }

    @Override
    public MiscArticleViewModel createMiscArticle(final String name, final UnitOfMeasure unitOfMeasure) {
        return new MiscArticleViewModel(warehouse.createMiscArticle(name, unitOfMeasure));
    }

    @Override
    public BeerArticleViewModel createBeerArticle(final String name, final UnitOfMeasure unitOfMeasure) {
        return new BeerArticleViewModel(warehouse.createBeerArticle(name, unitOfMeasure));
    }

    @Override
    public IngredientArticleViewModel createIngredientArticle(final String name,
                                                              final UnitOfMeasure unitOfMeasure,
                                                              final IngredientType ingredientType) {
        return new IngredientArticleViewModel(warehouse.createIngredientArticle(name,
                                                                                unitOfMeasure,
                                                                                ingredientType));
    }

    @Override
    public Result<StockViewModel> createStock(final int articleId, final Date expirationDate) {
        return Result.of(articleId)
                     .flatMap(this::getArticleById)
                     .flatMap(article -> warehouse.createStock(article, expirationDate))
                     .map(StockViewModel::new);
    }

    @Override
    public Result<StockViewModel> createStock(final int articleId) {
        return Result.of(articleId)
                     .flatMap(this::getArticleById)
                     .flatMap(warehouse::createStock)
                     .map(StockViewModel::new);
    }

    private Result<Article> getArticleById(final int articleId) {
        final QueryArticle queryArticle = new QueryArticleBuilder().setMinID(articleId).setMaxID(articleId).build();
        return Result.of(warehouse.getArticles(queryArticle))
                     .require(articles -> articles.size() == 1, new IllegalArgumentException(ARTICLE_NOT_FOUND))
                     .map(articles -> articles.get(0));
    }

    @Override
    public Result<AbstractArticleViewModel> setName(final int articleId, final String newName) {
        return Result.of(articleId)
                     .flatMap(this::getArticleById)
                     .flatMap(article -> warehouse.setName(article, newName))
                     .map(AbstractArticleViewModel::getViewArticle);
    }

}
