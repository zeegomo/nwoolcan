package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.utils.Quantity;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Query Batch. All the getters return an parametric {@link Optional},
 * because each field can be present or not.
 */
public class QueryBatch {

    @Nullable private final Integer minId;
    @Nullable private final Integer maxId;
    @Nullable private final BatchMethod batchMethod;
    @Nullable private final Quantity minBatchSize;
    @Nullable private final Quantity maxBatchSize;

    /**
     * Constructor of the class.
     * @param minId the min id to be displayed.
     * @param maxId the max id to be displayed.
     * @param batchMethod the only {@link BatchMethod} to be displayed.
     * @param minBatchSize a {@link Quantity} denoting the minimum size of the {@link Batch}.
     * @param maxBatchSize a {@link Quantity} denoting the maximum size of the {@link Batch}.
     */
    // Package-private
    QueryBatch(@Nullable final Integer minId,
               @Nullable final Integer maxId,
               @Nullable final BatchMethod batchMethod,
               @Nullable final Quantity minBatchSize,
               @Nullable final Quantity maxBatchSize) {
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
