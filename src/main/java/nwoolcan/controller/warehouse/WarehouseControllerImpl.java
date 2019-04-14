package nwoolcan.controller.warehouse;

import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStockBuilder;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticlesInfoViewModel;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.MiscArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.MasterStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.PlainStockViewModel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller of the warehouse.
 */
public final class WarehouseControllerImpl implements WarehouseController {

    private final Warehouse warehouse;
    private static final String ARTICLE_NOT_FOUND = "Article not found.";
    private static final String STOCK_NOT_FOUND = "Stock not found.";

    /**
     * Constructor.
     * @param warehouse to be used as warehouse.
     */
    public WarehouseControllerImpl(final Warehouse warehouse) {
         this.warehouse = warehouse;
    }

    @Override
    public ArticlesInfoViewModel getArticlesViewModel() {
        return new ArticlesInfoViewModel(warehouse);
    }

    @Override
    public List<AbstractArticleViewModel> getArticles(final QueryArticle queryArticle) {
        return warehouse.getArticles(queryArticle)
                        .stream()
                        .map(AbstractArticleViewModel::getViewArticle)
                        .collect(Collectors.toList());
    }

    @Override
    public List<MasterStockViewModel> getStocks(final QueryStock queryStock) {
        return warehouse.getStocks(queryStock)
                        .stream()
                        .map(MasterStockViewModel::getMasterViewStock)
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
    public Result<PlainStockViewModel> createStock(final int articleId, final Date expirationDate) {
        return Result.of(articleId)
                     .flatMap(this::getArticleById)
                     .flatMap(article -> warehouse.createStock(article, expirationDate))
                     .map(PlainStockViewModel::new);
    }

    @Override
    public Result<PlainStockViewModel> createStock(final int articleId) {
        return Result.of(articleId)
                     .flatMap(this::getArticleById)
                     .flatMap(warehouse::createStock)
                     .map(PlainStockViewModel::new);
    }

    @Override
    public Result<AbstractArticleViewModel> setName(final int articleId, final String newName) {
        return Result.of(articleId)
                     .flatMap(this::getArticleById)
                     .flatMap(article -> warehouse.setName(article, newName))
                     .map(AbstractArticleViewModel::getViewArticle);
    }

    @Override
    public Result<Empty> addRecord(final int stockId, final double amount, final Record.Action action, final Date date) {
        final Result<Stock> stockResult = getStockById(stockId);
        return stockResult.flatMap(stock -> Quantity.of(amount, stock.getArticle().getUnitOfMeasure()))
                          .flatMap(quantity -> stockResult.getValue().addRecord(new Record(quantity, date, action)))
                          .toEmpty();
    }

    @Override
    public Result<Empty> addRecord(final int stockId, final double amount, final Record.Action action) {
        return addRecord(stockId, amount, action, new Date());
    }

    private Result<Article> getArticleById(final int articleId) {
        final QueryArticle queryArticle = new QueryArticleBuilder().setMinID(articleId).setMaxID(articleId).build();
        return Result.of(warehouse.getArticles(queryArticle))
                     .require(articles -> articles.size() == 1, new IllegalArgumentException(ARTICLE_NOT_FOUND))
                     .map(articles -> articles.get(0));
    }

    private Result<Stock> getStockById(final int stockId) {
        final QueryStock queryStock = new QueryStockBuilder().setMinId(stockId).setMaxId(stockId).build().getValue();
        return Result.of(warehouse.getStocks(queryStock))
                     .require(stocks -> stocks.size() == 1, new IllegalArgumentException(STOCK_NOT_FOUND))
                     .map(stocks -> stocks.get(0));
    }

}
