package nwoolcan.controller.warehouse;

import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.Record;
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

/**
 * Controller of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
 */
public interface WarehouseController {

    /**
     * Return an {@link ArticlesInfoViewModel} which gives general info about all the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return an {@link ArticlesInfoViewModel} which gives general info about all the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    ArticlesInfoViewModel getArticlesViewModel();
    /**
     * Creates a {@link List} of {@link AbstractArticleViewModel} accordingly to the query.
     * @param queryArticle describes the nature of the query.
     * @return a {@link List} of {@link AbstractArticleViewModel} accordingly to the query.
     */
    List<AbstractArticleViewModel> getArticles(QueryArticle queryArticle);
    /**
     * Creates a {@link List} of {@link MasterStockViewModel} accordingly to the query.
     * @param queryStock describes the nature of the query.
     * @return a {@link List} of {@link MasterStockViewModel} accordingly to the query.
     */
    List<MasterStockViewModel> getStocks(QueryStock queryStock);
    /**
     * Return an updated instance of the {@link WarehouseViewModel}.
     * @return an updated instance of the {@link WarehouseViewModel}.
     */
    WarehouseViewModel getWarehouseViewModel();
    /**
     * Create a {@link nwoolcan.model.brewery.warehouse.article.Article} and return its {@link AbstractArticleViewModel}.
     * @param name of the new {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param unitOfMeasure of the new {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the {@link AbstractArticleViewModel}.
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
     * Create a {@link nwoolcan.model.brewery.warehouse.stock.Stock} and return its {@link PlainStockViewModel}.
     * @param articleId the id of the article linked to the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param expirationDate the {@link Date} in which this {@link nwoolcan.model.brewery.warehouse.stock.Stock} will expire.
     * @return the {@link PlainStockViewModel} of the new {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    Result<PlainStockViewModel> createStock(int articleId, Date expirationDate);
    /**
     * Create a {@link nwoolcan.model.brewery.warehouse.stock.Stock} and return its {@link PlainStockViewModel}.
     * @param articleId the id of the article linked to the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @return the {@link PlainStockViewModel} of the new {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    Result<PlainStockViewModel> createStock(int articleId);
    /**
     * Set a new name of an existing {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param articleId of the existing {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param newName to be set.
     * @return the new {@link AbstractArticleViewModel} representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    Result<AbstractArticleViewModel> setName(int articleId, String newName);
    /**
     * Add a record to the stock identified by the id.
     * @param stockId to identify the stock.
     * @param amount to be added.
     * @param action the {@link Record.Action} denoting whether the quantity is going to be added or subtracted.
     * @param date the {@link Date} associated to the {@link Record}.
     * @return a {@link Result} denoting possible errors.
     */
    Result<Empty> addRecord(int stockId, double amount, Record.Action action, Date date);
    /**
     * Add a record to the stock identified by the id.
     * @param stockId to identify the stock.
     * @param amount to be added.
     * @param action the {@link Record.Action} denoting whether the quantity is going to be added or subtracted.
     * @return a {@link Result} denoting possible errors.
     */
    Result<Empty> addRecord(int stockId, double amount, Record.Action action);
}
