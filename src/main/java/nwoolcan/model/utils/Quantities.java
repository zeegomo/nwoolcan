package nwoolcan.model.utils;

import nwoolcan.utils.Result;

import java.math.BigDecimal;

/**
 * Utils class for operations with quantity objects.
 */
public final class Quantities {

    private static final String NOT_SAME_UM_MESSAGE = "Cannot perform operation beacause quantities have different unit of measures";

    private Quantities() { }

    // Package private
    static boolean checkSameUM(final Quantity q1, final Quantity q2) {
        return q1.getUnitOfMeasure().equals(q2.getUnitOfMeasure());
    }

    /**
     * Returns a new {@link Result} with a {@link Quantity} that is the adding quantity added to the base quantity.
     * If the quantities' unit of measures do not match, the Result will contain a {@link ArithmeticException}.
     * This method does not modify the quantities passed by parameters, it creates a new one.
     * @param base base quantity.
     * @param adding adding quantity.
     * @return a new {@link Result} with a {@link Quantity} that is the adding quantity added to the base quantity.
     */
    public static Result<Quantity> add(final Quantity base, final Quantity adding) {
        return Quantity.of(BigDecimal.valueOf(base.getValue()).add(BigDecimal.valueOf(adding.getValue())).doubleValue(),
                           base.getUnitOfMeasure())
                       .require(q -> checkSameUM(q, adding), new ArithmeticException(NOT_SAME_UM_MESSAGE));
    }

    /**
     * Returns a new {@link Result} with a {@link Quantity} that is the removing quantity removed to the base quantity,
     * or an error with:
     * <ul>
     *     <li>{@link IllegalArgumentException} if the remaining quantity value will drop below zero after the operation.</li>
     *     <li>{@link ArithmeticException} if the quantities' unit of measures do not match.</li>
     * </ul>
     * This method does not modify the quantities passed by parameters, it creates a new one.
     * @param base base quantity.
     * @param removing removing quantity.
     * @return a new {@link Result} with a {@link Quantity} that is the removing quantity removed to the base quantity.
     */
    public static Result<Quantity> remove(final Quantity base, final Quantity removing) {
        return Quantity.of(BigDecimal.valueOf(base.getValue()).subtract(BigDecimal.valueOf(removing.getValue())).doubleValue(),
                           base.getUnitOfMeasure())
                       .require(q -> checkSameUM(q, removing), new ArithmeticException(NOT_SAME_UM_MESSAGE));
    }
}
