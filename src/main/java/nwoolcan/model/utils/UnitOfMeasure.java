package nwoolcan.model.utils;

import nwoolcan.utils.StringUtils;

/**
 * Enum used to identify the various unit of measures.
 */
public enum UnitOfMeasure {
    /**
     * Grams.
     */
    GRAM("g") {
        @Override
        public boolean validateValue(final Number value) {
            return isPositive(value);
        }
    },
    /**
     * Milliliters.
     */
    LITER("L") {
        @Override
        public boolean validateValue(final Number value) {
            return isPositive(value);
        }
    },
    /**
     * Basic units.
     */
    UNIT("u") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value) && isPositive(value);
        }
    },
    /**
     * Percentage.
     */
    PERCENTAGE("%") {
        @Override
        public boolean validateValue(final Number value) {
            return value.doubleValue() >= 0 && value.doubleValue() <= 100;
        }
    },
    /**
     * Celsius degrees.
     */
    CELSIUS_DEGREE("C") {
        @Override
        public boolean validateValue(final Number value) {
            return value.doubleValue() >= TEMP_MIN;
        }
    },
    /**
     * Milligrams per liter.
     */
    MILLIGRAMS_PER_LITER("mg/L") {
        @Override
        public boolean validateValue(final Number value) {
            return isPositive(value);
        }
    },
    /**
     * Pure number.
     */
    UNITLESS {
        @Override
        public boolean validateValue(final Number value) {
            return isPositive(value);
        }
    },
    /**
     * Ebc special unit of measure.
     */
    EBC {
        @Override
        public boolean validateValue(final Number value) {
            return isPositive(value);
        }
    },
    /**
     * IBU special unit of measure.
     */
    IBU {
        @Override
        public boolean validateValue(final Number value) {
            return isPositive(value);
        }
    },
    /**
     * Bottle unit of measure for a 33 cl capacity.
     */
    BOTTLE_33_CL("bot 33cl") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value) && isPositive(value);
        }
    },
    /**
     * Bottle unit of measure for a 50 cl capacity.
     */
    BOTTLE_50_CL("bot 50cl") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value) && isPositive(value);
        }
    },
    /**
     * Bottle unit of measure for a 75 cl capacity.
     */
    BOTTLE_75_CL("bot 75cl") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value) && isPositive(value);
        }
    },
    /**
     * Magnum bottle unit of measure corresponding to 1.5 liters.
     */
    BOTTLE_MAGNUM("bot magnum") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value) && isPositive(value);
        }
    };

    private final String symbol;
    private static final Double TEMP_MIN = -273.15;

    UnitOfMeasure() {
        this("");
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
    /**
     * Checks that the value associated with this unit of measure is valid.
     * @param value the value to validate.
     * @return true if the value can be associated to this unit of measure, false otherwise.
     */
    public abstract boolean validateValue(Number value);

    private static boolean validateWhenInteger(final Number value) {
        return value.doubleValue() == value.intValue();
    }
    private static boolean isPositive(final Number value) {
        return value.doubleValue() >= 0;
    }

    @Override
    public String toString() {
        return StringUtils.underscoreSeparatedToHuman(super.toString());
    }
}
