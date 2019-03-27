package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.utils.Result;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link WaterMeasurementBuilder}.
 */
public class WaterMeasurementBuilderTest {
    /**
     * test successful build.
     */
    @Test
    public void testSuccessfulBuild() {
        final int value = 15;
        WaterMeasurementBuilder builder = new WaterMeasurementBuilder().addCalciumIonsRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value));
        Result<WaterMeasurement> water = builder.build();
        assertTrue(water.isPresent());
        assertFalse(water.getValue().getBicarbonateIons().isPresent());
        assertEquals(water.getValue().getCalciumIons().get().getRegistrationValue(), value);

        Result<WaterMeasurement> water2 = builder.reset()
                                                 .addMagnesiumIonsRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value))
                                                 .addCalciumIonsRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 1))
                                                 .addSodiumIonsRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 0))
                                                 .build();
        assertTrue(water2.isPresent());
        assertFalse(water2.getValue().getBicarbonateIons().isPresent());
        assertEquals(water.getValue().getMagnesiumIons().get().getRegistrationValue(), value);
        assertEquals(water.getValue().getCalciumIons().get().getRegistrationValue(), 1);
        assertEquals(water.getValue().getSodiumIons().get().getRegistrationValue(), 0);
    }
    /**
     * test successful build.
     */
    @Test
    public void testFailedBuild() {
        final int value = 15;
        WaterMeasurementBuilder builder = new WaterMeasurementBuilder().addCalciumIonsRegistration(new ParameterImpl(ParameterTypeEnum.ABV, value));
        Result<WaterMeasurement> water = builder.build();
        assertTrue(water.isError());

        Result<WaterMeasurement> water1 =  builder.reset().build();
        assertTrue(water1.isPresent());
        assertFalse(water1.getValue().getChlorideIons().isPresent());

        Result<WaterMeasurement> water2 = builder.build();
        assertTrue(water2.isError());
    }
}
