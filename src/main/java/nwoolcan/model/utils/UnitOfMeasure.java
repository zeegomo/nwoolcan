package nwoolcan.model.utils;

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
            return true;
        }
    },
    /**
     * Milliliters.
     */
    LITER("L") {
        @Override
        public boolean validateValue(final Number value) {
            return true;
        }
    },
    /**
     * Basic units.
     */
    UNIT("no") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value);
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
            return true;
        }
    },
    /**
     * Milligrams per liter.
     */
    MILLIGRAMS_PER_LITER("mg/L") {
        @Override
        public boolean validateValue(final Number value) {
            return true;
        }
    },
    /**
     * Pure number.
     */
    UNITLESS {
        @Override
        public boolean validateValue(final Number value) {
            return true;
        }
    },
    /**
     * Ebc special unit of measure.
     */
    EBC {
        @Override
        public boolean validateValue(final Number value) {
            return true;
        }
    },
    /**
     * IBU special unit of measure.
     */
    IBU {
        @Override
        public boolean validateValue(final Number value) {
            return true;
        }
    },
    /**
     * Bottle unit of measure for a 33 cl capacity.
     */
    BOTTLE_33_CL("bot 33cl") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value);
        }
    },
    /**
     * Bottle unit of measure for a 50 cl capacity.
     */
    BOTTLE_50_CL("bot 50cl") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value);
        }
    },
    /**
     * Bottle unit of measure for a 75 cl capacity.
     */
    BOTTLE_75_CL("bot 75cl") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value);
        }
    },
    /**
     * Magnum bottle unit of measure corresponding to 1.5 liters.
     */
    BOTTLE_MAGNUM("bot magnum") {
        @Override
        public boolean validateValue(final Number value) {
            return validateWhenInteger(value);
        }
    };

    private final String symbol;

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
}
