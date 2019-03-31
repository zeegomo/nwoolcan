package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Date;
import java.util.List;

/**
 * Warehouse.
 */
public interface Warehouse {

    /**
     * Getter of the {@link Stock} in the warehouse, filtered with a {@link QueryStock}.
     * @param queryStock describes the nature of the query.
     * @return a {@link List} of {@link Stock} which respects the given {@link QueryStock}.
     */
    List<Stock> getStocks(QueryStock queryStock);
    /**
     * Getter of the {@link Article} in the warehouse, filtered with a {@link QueryArticle}.
     * @param queryArticle describes the nature of the query.
     * @return a {@link List} of {@link Article} which respects the given {@link QueryArticle}.
     */
    List<Article> getArticles(QueryArticle queryArticle);
    /**
     * Add a record to the warehouse: creates a {@link Stock} if it doesn't exist.
     * It may fails if the {@link Record} is not compatible with the {@link Article}.
     * @param article which is moving in or out the warehouse.
     * @param expirationDate of the article, if present, to identify the right {@link Stock}.
     * @param record to be registered
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> addRecord(Article article, Date expirationDate, Record record);
    /**
     * Add a record to the warehouse: creates a {@link Stock} if it doesn't exist.
     * It may fails if the {@link Record} is not compatible with the {@link Article}.
     * @param article which is moving in or out the warehouse.
     * @param record to be registered.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> addRecord(Article article, Record record);
    /**
     * If it does not exist, creates a misc {@link Article}, otherwise it will be returned.
     * @param name of the {@link Article}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link Article}.
     * @return a new {@link Article} if it does not exist, otherwise the existing one.
     */
    Article createMiscArticle(String name, UnitOfMeasure unitOfMeasure);
    /**
     * If it does not exist, creates a {@link BeerArticle}, otherwise it will be returned.
     * @param name of the {@link BeerArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link BeerArticle}.
     * @return a new {@link BeerArticle} if it does not exist, otherwise the existing one.
     */
    BeerArticle createBeerArticle(String name, UnitOfMeasure unitOfMeasure);
    /**
     * If it does not exist, creates a {@link IngredientArticle}, otherwise it will be returned.
     * @param name of the {@link IngredientArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link IngredientArticle}.
     * @param ingredientType the {@link IngredientType} of the {@link IngredientArticle}.
     * @return a new {@link IngredientArticle} if it does not exist, otherwise the existing one.
     */
    IngredientArticle createIngredientArticle(String name,
                                              UnitOfMeasure unitOfMeasure,
                                              IngredientType ingredientType);

}
