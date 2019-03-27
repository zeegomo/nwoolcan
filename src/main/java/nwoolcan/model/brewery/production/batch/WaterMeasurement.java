package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;

import java.util.Optional;

/**
 * WaterMeasurement.
 */
public interface WaterMeasurement {
        /**
         * Returns the amount of Ca+2 in mg/L.
         * @return a {@link Parameter} describing Ca+2 levels
         */
        Optional<Parameter> getCalciumIons();
        /**
         * Returns the amount of Mg+2 in mg/L.
         * @return a {@link Parameter} describing Mg+2 levels
         */
        Optional<Parameter> getMagnesiumIons();
        /**
         * Returns the amount of Na+1 in mg/L.
         * @return a {@link Parameter} describing Na+1 levels
         */
        Optional<Parameter> getSodiumIons();
        /**
         * Returns the amount of HCO3-1 in mg/L.
         * @return a {@link Parameter} describing HCO3-1 levels
         */
        Optional<Parameter> getBicarbonateIons();
        /**
         * Returns the amount of SO4-2 in mg/L.
         * @return a {@link Parameter} describing SO4-2 levels
         */
        Optional<Parameter> getSulfateIons();
        /**
         * Returns the amount of Cl-1 in mg/L.
         * @return a {@link Parameter} describing Cl-1 levels
         */
        Optional<Parameter> getChlorideIons();
}
