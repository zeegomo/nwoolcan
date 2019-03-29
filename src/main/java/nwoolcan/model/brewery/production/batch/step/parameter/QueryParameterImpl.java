package nwoolcan.model.brewery.production.batch.step.parameter;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

/**
 * Object for representing a query to perform on a collection of parameters.
 * Package-private.
 */
final class QueryParameterImpl implements QueryParameter {

    @Nullable
    private final ParameterType parameterType;
    @Nullable
    private final Number greaterThanValue;
    @Nullable
    private final Number lessThanValue;
    @Nullable
    private final Number exactValue;
    @Nullable
    private final Date startDate;
    @Nullable
    private final Date endDate;

    private final boolean sortByValue;
    private final boolean sortByDate;
    private final boolean sortDescending;

    //Package-private.
    QueryParameterImpl(@Nullable final ParameterType parameterType,
                       @Nullable final Number greaterThanValue,
                       @Nullable final Number lessThanValue,
                       @Nullable final Number exactValue,
                       @Nullable final Date startDate,
                       @Nullable final Date endDate,
                       final boolean sortByValue,
                       final boolean sortByDate,
                       final boolean sortDescending) {
        this.parameterType = parameterType;
        this.greaterThanValue = greaterThanValue;
        this.lessThanValue = lessThanValue;
        this.exactValue = exactValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sortByValue = sortByValue;
        this.sortByDate = sortByDate;
        this.sortDescending = sortDescending;
    }

    @Override
    public Optional<ParameterType> getParameterType() {
        return Optional.ofNullable(parameterType);
    }

    @Override
    public Optional<Number> getGreaterThanValue() {
        return Optional.ofNullable(greaterThanValue);
    }

    @Override
    public Optional<Number> getLessThanValue() {
        return Optional.ofNullable(lessThanValue);
    }

    @Override
    public Optional<Number> getExactValue() {
        return Optional.ofNullable(exactValue);
    }

    @Override
    public Optional<Date> getStartDate() {
        return Optional.ofNullable(startDate).map(d -> new Date(d.getTime()));
    }

    @Override
    public Optional<Date> getEndDate() {
        return Optional.ofNullable(endDate).map(d -> new Date(d.getTime()));
    }

    @Override
    public boolean isSortByValue() {
        return sortByValue;
    }

    @Override
    public boolean isSortByDate() {
        return sortByDate;
    }

    @Override
    public boolean isSortDescending() {
        return sortDescending;
    }

    @Override
    public String toString() {
        return "[QueryParameter] {"
            + "parameterType=" + parameterType
            + ", greaterThanValue=" + greaterThanValue
            + ", lessThanValue=" + lessThanValue
            + ", exactValue=" + exactValue
            + ", startDate=" + startDate
            + ", endDate=" + endDate
            + ", sortByValue=" + sortByValue
            + ", sortByDate=" + sortByDate
            + ", sortDescending=" + sortDescending
            + '}';
    }
}
