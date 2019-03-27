package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;

import java.util.Optional;

/**
 * WaterMeasurement.
 */
public interface WaterMeasurement {
        /**
         * Water elements measured.
         */
        enum Elements {
                /**
                 * Ca+2.
                 */
                CALCIUM,
                /**
                 * Mg+2.
                 */
                MAGNESIUM,
                /**
                 * Na+1.
                 */
                SODIUM,
                /**
                 * HCO3-1.
                 */
                BICARBONATE,
                /**
                 * SO4-2.
                 */
                SULFATE,
                /**
                 * Cl-1.
                 */
                CHLORIDE;
        }

        /**
         * Return the measurement of the specific element, if available.
         * @param e the element of which we want to know the measurement.
         * @return a {@link Parameter}.
         */
        Optional<Parameter> getMeasurement(Elements e);
}
