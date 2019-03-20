package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Collection;
import java.util.Date;

/**
 * Warehouse.
 */
public interface Warehouse {

    /**
     * Getter of the {@link Stock} in the warehouse, filtered with a {@link QueryStock}.
     * @param queryStock describes the nature of the query.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Collection<Stock>> getStocks(QueryStock queryStock);
    /**
     * Getter of the {@link Article} in the warehouse, filtered with a {@link QueryArticle}.
     * @param queryArticle describes the nature of the query.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Collection<Article>> getArticles(QueryArticle queryArticle);
    /**
     * Adds a Stock to the warehouse.
     * @param newArticle the new {@link Article} to be registered.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> addArticle(Article newArticle);
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

}
