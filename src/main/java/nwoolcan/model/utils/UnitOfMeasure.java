package nwoolcan.model.utils;

/**
 * Enum used to identify the various unit of measures.
 */
public enum UnitOfMeasure {
    /**
     * Unit of measures that can be used as quantity and parameter.
     */
    Kilogram, Liter, Pound,
    /**
     * Unit of measures that cannot be used as quantity.
     */
    Celcius(false, true);

    private boolean canBeQuantity;
    private boolean canBeParameter;

    UnitOfMeasure() {
        this(true, true);
    }

    UnitOfMeasure(final boolean canBeQuantity, final boolean canBeParameter) {
        this.canBeQuantity = canBeQuantity;
        this.canBeParameter = canBeParameter;
    }

    /**
     * Returns true if the unit of measure can be used as a quantity, false instead.
     * @return true if the unit of measure can be used as a quantity, false instead.
     */
    public boolean canBeQuantity() {
        return this.canBeQuantity;
    }

    /**
     * Returns true if the unit of measure can be used as a parameter, false instead.
     * @return true if the unit of measure can be used as a parameter, false instead.
     */
    public boolean canBeParameter() {
        return this.canBeParameter;
    }
}
