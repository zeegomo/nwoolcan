package nwoolcan.controller.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.MiscArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.AbstractStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.StockViewModel;

import java.util.Date;
import java.util.List;

/**
 * Controller of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
 */
public interface WarehouseController {
    /**
     * Creates a {@link List} of {@link ArticleViewModel} accordingly to the query.
     * @param queryArticle describes the nature of the query.
     * @return a {@link List} of {@link ArticleViewModel} accordingly to the query.
     */
    List<ArticleViewModel> getArticles(QueryArticle queryArticle);
    /**
     * Creates a {@link List} of {@link AbstractStockViewModel} accordingly to the query.
     * @param queryStock describes the nature of the query.
     * @return a {@link List} of {@link AbstractStockViewModel} accordingly to the query.
     */
    List<AbstractStockViewModel> getStocks(QueryStock queryStock);
    /**
     * Return an updated instance of the {@link WarehouseViewModel}.
     * @return an updated instance of the {@link WarehouseViewModel}.
     */
    WarehouseViewModel getWarhouseViewModel();
    /**
     * Create a {@link nwoolcan.model.brewery.warehouse.article.Article} and return its {@link ArticleViewModel}.
     * @param name of the new {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param unitOfMeasure of the new {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the {@link ArticleViewModel}.
     */
    MiscArticleViewModel createMiscArticle(String name, UnitOfMeasure unitOfMeasure);
    /**
     * Create a {@link nwoolcan.model.brewery.warehouse.article.BeerArticle} and return its {@link BeerArticleViewModel}.
     * @param name of the new {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @param unitOfMeasure of the new {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @return the {@link BeerArticleViewModel}.
     */
    BeerArticleViewModel createBeerArticle(String name, UnitOfMeasure unitOfMeasure);
    /**
     * Create a {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle} and return its {@link IngredientArticleViewModel}.
     * @param name of the new {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param unitOfMeasure of the new {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param ingredientType the type of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @return the {@link IngredientArticleViewModel}.
     */
    IngredientArticleViewModel createIngredientArticle(String name,
                                                       UnitOfMeasure unitOfMeasure,
                                                       IngredientType ingredientType);
    /**
     * Create a {@link nwoolcan.model.brewery.warehouse.stock.Stock} and return its {@link StockViewModel}.
     * @param articleId the id of the article linked to the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param expirationDate the {@link Date} in which this {@link nwoolcan.model.brewery.warehouse.stock.Stock} will expire.
     * @return the {@link StockViewModel} of the new {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    Result<StockViewModel> createStock(int articleId, Date expirationDate);
    /**
     * Create a {@link nwoolcan.model.brewery.warehouse.stock.Stock} and return its {@link StockViewModel}.
     * @param articleId the id of the article linked to the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @return the {@link StockViewModel} of the new {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    Result<StockViewModel> createStock(int articleId);
    /**
     * Set a new name of an existing {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param articleId of the existing {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param newName to be set.
     * @return the new {@link ArticleViewModel} representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    Result<ArticleViewModel> setName(int articleId, String newName);

}
