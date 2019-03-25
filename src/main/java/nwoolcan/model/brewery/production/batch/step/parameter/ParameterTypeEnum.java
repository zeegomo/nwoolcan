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
     * Hops that can be added in some production steps.
     */
    AddedHops(UnitOfMeasure.Kilogram),
    /**
     * Alcohol by volume of the beer.
     */
    ABV(UnitOfMeasure.Percentage),
    /**
     * Color of the beer.
     */
    EBC(UnitOfMeasure.Ebc),
    /**
     * Bitterness of the beer.
     */
    IBU(UnitOfMeasure.Ibu),
    /**
     * Specific gravity of the beer.
     */
    GRAVITY(UnitOfMeasure.Gravity);


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
