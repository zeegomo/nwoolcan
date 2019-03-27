package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.production.batch.WaterMeasurement.Elements;
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
        WaterMeasurementBuilder builder = new WaterMeasurementBuilder()
            .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value), Elements.CALCIUM);
        Result<WaterMeasurement> water = builder.build();
        assertTrue(water.isPresent());
        assertFalse(water.getValue().getMeasurement(Elements.BICARBONATE).isPresent());
        assertEquals(water.getValue().getMeasurement(Elements.CALCIUM).get().getRegistrationValue(), value);

        Result<WaterMeasurement> water2 = builder.reset()
                                                 .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value), Elements.MAGNESIUM)
                                                 .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 1),  Elements.CALCIUM)
                                                 .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 0),  Elements.SODIUM)
                                                 .build();
        assertTrue(water2.isPresent());
        assertFalse(water2.getValue().getMeasurement(Elements.BICARBONATE).isPresent());
        assertEquals(water.getValue().getMeasurement(Elements.MAGNESIUM).get().getRegistrationValue(), value);
        assertEquals(water.getValue().getMeasurement(Elements.CALCIUM).get().getRegistrationValue(), 1);
        assertEquals(water.getValue().getMeasurement(Elements.SODIUM).get().getRegistrationValue(), 0);
    }
    /**
     * test successful build.
     */
    @Test
    public void testFailedBuild() {
        final int value = 15;
        WaterMeasurementBuilder builder = new WaterMeasurementBuilder()
            .addRegistration(new ParameterImpl(ParameterTypeEnum.ABV, value), Elements.CALCIUM);
        Result<WaterMeasurement> water = builder.build();
        assertTrue(water.isError());

        Result<WaterMeasurement> water1 =  builder.reset().build();
        assertTrue(water1.isPresent());
        assertFalse(water1.getValue().getMeasurement(Elements.CALCIUM).isPresent());

        Result<WaterMeasurement> water2 = builder.build();
        assertTrue(water2.isError());
    }
}
