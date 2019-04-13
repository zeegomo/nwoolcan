package nwoolcan.model.brewery.batch.step.parameter;

import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Builder for {@link QueryParameter}.
 */
public class QueryParameterBuilder {

    private static final String WRONG_VALUE_FILTER_MESSAGE = "Cannot filter for these values.";
    private static final String WRONG_DATE_FILTER_MESSAGE = "Cannot filter for these dates.";
    private static final String WRONG_SORTING_MESSAGE = "Cannot use more than one sorting method.";

    @Nullable
    private ParameterType parameterType = null;
    @Nullable
    private Number greaterThanValue = null;
    @Nullable
    private Number lessThanValue = null;
    @Nullable
    private Number exactValue = null;
    @Nullable
    private Date startDate = null;
    @Nullable
    private Date endDate = null;

    private boolean sortByValue = false;
    private boolean sortByDate = false;
    private boolean sortDescending = false;

    private Result<Empty> partialResult;

    /**
     * Creates an empty builder.
     */
    public QueryParameterBuilder() {
        partialResult = Result.ofEmpty();
    }

    /**
     * Sets the parameter type to filter for the query.
     * @param parameterType the parameter type to filter.
     * @return this.
     */
    public QueryParameterBuilder parameterType(final ParameterType parameterType) {
        this.parameterType = parameterType;
        return this;
    }

    /**
     * Sets the value to use as lower bound to filter for the query.
     * If the exact value was already set or the lessThanValue has a lower value than the one passed
     * by parameter, sets a {@link IllegalArgumentException} in the build result.
     * @param greaterThanValue the value to use as lower bound to filter for the query.
     * @return this.
     */
    public QueryParameterBuilder greaterThanValue(final Number greaterThanValue) {
        this.partialResult = this.partialResult.require(() -> this.exactValue == null
            && (this.lessThanValue == null || this.lessThanValue.doubleValue() >= greaterThanValue.doubleValue()),
            new IllegalArgumentException(WRONG_VALUE_FILTER_MESSAGE))
                                               .peek(e -> this.greaterThanValue = greaterThanValue);
        return this;
    }

    /**
     * Sets the value to use as upper bound to filter for the query.
     * If the exact value was already set or the greaterThanValue has a greater value than the one passed
     * by parameter, sets a {@link IllegalArgumentException} in the build result.
     * @param lessThanValue the value to use as upper bound to filter for the query.
     * @return this.
     */
    public QueryParameterBuilder lessThanValue(final Number lessThanValue) {
        this.partialResult = this.partialResult.require(() -> this.exactValue == null
                && (this.greaterThanValue == null || this.greaterThanValue.doubleValue() <= lessThanValue.doubleValue()),
            new IllegalArgumentException(WRONG_VALUE_FILTER_MESSAGE))
                                               .peek(e -> this.lessThanValue = lessThanValue);
        return this;
    }

    /**
     * Sets the exact value to use as filter for the query.
     * If one of greaterThanValue or lessThanValue were already set,
     * sets a {@link IllegalArgumentException} in the build result.
     * @param exactValue the exact value to use as filter for the query.
     * @return this.
     */
    public QueryParameterBuilder exactValue(final Number exactValue) {
        this.partialResult = this.partialResult.require(() -> this.greaterThanValue == null && this.lessThanValue == null,
            new IllegalArgumentException(WRONG_VALUE_FILTER_MESSAGE))
                                               .peek(e -> this.exactValue = exactValue);
        return this;
    }

    /**
     * Sets the date to use as a lower bound to filter for the query.
     * If the endDate set is before the date passed by parameter,
     * sets a {@link IllegalArgumentException} in the build result.
     * @param startDate the date to use as a lower bound to filter for the query.
     * @return this.
     */
    public QueryParameterBuilder startDate(final Date startDate) {
        this.partialResult = this.partialResult.require(() -> this.endDate == null || !this.endDate.before(startDate),
            new IllegalArgumentException(WRONG_DATE_FILTER_MESSAGE))
                                               .peek(e -> this.startDate = startDate);
        return this;
    }

    /**
     * Sets the date to use as a upper bound to filter for the query.
     * If the startDate set is after the date passed by parameter,
     * sets a {@link IllegalArgumentException} in the build result.
     * @param endDate the date to use as a upper bound to filter for the query.
     * @return this.
     */
    public QueryParameterBuilder endDate(final Date endDate) {
        this.partialResult = this.partialResult.require(() -> this.startDate == null || !this.startDate.after(endDate),
            new IllegalArgumentException(WRONG_DATE_FILTER_MESSAGE))
                                               .peek(e -> this.endDate = endDate);
        return this;
    }

    /**
     * Sets if needs to be sorted by value. If another sorting method was already set,
     * sets a {@link IllegalArgumentException} in the build result.
     * @param sortByValue if needs to be sorted by value.
     * @return this.
     */
    public QueryParameterBuilder sortByValue(final boolean sortByValue) {
        this.partialResult = this.partialResult.require(() -> !this.sortByDate, new IllegalArgumentException(WRONG_SORTING_MESSAGE))
                                               .peek(e -> this.sortByValue = sortByValue);
        return this;
    }

    /**
     * Sets if needs to be sorted by date. If another sorting method was already set,
     * sets a {@link IllegalArgumentException} in the build result.
     * @param sortByDate if needs to be sorted by date.
     * @return this.
     */
    public QueryParameterBuilder sortByDate(final boolean sortByDate) {
        this.partialResult = this.partialResult.require(() -> !this.sortByValue, new IllegalArgumentException(WRONG_SORTING_MESSAGE))
                                               .peek(e -> this.sortByDate = sortByDate);
        return this;
    }

    /**
     * Sets whether to sort in descending order.
     * @param sortDescending whether to sort in descending order.
     * @return this.
     */
    public QueryParameterBuilder sortDescending(final boolean sortDescending) {
        this.sortDescending = sortDescending;
        return this;
    }

    /**
     * Returns a {@link Result} with the constructed {@link QueryParameter} or with a {@link IllegalArgumentException}
     * if the building operation was not correct.
     * @return a Result with the constructed QueryParameter.
     */
    public Result<QueryParameter> build() {
        return partialResult.map(e -> new QueryParameterImpl(
            parameterType,
            greaterThanValue,
            lessThanValue,
            exactValue,
            startDate,
            endDate,
            sortByValue,
            sortByDate,
            sortDescending
        ));
    }
}
