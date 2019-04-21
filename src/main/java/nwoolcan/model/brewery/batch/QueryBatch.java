package nwoolcan.model.brewery.batch;

import nwoolcan.model.utils.Quantity;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

/**
 * Query Batch. All the getters return an parametric {@link Optional},
 * because each field can be present or not.
 */
public class QueryBatch {

    @Nullable private final Integer batchId;
    @Nullable private final String beerName;
    @Nullable private final String beerStyle;
    @Nullable private final BatchMethod batchMethod;
    @Nullable private final Quantity minBatchSize;
    @Nullable private final Quantity maxBatchSize;
    @Nullable private final Date minStartDate;
    @Nullable private final Boolean onlyEnded;

    /**
     * Constructor of the class.
     * @param batchId the only batch id to be displayed.
     * @param batchMethod the only {@link BatchMethod} to be displayed.
     * @param beerName the only beer name to be displayed.
     * @param beerStyle the only beer style to be displayed.
     * @param minBatchSize a {@link Quantity} denoting the minimum size of the {@link Batch}.
     * @param maxBatchSize a {@link Quantity} denoting the maximum size of the {@link Batch}.
     * @param minStartDate a {@link Date} denoting the lower bound for the batch start date.
     * @param onlyEnded true to filter for only ended batches.
     */
    // Package-private
    QueryBatch(@Nullable final Integer batchId,
               @Nullable final String beerName,
               @Nullable final String beerStyle,
               @Nullable final BatchMethod batchMethod,
               @Nullable final Quantity minBatchSize,
               @Nullable final Quantity maxBatchSize,
               @Nullable final Date minStartDate,
               @Nullable final Boolean onlyEnded) {
        this.batchId = batchId;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.batchMethod = batchMethod;
        this.minBatchSize = minBatchSize;
        this.maxBatchSize = maxBatchSize;
        this.minStartDate = minStartDate == null ? null : new Date(minStartDate.getTime());
        this.onlyEnded = onlyEnded;
    }
    /**
     * Returns the batch id if the batch to be displayed.
     * @return the batch id if the batch to be displayed.
     */
    public Optional<Integer> getBatchId() {
        return Optional.ofNullable(this.batchId);
    }
    /**
     * Returns the only beer name to be displayed.
     * @return the only beer name to be displayed.
     */
    public Optional<String> getBeerName() {
        return Optional.ofNullable(this.beerName);
    }
    /**
     * Returns the only beer style to be displayed.
     * @return the only beer style to be displayed.
     */
    public Optional<String> getBeerStyle() {
        return Optional.ofNullable(this.beerStyle);
    }
    /**
     * Returns the only {@link BatchMethod} to be displayed.
     * @return the only {@link BatchMethod} to be displayed.
     */
    public Optional<BatchMethod> getBatchMethod() {
        return Optional.ofNullable(this.batchMethod);
    }
    /**
     * Returns a {@link Quantity} denoting the minimum size of the {@link Batch}.
     * @return a {@link Quantity} denoting the minimum size of the {@link Batch}.
     */
    public Optional<Quantity> getMinBatchSize() {
        return Optional.ofNullable(this.minBatchSize);
    }
    /**
     * Returns a {@link Quantity} denoting the maximum size of the {@link Batch}.
     * @return a {@link Quantity} denoting the maximum size of the {@link Batch}.
     */
    public Optional<Quantity> getMaxBatchSize() {
        return Optional.ofNullable(this.maxBatchSize);
    }
    /**
     * Returns the lower bound of batch start date.
     * @return the lower bound of batch start date.
     */
    public Optional<Date> getMinStartDate() {
        return Optional.ofNullable(this.minStartDate == null ? null : new Date(this.minStartDate.getTime()));
    }
    /**
     * Returns true if the only ended filter is set.
     * @return true if the only ended filter is set.
     */
    public Optional<Boolean> getOnlyEnded() {
        return Optional.ofNullable(this.onlyEnded);
    }
}
