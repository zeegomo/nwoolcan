package nwoolcan.model.utils;

import java.util.function.Predicate;

/**
 * Enum used to identify the various unit of measures.
 */
public enum UnitOfMeasure {
    /**
     * Grams.
     */
    GRAM("g"),
    /**
     * Milliliters.
     */
    MILLILITER("ml"),
    /**
     * Basic units.
     */
    UNIT("no", n -> n.doubleValue() == n.intValue()),
    /**
     * Percentage.
     */
    PERCENTAGE("%", n -> n.doubleValue() >= 0 && n.doubleValue() <= 100),
    /**
     * Celsius degrees.
     */
    CELSIUS_DEGREE("C"),
    /**
     * Milligrams per liter.
     */
    MILLIGRAMS_PER_LITER("mg/L"),
    /**
     * Pure number.
     */
    UNITLESS,
    /**
     * Ebc special unit of measure.
     */
    EBC,
    /**
     * IBU special unit of measure.
     */
    IBU,
    /**
     * Bottle unit of measure for a 33 cl capacity.
     */
    BOTTLE_33_CL("bot 33cl", n -> n.doubleValue() == n.intValue()),
    /**
     * Bottle unit of measure for a 50 cl capacity.
     */
    BOTTLE_50_CL("bot 50cl", n -> n.doubleValue() == n.intValue()),
    /**
     * Bottle unit of measure for a 75 cl capacity.
     */
    BOTTLE_75_CL("bot 75cl", n -> n.doubleValue() == n.intValue()),
    /**
     * Magnum bottle unit of measure corresponding to 1.5 liters.
     */
    BOTTLE_MAGNUM("bot magnum", n -> n.doubleValue() == n.intValue());

    private final String symbol;
    private final Predicate<Number> checker;

    UnitOfMeasure() {
        this("");
    }

    UnitOfMeasure(final String symbol) {
        this(symbol, n -> true);
    }

    UnitOfMeasure(final String symbol, final Predicate<Number> checker) {
        this.symbol = symbol;
        this.checker = checker;
    }

    /**
     * Returns the symbol of the unit of measure.
     * @return the symbol of the unit of measure.
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Checks that the value associated with this unit of measure is valid.
     * @param value the value to validate.
     * @return true if the value can be associated to this unit of measure, false otherwise.
     */
    public boolean validateValue(final Number value) {
        return this.checker.test(value);
    }
}
