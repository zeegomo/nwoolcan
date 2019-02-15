package nwoolcan.model.utils;

/**
 * Quantity interface for handling value and unit of measure.
 */
public interface Quantity {
    /**
     * Returns the quantity value.
     * @return quantity value.
     */
    Number getValue();

    /**
     * Returns the quantity unit of measure.
     * @return quantity unit of measure.
     */
    UnitOfMeasure getUnitOfMeasure();
}
