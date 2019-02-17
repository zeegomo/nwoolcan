package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Date;
import java.util.Optional;

/**
 * Udc.
 */
public interface Udc {

    /**
     * Returns the Id of the current udc.
     * @return the Id of the current udc.
     */
    Integer getId();
    /**
     * Returns the article related to the current udc.
     * @return the article related to the current udc.
     */
    Article getArticle();
    /**
     * Returns the quantity of the current udc.
     * @return the quantity of the current udc.
     */
    Quantity getQuantity();
    /**
     * Returns the state of the current udc.
     * @return the state of the current udc.
     */
    UdcState getState();
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
     * Adds a certain quantity to the current udc.
     * @param quantity the quantity to be added to the udc.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> addQuantity(Quantity quantity);
    /**
     * Removes a certain quantity from the current udc.
     * @param quantity the {@link Quantity} to be removed from the current udc.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> removeQuantity(Quantity quantity);
    /**
     * Consumes a certain quantity from the current udc creating a new Udc with that quantity.
     * The {@link UdcState} of the new Udc will be CONSUMED.
     * It is possible to change this function only for Article of type BeerArticle.
     * @param quantity the {@link Quantity} to be removed from the current udc.
     * @return a {@link Result} of a new Udc: the consumed one.
     */
    Result<Udc> consume(Quantity quantity);
    /**
     * Sets the current quantity to the quantity specified.
     * @param quantity the quantity that will be set to the current udc.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> setQuantity(Quantity quantity);
    /**
     * Updates the last change date of the current udc.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> updateLastChangeDate();
    /**
     * Changes the state of the current udc to newState.
     * It is not possible to call this function on Article of type BeerArticle. Use consume instead.
     * @param newState the new state of the current udc.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> changeState(UdcState newState);

}
