package nwoolcan.model.brewery.production.batch.step.parameter;

import nwoolcan.model.utils.UnitOfMeasure;

/**
 * Parameter type enum implementation.
 */
public enum ParameterTypeEnum implements ParameterType {
    /**
     * Temperature parameter type.
     */
    Temperature(UnitOfMeasure.Celsius),
    /**
     * Alcohol by volume of the beer.
     */
    ABV(UnitOfMeasure.Percentage);

    private final UnitOfMeasure um;

    ParameterTypeEnum(final UnitOfMeasure um) {
        this.um = um;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public UnitOfMeasure getUnitOfMeasure() {
        return this.um;
    }
}
