package nwoolcan.model.brewery.production.batch;

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
    private enum Ions {
        CALCIUM, MAGNESIUM, SODIUM, BICARBONATE, SULFATE, CHLORIDE
    }

    private final EnumMap<Ions, Parameter> parameter = new EnumMap<>(Ions.class);
    private boolean built;

    /**
     * Add Calcium measurement.
     * @param p the parameter holding the measure.
     * @return this.
     */
    public WaterMeasurementBuilder addCalciumIonsRegistration(final Parameter p) {
        this.parameter.put(Ions.CALCIUM, p);
        return this;
    }
    /**
     * Add magnesium measurement.
     * @param p the parameter holding the measure.
     * @return this.
     */
    public WaterMeasurementBuilder addMagnesiumIonsRegistration(final Parameter p) {
        this.parameter.put(Ions.MAGNESIUM, p);
        return this;
    }
    /**
     * Add sodium measurement.
     * @param p the parameter holding the measure.
     * @return this.
     */
    public WaterMeasurementBuilder addSodiumIonsRegistration(final Parameter p) {
        this.parameter.put(Ions.SODIUM, p);
        return this;
    }
    /**
     * Add bicarbonate measurement.
     * @param p the parameter holding the measure.
     * @return this.
     */
    public WaterMeasurementBuilder addBicarbonateRegistration(final Parameter p) {
        this.parameter.put(Ions.BICARBONATE, p);
        return this;
    }
    /**
     * Add sulfate measurement.
     * @param p the parameter holding the measure.
     * @return this.
     */
    public WaterMeasurementBuilder addSulfateIonsRegistration(final Parameter p) {
        this.parameter.put(Ions.SULFATE, p);
        return this;
    }

    /**
     * Add chloride measurement.
     * @param p the parameter holding the measure.
     * @return this.
     */
    public WaterMeasurementBuilder addChlorideIonsRegistration(final Parameter p) {
        this.parameter.put(Ions.CHLORIDE, p);
        return this;
    }
    /**
     * Build the water measurement object.
     * @return a {@link Result} holding a {@link WaterMeasurement} if everything went well.
     */
    public Result<WaterMeasurement> build() {
        return Result.of(this.parameter)
                     .require(params -> params.values().stream().allMatch(p -> p.getType().equals(ParameterTypeEnum.WATER_MEASUREMENT)),
                         new IllegalArgumentException(INVALID_PARAMETER))
                     .require(() -> !this.built, new IllegalStateException(BUILDER_BUILT))
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
        private final EnumMap<Ions, Parameter> parameter;

        private WaterMeasurementImpl(final EnumMap<Ions, Parameter> parameters) {
            this.parameter = parameters;
        }

        @Override
        public Optional<Parameter> getCalciumIons() {
            return Optional.ofNullable(parameter.get(Ions.CALCIUM));
        }

        @Override
        public Optional<Parameter> getMagnesiumIons() {
            return Optional.ofNullable(parameter.get(Ions.MAGNESIUM));
        }

        @Override
        public Optional<Parameter> getSodiumIons() {
            return Optional.ofNullable(parameter.get(Ions.SODIUM));
        }

        @Override
        public Optional<Parameter> getBicarbonateIons() {
            return Optional.ofNullable(parameter.get(Ions.BICARBONATE));
        }

        @Override
        public Optional<Parameter> getSulfateIons() {
            return Optional.ofNullable(parameter.get(Ions.SULFATE));
        }

        @Override
        public Optional<Parameter> getChlorideIons() {
            return Optional.ofNullable(parameter.get(Ions.CHLORIDE));
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
                + " calcium = " + this.parameter.get(Ions.CALCIUM)
                + ", sodium = " + this.parameter.get(Ions.SODIUM)
                + ", magnesium = " + this.parameter.get(Ions.SODIUM)
                + ", bicarbonate = " + this.parameter.get(Ions.BICARBONATE)
                + ", chloride = " + this.parameter.get(Ions.CHLORIDE)
                + '}';
        }
    }
}
