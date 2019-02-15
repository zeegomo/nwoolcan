package nwoolcan.model.utils;

/**
 * Basic implementation of Quantity interface.
 */
public class QuantityImpl implements Quantity {

    private Number value;
    private final UnitOfMeasure unitOfMeasure;

    /**
     * @param value quantity value.
     * @param unitOfMeasure quantity unit of measure.
     */
    public QuantityImpl(final Number value, final UnitOfMeasure unitOfMeasure) {
        this.value = value;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public final Number getValue() {
        return this.value;
    }

    @Override
    public final UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }
}
