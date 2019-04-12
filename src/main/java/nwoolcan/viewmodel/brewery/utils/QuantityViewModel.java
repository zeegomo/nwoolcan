package nwoolcan.viewmodel.brewery.utils;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * View model class representing a quantity.
 */
public final class QuantityViewModel {

    private final double value;
    private final UnitOfMeasure unitOfMeasure;

    /**
     * Decorator constructor with quantity.
     * @param quantity quantity to get data from.
     */
    public QuantityViewModel(final Quantity quantity) {
        this.value = quantity.getValue();
        this.unitOfMeasure = quantity.getUnitOfMeasure();
    }

    /**
     * Returns the quantity value.
     * @return the quantity value.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Returns the quantity unit of measure.
     * @return the quantity unit of measure.
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    @Override
    public String toString() {
        return String.format("%1$.2f %2$s", this.value, this.unitOfMeasure.getSymbol());
    }
}
