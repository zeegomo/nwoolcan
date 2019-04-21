package nwoolcan.model.brewery.batch.step.parameter;

import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.StringUtils;

/**
 * Parameter type enum implementation.
 */
public enum ParameterTypeEnum implements ParameterType {
    /**
     * Temperature parameter type.
     */
    TEMPERATURE(UnitOfMeasure.CELSIUS_DEGREE),
    /**
     * Alcohol by volume of the beer.
     */
    ABV(UnitOfMeasure.PERCENTAGE),
    /**
     * Color of the beer.
     */
    EBC(UnitOfMeasure.EBC),
    /**
     * Bitterness of the beer.
     */
    IBU(UnitOfMeasure.IBU),
    /**
     * Specific gravity of the beer.
     */
    GRAVITY(UnitOfMeasure.UNITLESS),
    /**
     * Water measurements for the beer.
     */
    WATER_MEASUREMENT(UnitOfMeasure.MILLIGRAMS_PER_LITER);

    private final UnitOfMeasure um;

    ParameterTypeEnum(final UnitOfMeasure um) {
        this.um = um;
    }

    @Override
    public String getName() {
        return StringUtils.underscoreSeparatedToHuman(this.name());
    }

    @Override
    public UnitOfMeasure getUnitOfMeasure() {
        return this.um;
    }

    @Override
    public String toString() {
        return StringUtils.underscoreSeparatedToHuman(this.name());
    }
}
