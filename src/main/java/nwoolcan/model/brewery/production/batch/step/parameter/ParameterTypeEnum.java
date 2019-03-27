package nwoolcan.model.brewery.production.batch.step.parameter;

import nwoolcan.model.utils.UnitOfMeasure;

/**
 * Parameter type enum implementation.
 */
public enum ParameterTypeEnum implements ParameterType {
    /**
     * Temperature parameter type.
     */
    TEMPERATURE(UnitOfMeasure.Celsius),
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
    GRAVITY(UnitOfMeasure.Gravity),
    /**
     * Water measurements for the beer.
     */
    WATER_MEASUREMENT(UnitOfMeasure.MilligramsPerLiter);

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
