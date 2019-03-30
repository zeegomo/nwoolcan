package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurementBuilder;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement.Element;
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
            .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value), Element.CALCIUM);
        Result<WaterMeasurement> water = builder.build();
        assertTrue(water.isPresent());
        assertFalse(water.getValue().getMeasurement(Element.BICARBONATE).isPresent());
        assertEquals(water.getValue().getMeasurement(Element.CALCIUM).get().getRegistrationValue(), value);

        Result<WaterMeasurement> water2 = builder.reset()
                                                 .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value), Element.MAGNESIUM)
                                                 .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 1),  Element.CALCIUM)
                                                 .addRegistration(new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 0),  Element.SODIUM)
                                                 .build();
        assertTrue(water2.isPresent());
        assertFalse(water2.getValue().getMeasurement(Element.BICARBONATE).isPresent());
        assertEquals(water.getValue().getMeasurement(Element.MAGNESIUM).get().getRegistrationValue(), value);
        assertEquals(water.getValue().getMeasurement(Element.CALCIUM).get().getRegistrationValue(), 1);
        assertEquals(water.getValue().getMeasurement(Element.SODIUM).get().getRegistrationValue(), 0);
    }
    /**
     * test successful build.
     */
    @Test
    public void testFailedBuild() {
        final int value = 15;
        WaterMeasurementBuilder builder = new WaterMeasurementBuilder()
            .addRegistration(new ParameterImpl(ParameterTypeEnum.ABV, value), Element.CALCIUM);
        Result<WaterMeasurement> water = builder.build();
        assertTrue(water.isError());

        Result<WaterMeasurement> water1 =  builder.reset().build();
        assertTrue(water1.isPresent());
        assertFalse(water1.getValue().getMeasurement(Element.CALCIUM).isPresent());

        Result<WaterMeasurement> water2 = builder.build();
        assertTrue(water2.isError());
    }
}
