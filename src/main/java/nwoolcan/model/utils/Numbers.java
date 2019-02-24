package nwoolcan.model.utils;

import java.math.BigDecimal;

/**
 * Numbers utils for operation between Number objects.
 */
public final class Numbers {

    private Numbers() { }

    /**
     * Returns a new {@link Number} that is the first plus the second passed by parameters.
     * @param x first number.
     * @param y second number.
     * @return a new {@link Number} that is the first plus the second passed by parameters.
     */
    public static Number add(final Number x, final Number y) {
        return new BigDecimal(x.doubleValue()).add(new BigDecimal(y.doubleValue()));
    }

    /**
     * Returns a new {@link Number} that is the first minus the second passed by parameters.
     * @param x first number.
     * @param y second number.
     * @return a new {@link Number} that is the first minus the second passed by parameters.
     */
    public static Number subtract(final Number x, final Number y) {
        return new BigDecimal(x.doubleValue()).subtract(new BigDecimal(y.doubleValue()));
    }
}
