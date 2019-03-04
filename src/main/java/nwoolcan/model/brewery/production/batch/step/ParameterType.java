package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.UnitOfMeasure;

/**
 * Interface representing a parameter type that can be registered in a production step.
 */
public interface ParameterType {

    /**
     * Returns the name of the parameter type.
     * @return the name of the parameter type.
     */
    String getName();

    /**
     * Returns the unit of measure of the parameter type.
     * @return the unit of measure of the parameter type.
     */
    UnitOfMeasure getUnitOfMeasure();
}
