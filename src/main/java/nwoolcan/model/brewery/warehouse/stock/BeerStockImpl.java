package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

/**
 * {@link BeerStock} implementation.
 */
public final class BeerStockImpl extends StockImpl implements BeerStock {

    private final int batchId;

    /**
     * Constructor.
     * @param beerArticle the {@link BeerArticle} related to the {@link BeerStock}.
     * @param expirationDate the {@link Date} in which this {@link BeerStock} will expire.
     * @param batch the {@link Batch} related to this {@link BeerStock}.
     */
    // Package-Private
    BeerStockImpl(final int id, final BeerArticle beerArticle, @Nullable final Date expirationDate, final Batch batch) {
        super(id, beerArticle, expirationDate);
        this.batchId = batch.getId();
    }

    @Override
    public int getBatchId() {
        return batchId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), batchId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BeerStock)) {
            return false;
        }

        BeerStock other = (BeerStock) obj;
        return super.equals(obj) && batchId == other.getBatchId();
    }

    @Override
    public String toString() {
        return "[BeerStockImpl]{"
            + "articleId=" + getArticleId()
            + ", expirationDate=" + (getExpirationDate().isPresent() ? getExpirationDate().get() : "null")
            + ", records=" + getRecords()
            + ", creationDate=" + getCreationDate()
            + ", lastChangeDate=" + getLastChangeDate()
            + ", remainingQuantity=" + getRemainingQuantity()
            + ", usedQuantity=" + getUsedQuantity()
            + ", batchId=" + getBatchId()
            + '}';
    }
}
