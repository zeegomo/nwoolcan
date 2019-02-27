package nwoolcan.model.brewery.production.batch.step;

import java.util.Date;
import java.util.Optional;

/**
 * Object for representing a query to perform on a collection of parameters.
 */
public class QueryParameter {

    private ParameterType parameterType;
    private Number greaterThanValue;
    private Number lessThanValue;
    private Number exactValue;
    private Date startDate;
    private Date endDate;
    private boolean sortByValue = false;
    private boolean sortByDate = false;
    private boolean sortDescending = false;

    /**
     * Empty constructor. In order to build the object use the builder methods.
     */
    public QueryParameter() { }

    /**
     * Returns the {@link ParameterType} to use as a filter in the query.
     * @return the {@link ParameterType} to use as a filter in the query.
     */
    public Optional<ParameterType> getParameterType() {
        return Optional.ofNullable(parameterType);
    }

    /**
     * Returns the {@link Number} that represent the lower bound of the value filter.
     * @return the {@link Number} that represent the lower bound of the value filter.
     */
    public Optional<Number> getGreaterThanValue() {
        return Optional.ofNullable(greaterThanValue);
    }

    /**
     * Returns the {@link Number} that represent the upper bound of the value filter.
     * @return the {@link Number} that represent the upper bound of the value filter.
     */
    public Optional<Number> getLessThanValue() {
        return Optional.ofNullable(lessThanValue);
    }

    /**
     * Returns the {@link Number} that represent the exact value filter.
     * @return the {@link Number} that represent the exact value filter.
     */
    public Optional<Number> getExactValue() {
        return Optional.ofNullable(exactValue);
    }

    /**
     * Returns the {@link Date} that represent the lower bound of the date filter.
     * @return the {@link Date} that represent the lower bound of the date filter.
     */
    public Optional<Date> getStartDate() {
        return Optional.ofNullable(startDate).map(d -> new Date(d.getTime()));
    }

    /**
     * Returns the {@link Date} that represent the upper bound of the date filter.
     * @return the {@link Date} that represent the upper bound of the date filter.
     */
    public Optional<Date> getEndDate() {
        return Optional.ofNullable(endDate).map(d -> new Date(d.getTime()));
    }

    /**
     * Returns true if needs to be sorted by value, false otherwise.
     * @return true if needs to be sorted by value, false otherwise.
     */
    public boolean isSortByValue() {
        return sortByValue;
    }

    /**
     * Returns true if needs to be sorted by date, false otherwise.
     * @return true if needs to be sorted by date, false otherwise.
     */
    public boolean isSortByDate() {
        return sortByDate;
    }

    /**
     * Returns true if sorting in descending order, false otherwise.
     * @return true if sorting in descending order, false otherwise.
     */
    public boolean isSortDescending() {
        return sortDescending;
    }

    /**
     * Sets the parameter type value to filter.
     * @param parameterType the parameter type value to filter.
     * @return myself after setting the parameter type value to filter.
     */
    public QueryParameter parameterType(final ParameterType parameterType) {
        this.parameterType = parameterType;
        return this;
    }

    /**
     * Sets the value to use as lower bound for filtering.
     * @param value the value to use as lower bound for filtering.
     * @return myself after setting the value to use as lower bound for filtering.
     */
    public QueryParameter greaterThanValue(final Number value) {
        this.greaterThanValue = value;
        return this;
    }

    /**
     * Sets the value to use as upper bound for filtering.
     * @param value the value to use as upper bound for filtering.
     * @return myself after setting the value to use as upper bound for filtering.
     */
    public QueryParameter lessThanValue(final Number value) {
        this.lessThanValue = value;
        return this;
    }

    /**
     * Sets the exact value to filter.
     * @param value the exact value to filter.
     * @return myself after setting the exact value to filter.
     */
    public QueryParameter exactValue(final Number value) {
        this.exactValue = value;
        return this;
    }

    /**
     * Sets the date to use as lower bound for filtering.
     * @param startDate the date to use as lower bound for filtering.
     * @return myself after setting the date to use as lower bound for filtering.
     */
    public QueryParameter startDate(final Date startDate) {
        if (startDate != null) {
            this.startDate = new Date(startDate.getTime());
        }
        return this;
    }

    /**
     * Sets the date to use as upper bound for filtering.
     * @param endDate the date to use as upper bound for filtering.
     * @return myself after setting the date to use as upper bound for filtering.
     */
    public QueryParameter endDate(final Date endDate) {
        if (endDate != null) {
            this.endDate = new Date(endDate.getTime());
        }
        return this;
    }

    /**
     * Sets weather needed to sort parameters by value.
     * @param sort true if needed to sort parameters by value, false otherwise.
     * @return myself after setting if needed to sort parameters by value.
     */
    public QueryParameter sortByValue(final boolean sort) {
        this.sortByValue = sort;
        return this;
    }

    /**
     * Sets weather needed to sort parameters by date.
     * @param sort true if needed to sort parameters by date, false otherwise.
     * @return myself after setting if needed to sort parameters by date.
     */
    public QueryParameter sortByDate(final boolean sort) {
        this.sortByDate = sort;
        return this;
    }

    /**
     * Sets weather needed to sort parameters in descending order.
     * @param sort true if needed to sort parameters in descending order, false otherwise.
     * @return myself after setting if needed to sort parameters in descending order.
     */
    public QueryParameter sortDescending(final boolean sort) {
        this.sortDescending = sort;
        return this;
    }
}
