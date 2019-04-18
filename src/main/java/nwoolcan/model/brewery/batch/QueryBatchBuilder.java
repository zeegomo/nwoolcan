package nwoolcan.model.brewery.batch;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;

/**
 * The builder of {@link QueryBatch}.
 */
public class QueryBatchBuilder {

    @Nullable private BatchMethod batchMethod;
    @Nullable private Quantity minBatchSize;
    @Nullable private Quantity maxBatchSize;

    /**
     * Constructor to initialize the queryBuilder.
     */
    public QueryBatchBuilder() { }
    /**
     * Setter of the {@link BatchMethod}.
     * @param method the {@link BatchMethod}.
     * @return this.
     */
    public QueryBatchBuilder setBatchMethod(final BatchMethod method) {
        batchMethod = method;
        return this;
    }
    /**
     * Setter of the min batch size.
     * @param size a {@link Quantity} denoting the min batch size.
     * @return this.
     */
    public QueryBatchBuilder setMinBatchSize(final Quantity size) {
        minBatchSize = size;
        return this;
    }
    /**
     * Setter of the max batch size.
     * @param size a {@link Quantity} denoting the max batch size.
     * @return this.
     */
    public QueryBatchBuilder setMaxBatchSize(final Quantity size) {
        maxBatchSize = size;
        return this;
    }
    /**
     * Builds the QueryBatch.
     * @return the built {@link QueryBatch}.
     */
    public Result<QueryBatch> build() {
        return Result.of(new QueryBatch(batchMethod, minBatchSize, maxBatchSize))
                     .require(this::checkBatchSize);
    }
    /**
     * Check the {@link UnitOfMeasure} of the batch size.
     * @return a boolean denoting the consistency of the uom used.
     */
    private boolean checkBatchSize() {
        if (minBatchSize != null
         && minBatchSize.getUnitOfMeasure() != UnitOfMeasure.LITER) {
            return false;
        }
        if (maxBatchSize != null
         && maxBatchSize.getUnitOfMeasure() != UnitOfMeasure.LITER) {
            return false;
        }
        return true;
    }
}
