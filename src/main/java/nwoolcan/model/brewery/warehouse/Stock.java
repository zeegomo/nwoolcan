package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Stock.
 */
public interface Stock {

    /**
     * Returns the Id of the current stock.
     * @return the Id of the current stock.
     */
    Integer getId();
    /**
     * Returns the article related to the current stock.
     * @return the article related to the current stock.
     */
    Article getArticle();
    /**
     * Returns the remaining quantity of the current stock.
     * @return the remaining quantity of the current stock.
     */
    Quantity getRemainingQuantity();
    /**
     * Returns the used quantity of the current stock.
     * @return the used quantity of the current stock.
     */
    Quantity getUsedQuantity();
    /**
     * Returns the state of the current stock.
     * @return the state of the current stock.
     */
    StockState getState();
    /**
     * Returns an {@link Optional} of the {@link Date} of Expiration, if it exists. Otherwise an {@link Optional} empty.
     * @return an {@link Optional} of the {@link Date} of Expiration, if it exists. Otherwise an {@link Optional} empty.
     */
    Optional<Date> getExpirationDate();
    /**
     * Returns the creation date.
     * @return the creation date.
     */
    Date getCreationDate();
    /**
     * Returns the last change date.
     * @return the last change date.
     */
    Date getLastChangeDate();
    /**
     * Adds or remove a certain quantity to the current stock.
     * @param record which will be saved into the stock.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> addRecord(Record record);

    /**
     * Getters of all the records of the Stock, ordered by its {@link Date}.
     * @return the list of all the saved records.
     */
    List<Record> getRecords();

}
