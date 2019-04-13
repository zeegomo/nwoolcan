package nwoolcan.model.brewery.batch.step.parameter;

import java.util.Date;
import java.util.Optional;

/**
 * Interface representing a query to perform on a collection of parameters.
 */
public interface QueryParameter {
    /**
     * Returns the {@link ParameterType} to use as a filter in the query.
     * @return the {@link ParameterType} to use as a filter in the query.
     */
    Optional<ParameterType> getParameterType();

    /**
     * Returns the {@link Number} that represent the lower bound of the value filter.
     * @return the {@link Number} that represent the lower bound of the value filter.
     */
    Optional<Number> getGreaterThanValue();

    /**
     * Returns the {@link Number} that represent the upper bound of the value filter.
     * @return the {@link Number} that represent the upper bound of the value filter.
     */
    Optional<Number> getLessThanValue();

    /**
     * Returns the {@link Number} that represent the exact value filter.
     * @return the {@link Number} that represent the exact value filter.
     */
    Optional<Number> getExactValue();

    /**
     * Returns the {@link Date} that represent the lower bound of the date filter.
     * @return the {@link Date} that represent the lower bound of the date filter.
     */
    Optional<Date> getStartDate();

    /**
     * Returns the {@link Date} that represent the upper bound of the date filter.
     * @return the {@link Date} that represent the upper bound of the date filter.
     */
    Optional<Date> getEndDate();

    /**
     * Returns true if needs to be sorted by value, false otherwise.
     * @return true if needs to be sorted by value, false otherwise.
     */
    boolean isSortByValue();

    /**
     * Returns true if needs to be sorted by date, false otherwise.
     * @return true if needs to be sorted by date, false otherwise.
     */
    boolean isSortByDate();

    /**
     * Returns true if sorting in descending order, false otherwise.
     * @return true if sorting in descending order, false otherwise.
     */
    boolean isSortDescending();
}
