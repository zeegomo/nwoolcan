package nwoolcan.model.utils;

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
    MILLILITER("mL"),
    /**
     * Basic units.
     */
    UNIT,
    /**
     * Percentage.
     */
    PERCENTAGE("%"),
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
    BOTTLE_33_CL,
    /**
     * Bottle unit of measure for a 50 cl capacity.
     */
    BOTTLE_50_CL,
    /**
     * Bottle unit of measure for a 75 cl capacity.
     */
    BOTTLE_75_CL,
    /**
     * Magnum bottle unit of measure corresponding to 1.5 liters.
     */
    BOTTLE_MAGNUM;

    private final String symbol;

    UnitOfMeasure() {
        this.symbol = "";
    }

    UnitOfMeasure(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol of the unit of measure.
     * @return the symbol of the unit of measure.
     */
    public String getSymbol() {
        return this.symbol;
    }
}
