package nwoolcan.model.utils;

import nwoolcan.utils.Result;

import java.util.Objects;

/**
 * Quantity class for handling value and unit of measure.
 */
public final class Quantity implements Comparable<Quantity> {

    private final int value;
    private final UnitOfMeasure unitOfMeasure;

    //Private constructor to use as a static factory with method Quantity.of(...).
    private Quantity(final int value, final UnitOfMeasure um) {
        this.value = value;
        this.unitOfMeasure = um;
    }

    /**
     * Returns the quantity value.
     * @return quantity value.
     */
    public int getValue() {
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
     * @throws IllegalArgumentException if the value is negative or if the unit of measure cannot be a quantity.
     */
    public static Quantity of(final int value, final UnitOfMeasure unitOfMeasure) {
        final Result<Quantity> res = QuantityChecker.check(new Quantity(value, unitOfMeasure));
        if (res.isError()) {
            throw new IllegalArgumentException(res.getError());
        }
        return res.getValue();
    }

    /**
     * Compares the value of two {@link Quantity}. It returns:
     * <ul>
     *     <li>0 when they are equals.</li>
     *     <li>An integer smaller than 0 when this is less than other.</li>
     *     <li>An integer greater than 0 when this is greater than other.</li>
     * </ul>
     * @param other the {@link Quantity} which has to be compared with this.
     * @return an integer denoting the result of the comparison.
     */
    @Override
    public int compareTo(final Quantity other) {
        if (this.equals(other)) {
            return 0;
        }
        return Integer.compare(getValue(), other.getValue());
    }

    /**
     * Compares this {@link Quantity} with another one, checking the {@link UnitOfMeasure} and calling the method compareTo.
     * @param other the {@link Quantity} which has to be compared with this.
     * @return a {@link Result} containing the return value of the method compareTo.
     */
    public Result<Integer> checkedCompareTo(final Quantity other) {
        return Result.of(compareTo(other)).require(() -> Quantities.checkSameUM(this, other));
    }

    /**
     * Compares this with another {@link Quantity} and returns true when the value of this is less
     * than the other one.
     * @param other to be compared with this.
     * @return a boolean denoting whether the value of this is less than the other one.
     */
    public boolean lessThan(final Quantity other) {
        return compareTo(other) < 0;
    }

    /**
     * Compares this {@link Quantity} with another one, checking the {@link UnitOfMeasure} and calling the method lessThan.
     * @param other the {@link Quantity} which has to be compared with this.
     * @return a {@link Result} containing the return value of the method lessThan.
     */
    public Result<Boolean> checkedLessThan(final Quantity other) {
        return Result.of(lessThan(other)).require(() -> Quantities.checkSameUM(this, other));
    }

    /**
     * Compares this with another {@link Quantity} and returns true when the value of this is more
     * than the other one.
     * @param other to be compared with this.
     * @return a boolean denoting whether the value of this is more than the other one.
     */
    public boolean moreThan(final Quantity other) {
        return compareTo(other) > 0;
    }

    /**
     * Compares this {@link Quantity} with another one, checking the {@link UnitOfMeasure} and calling the method moreThan.
     * @param other the {@link Quantity} which has to be compared with this.
     * @return a {@link Result} containing the return value of the method moreThan.
     */
    public Result<Boolean> checkedMoreThan(final Quantity other) {
        return Result.of(moreThan(other)).require(() -> Quantities.checkSameUM(this, other));
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
        return this.value == quantity.value
            && this.unitOfMeasure == quantity.unitOfMeasure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.unitOfMeasure);
    }

    /**
     * Returns a string representation of the quantity (VALUE UNIT_OF_MEASURE_SYMBOL).
     * @return  a string representation of the quantity.
     */
    @Override
    public String toString() {
        return this.value + " " + this.unitOfMeasure.getSymbol();
    }
}
