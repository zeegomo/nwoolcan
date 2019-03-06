package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public final class StockImpl implements Stock {

    /**
     * Constructor for Stock with expiration date.
     * @param id of the Stock.
     * @param article referred to the Stock.
     * @param expirationDate of the Stock.
     */
    // Package protected
    StockImpl(final Integer id, final Article article, final Date expirationDate) {

    }

    /**
     * Constructor for Stock without expiration date.
     * @param id of the Stock.
     * @param article referred to the Stock.
     */
    StockImpl(final Integer id, final Article article) {

    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public Article getArticle() {
        return null;
    }

    @Override
    public Quantity getRemainingQuantity() {
        return null;
    }

    @Override
    public Quantity getUsedQuantity() {
        return null;
    }

    @Override
    public StockState getState() {
        return null;
    }

    @Override
    public Optional<Date> getExpirationDate() {
        return Optional.empty();
    }

    @Override
    public Date getCreationDate() {
        return null;
    }

    @Override
    public Date getLastChangeDate() {
        return null;
    }

    @Override
    public Result<Empty> addRecord(final Record record) {
        return null;
    }

    @Override
    public List<Record> getRecords() {
        return null;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
