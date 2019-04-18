package nwoolcan.model.brewery.batch;

import nwoolcan.model.utils.Quantity;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Query Batch. All the getters return an parametric {@link Optional},
 * because each field can be present or not.
 */
public class QueryBatch {

    @Nullable private final BatchMethod batchMethod;
    @Nullable private final Quantity minBatchSize;
    @Nullable private final Quantity maxBatchSize;

    /**
     * Constructor of the class.
     * @param batchMethod the only {@link BatchMethod} to be displayed.
     * @param minBatchSize a {@link Quantity} denoting the minimum size of the {@link Batch}.
     * @param maxBatchSize a {@link Quantity} denoting the maximum size of the {@link Batch}.
     */
    // Package-private
    QueryBatch(@Nullable final BatchMethod batchMethod,
               @Nullable final Quantity minBatchSize,
               @Nullable final Quantity maxBatchSize) {
        this.batchMethod = batchMethod;
        this.minBatchSize = minBatchSize;
        this.maxBatchSize = maxBatchSize;
    }
    /**
     * Return the only {@link BatchMethod} to be displayed.
     * @return the only {@link BatchMethod} to be displayed.
     */
    public Optional<BatchMethod> getBatchMethod() {
        return Optional.ofNullable(batchMethod);
    }
    /**
     * Return a {@link Quantity} denoting the minimum size of the {@link Batch}.
     * @return a {@link Quantity} denoting the minimum size of the {@link Batch}.
     */
    public Optional<Quantity> getMinBatchSize() {
        return Optional.ofNullable(minBatchSize);
    }
    /**
     * Return a {@link Quantity} denoting the maximum size of the {@link Batch}.
     * @return a {@link Quantity} denoting the maximum size of the {@link Batch}.
     */
    public Optional<Quantity> getMaxBatchSize() {
        return Optional.ofNullable(maxBatchSize);
    }

}
