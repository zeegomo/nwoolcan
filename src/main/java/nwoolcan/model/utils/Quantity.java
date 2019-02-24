package nwoolcan.model.utils;

import java.util.Objects;

/**
 * Quantity class for handling value and unit of measure.
 */
public final class Quantity {

    private final Number value;
    private final UnitOfMeasure unitOfMeasure;

    //Private constructor to use as a static factory with method Quantity.of(...).
    private Quantity(final Number value, final UnitOfMeasure um) {
        this.value = value;
        this.unitOfMeasure = um;
    }

    /**
     * Returns the quantity value.
     * @return quantity value.
     */
    public Number getValue() {
        return this.value;
    }

    /**
     * Returns the quantity unit of measure.
     * @return quantity unit of measure.
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    /**
     * Returns a new {@link Quantity} with the specified value and unit of measure.
     * @param value new quantity value.
     * @param unitOfMeasure new quantity unit of measure.
     * @return a new {@link Quantity} with the specified value and unit of measure.
     */
    public static Quantity of(final Number value, final UnitOfMeasure unitOfMeasure) {
        return new Quantity(value, unitOfMeasure);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        return Objects.equals(value, quantity.value)
            && unitOfMeasure == quantity.unitOfMeasure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unitOfMeasure);
    }

    @Override
    public String toString() {
        return "[Quantity]{"
            + "value=" + value
            + ", unitOfMeasure=" + unitOfMeasure
            + '}';
    }
}
