package nwoolcan.model.brewery.production.batch.step.parameter;

import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.util.Date;

/**
 * Simple static factory for creating parameters.
 */
public final class ParameterFactory {
    private ParameterFactory() { }

    /**
     * Creates a new parameter with specified type, registration value and date.
     * @param type parameter type.
     * @param registrationValue registration value.
     * @param registrationDate registration date.
     * @return a {@link Result} with an error if the parameter unit of measure cannot contain the specified number.
     */
    public static Result<Parameter> create(final ParameterType type,
                                           final Number registrationValue,
                                           final Date registrationDate) {
        return Results.ofChecked(() -> new ParameterImpl(type, registrationValue, registrationDate));
    }

    /**
     * Creates a new parameter with specified type, registration value.
     * The registration date is created with time now.
     * @param type parameter type.
     * @param registrationValue registration value.
     * @return a {@link Result} with an error if the parameter unit of measure cannot contain the specified number.
     */
    public static Result<Parameter> create(final ParameterType type,
                                           final Number registrationValue) {
        return create(type, registrationValue, new Date());
    }
}
