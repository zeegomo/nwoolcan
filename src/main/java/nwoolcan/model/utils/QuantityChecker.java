package nwoolcan.model.utils;

import nwoolcan.utils.Result;

import java.util.Arrays;
import java.util.Collection;

/**
 * Utils class that performs a check on a {@link Quantity} object.
 */
public final class QuantityChecker {

    private static final String NULL_VALUE_MESSAGE = "Value cannot be null.";
    private static final String VALUE_NEGATIVE_MESSAGE = "Value cannot be negative.";
    private static final String NULL_UM_MESSAGE = "Unit of measure cannot be null.";
    private static final String CANNOT_BE_QUANTITY_MESSAGE = "Unit of measure cannot be a quantity.";

    private static final Collection<UnitOfMeasure> VALID_UMS = Arrays.asList(
        UnitOfMeasure.Liter,
        UnitOfMeasure.Kilogram,
        UnitOfMeasure.Pound,
        UnitOfMeasure.Unit
    );

    private QuantityChecker() { }

    /**
     * Performs a check on the {@link Quantity} passed by parameter, returning a {@link Result}
     * with a {@link NullPointerException} error if the quantity value is null or the quantity
     * unit of measure is null,
     * with a {@link IllegalArgumentException} error if the quantity value is negative or the quantity
     * unit of measure is not valid for a quantity,
     * otherwise with the same quantity passed by parameter.
     * @param quantity quantity to check.
     * @return a {@link Result} with same quantity if the check passes, with an error otherwise.
     */
    public static Result<Quantity> check(final Quantity quantity) {
        return Result.of(quantity)
                      .require(q -> q.getValue().doubleValue() >= 0, new IllegalArgumentException(VALUE_NEGATIVE_MESSAGE))
                      .require(q -> VALID_UMS.contains(q.getUnitOfMeasure()), new IllegalArgumentException(CANNOT_BE_QUANTITY_MESSAGE));
    }
}
