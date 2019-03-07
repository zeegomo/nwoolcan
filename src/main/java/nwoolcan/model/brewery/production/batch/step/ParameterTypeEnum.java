package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.UnitOfMeasure;

import java.util.Objects;

/**
 * Parameter type enum implementation.
 */
public enum ParameterTypeEnum implements ParameterType {
    /**
     * Temperature parameter type.
     */
    Temperature(UnitOfMeasure.Celsius),
    /**
     * Number of hops that can be added in some production steps.
     */
    AddedHops(UnitOfMeasure.Unit),
    /**
     * Alcohol by volume of the beer.
     */
    ABV(UnitOfMeasure.Percentage);

    private final UnitOfMeasure um;

    ParameterTypeEnum(final UnitOfMeasure um) {
        this.um = Objects.requireNonNull(um);
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
