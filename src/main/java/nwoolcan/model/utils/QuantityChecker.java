package nwoolcan.model.utils;

import nwoolcan.utils.Result;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Utils class that performs a check on a {@link Quantity} object.
 */
final class QuantityChecker {

    private static final String NULL_VALUE_MESSAGE = "Value cannot be null.";
    private static final String VALUE_NEGATIVE_MESSAGE = "Value cannot be negative.";
    private static final String NULL_UM_MESSAGE = "Unit of measure cannot be null.";
    private static final String CANNOT_BE_QUANTITY_MESSAGE = "Unit of measure cannot be a quantity.";
    private static final String CANNOT_VALIDATE_QUANTITY_VALUE_MESSAGE = "Cannot validate value for this quantity.";

    private static final Collection<UnitOfMeasure> VALID_UMS = Arrays.asList(
        UnitOfMeasure.MILLILITER,
        UnitOfMeasure.GRAM,
        UnitOfMeasure.UNIT,
        UnitOfMeasure.BOTTLE_33_CL,
        UnitOfMeasure.BOTTLE_50_CL,
        UnitOfMeasure.BOTTLE_75_CL,
        UnitOfMeasure.BOTTLE_MAGNUM
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
    static Result<Quantity> check(final Quantity quantity) {
        return Result.of(quantity)
                      .require(q -> q.getValue() >= 0, new IllegalArgumentException(VALUE_NEGATIVE_MESSAGE))
                      .require(q -> q.getUnitOfMeasure().validateValue(q.getValue()), new IllegalArgumentException(CANNOT_VALIDATE_QUANTITY_VALUE_MESSAGE))
                      .require(q -> VALID_UMS.contains(q.getUnitOfMeasure()), new IllegalArgumentException(CANNOT_BE_QUANTITY_MESSAGE));
    }

    static Collection<UnitOfMeasure> getValidUnitsOfMeasure() {
        return Collections.unmodifiableCollection(VALID_UMS);
    }
}
