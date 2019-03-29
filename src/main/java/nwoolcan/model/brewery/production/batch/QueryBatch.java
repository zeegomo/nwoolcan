package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.utils.Quantity;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Query Batch.
 */
public class QueryBatch {

    @Nullable private Integer minId;
    @Nullable private Integer maxId;
    @Nullable private BatchMethod batchMethod;
    @Nullable private Quantity minBatchSize;
    @Nullable private Quantity maxBatchSize;

    // Package-private
    QueryBatch(@Nullable Integer minId,
               @Nullable Integer maxId,
               @Nullable BatchMethod batchMethod,
               @Nullable Quantity minBatchSize,
               @Nullable Quantity maxBatchSize) {
        this.minId = minId;
        this.maxId = maxId;
        this.batchMethod = batchMethod;
        this.minBatchSize = minBatchSize;
        this.maxBatchSize = maxBatchSize;
    }

    /**
     * Return an int denoting the min id to be displayed.
     * @return an int denoting the min id to be displayed.
     */
    public Optional<Integer> getMinId() {
        return Optional.ofNullable(minId);
    }
    /**
     * Return an int denoting the max id to be displayed.
     * @return an int denoting the max id to be displayed.
     */
    public Optional<Integer> getMaxId() {
        return Optional.ofNullable(maxId);
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
