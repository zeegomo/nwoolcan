package nwoolcan.viewmodel.brewery.production.batch;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * DTO for storing data necessary to stock a batch.
 */
public final class StockBatchDTO {

    private final int batchId;
    private final int beerArticleId;

    @Nullable
    private final Date expirationDate;

    /**
     * Basic constructor.
     * @param batchId the id of the batch to stock.
     * @param beerArticleId the id of the beer article to use for stock creation,
     * @param expirationDate the possible expiration date of the new beer stock.
     */
    public StockBatchDTO(final int batchId,
                         final int beerArticleId,
                         @Nullable final Date expirationDate) {
        this.batchId = batchId;
        this.beerArticleId = beerArticleId;
        this.expirationDate = expirationDate == null ? null : new Date(expirationDate.getTime());
    }

    /**
     * Returns the id of the batch to stock.
     * @return the id of the batch to stock.
     */
    public int getBatchId() {
        return this.batchId;
    }

    /**
     * Returns the id of the beer article to use for stock creation.
     * @return the id of the beer article to use for stock creation.
     */
    public int getBeerArticleId() {
        return this.beerArticleId;
    }

    /**
     * Returns the possible expiration date of the new beer stock to create.
     * @return the possible expiration date of the new beer stock to create.
     */
    @Nullable
    public Date getExpirationDate() {
        return this.expirationDate;
    }
}
