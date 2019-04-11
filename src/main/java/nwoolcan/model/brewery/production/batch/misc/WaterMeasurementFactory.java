package nwoolcan.model.brewery.production.batch.misc;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.utils.Result;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Water measurement implementation.
 */
public final class WaterMeasurementFactory {

    private static final String INVALID_PARAMETER = "One or more parameter are not a valid water measurement type";
    private static final String INVALID_SIZE = "No measurement provided";


    private WaterMeasurementFactory() { }
    /**
     * Add water measurement.
     * @param p the parameter holding the measure.
     * @param e the element measured.
     * @return this.
     */
    public static Result<WaterMeasurement> create(final Collection<Pair<WaterMeasurement.Element, Parameter>> registration) {
        return Result.of(registration)
                     .require(reg -> reg.size() > 1, new IllegalArgumentException(INVALID_SIZE))
                     .require(reg -> reg.stream().allMatch(p -> p.getRight().getType().equals(ParameterTypeEnum.WATER_MEASUREMENT)),
                         new IllegalArgumentException(INVALID_PARAMETER))
                     .map(reg -> reg.stream()
                                      .collect(Collectors.toMap(Pair::getLeft, Pair::getRight)))
                     .map(WaterMeasurementImpl::new);
    }

    private static final class WaterMeasurementImpl implements WaterMeasurement {

        private final Map<Element, Parameter> parameter;

        private WaterMeasurementImpl(final Map<Element, Parameter> parameters) {
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
