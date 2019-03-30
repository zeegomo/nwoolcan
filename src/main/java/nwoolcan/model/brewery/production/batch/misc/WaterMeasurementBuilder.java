package nwoolcan.model.brewery.production.batch.misc;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.utils.Result;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Water measurement implementation.
 */
public class WaterMeasurementBuilder {
    private static final String INVALID_PARAMETER = "One or more parameter are not a valid water measurement type";
    private static final String BUILDER_BUILT = "This builder has already built";
    private final EnumMap<WaterMeasurement.Element, Parameter> parameter = new EnumMap<>(WaterMeasurement.Element.class);
    private boolean built;

    /**
     * Add water measurement.
     * @param p the parameter holding the measure.
     * @param e the element measured.
     * @return this.
     */
    public WaterMeasurementBuilder addRegistration(final Parameter p, final WaterMeasurement.Element e) {
        this.parameter.put(e, p);
        return this;
    }
    /**
     * Return whether this builder can build another instance or have to reset.
     * @return true if this builder can build, false otherwise.
     */
    public boolean canBuild() {
        return !this.built;
    }
    /**
     * Build the water measurement object.
     * @return a {@link Result} holding a {@link WaterMeasurement} if everything went well.
     */
    public Result<WaterMeasurement> build() {
        return Result.of(this.parameter)
                     .require(params -> params.values().stream().allMatch(p -> p.getType().equals(ParameterTypeEnum.WATER_MEASUREMENT)),
                         new IllegalArgumentException(INVALID_PARAMETER))
                     .require(this::canBuild, new IllegalStateException(BUILDER_BUILT))
                     .peek(params -> this.built = true)
                     .map(WaterMeasurementImpl::new);
    }
    /**
     * Reset the builder.
     * @return this.
     */
    public WaterMeasurementBuilder reset() {
        this.built = false;
        this.parameter.clear();
        return this;
    }

    private static final class WaterMeasurementImpl implements WaterMeasurement {
        private final EnumMap<Element, Parameter> parameter;

        private WaterMeasurementImpl(final EnumMap<Element, Parameter> parameters) {
            this.parameter = parameters;
        }

        @Override
        public Optional<Parameter> getMeasurement(final Element e) {
            return Optional.ofNullable(this.parameter.get(e));
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WaterMeasurementImpl that = (WaterMeasurementImpl) o;
            return Objects.equals(parameter, that.parameter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parameter);
        }

        @Override
        public String toString() {
            return "[WaterMeasurementImpl]{"
                + " calcium = " + this.parameter.get(Element.CALCIUM)
                + ", sodium = " + this.parameter.get(Element.SODIUM)
                + ", magnesium = " + this.parameter.get(Element.SODIUM)
                + ", bicarbonate = " + this.parameter.get(Element.BICARBONATE)
                + ", chloride = " + this.parameter.get(Element.CHLORIDE)
                + '}';
        }
    }
}
