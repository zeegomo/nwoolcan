package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.utils.Quantity;

import java.util.Optional;

/**
 * WaterMeasurement.
 */
public interface WaterMeasurement {
        /**
         * Returns the amount of Ca+2.
         * @return a {@link Quantity} describing Ca+2 levels
         */
        Optional<Quantity> getCalciumIons();
        /**
         * Returns the amount of Mg+2.
         * @return a {@link Quantity} describing Mg+2 levels
         */
        Optional<Quantity> getMagnesiumIons();
        /**
         * Returns the amount of Na+1.
         * @return a {@link Quantity} describing Na+1 levels
         */
        Optional<Quantity> getSodiumIons();
        /**
         * Returns the amount of HCO3-1.
         * @return a {@link Quantity} describing HCO3-1 levels
         */
        Optional<Quantity> getBicarbonateIons();
        /**
         * Returns the amount of SO4-2.
         * @return a {@link Quantity} describing SO4-2 levels
         */
        Optional<Quantity> getSulfateIons();
        /**
         * Returns the amount of Cl-1.
         * @return a {@link Quantity} describing Cl-1 levels
         */
        Optional<Quantity> getChlorideIons();
}
