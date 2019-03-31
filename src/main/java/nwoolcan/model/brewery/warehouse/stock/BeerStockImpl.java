package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * {@link BeerStock} implementation.
 */
public final class BeerStockImpl extends StockImpl implements BeerStock {

    private final Batch batch;

    /**
     * Constructor.
     * @param beerArticle the {@link BeerArticle} related to the {@link BeerStock}.
     * @param expirationDate the {@link Date} in which this {@link BeerStock} will expire.
     * @param batch the {@link Batch} related to this {@link BeerStock}.
     */
    // Package-Private
    BeerStockImpl(final BeerArticle beerArticle, @Nullable final Date expirationDate, final Batch batch) {
        super(beerArticle, expirationDate);
        this.batch = batch;
    }

    @Override
    public Batch getBatch() {
        return batch;
    }

    @Override
    public String toString() {
        return "[BeerStockImpl]{"
            + "article=" + getArticle()
            + ", expirationDate=" + (getExpirationDate().isPresent() ? getExpirationDate().get() : "null")
            + ", records=" + getRecords()
            + ", creationDate=" + getCreationDate()
            + ", lastChangeDate=" + getLastChangeDate()
            + ", remainingQuantity=" + getRemainingQuantity()
            + ", usedQuantity=" + getUsedQuantity()
            + ", batch=" + getBatch()
            + '}';
    }
}
