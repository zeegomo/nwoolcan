package nwoolcan.model.brewery.batch.misc;

import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.batch.step.parameter.ParameterFactory;
import nwoolcan.model.brewery.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.utils.Result;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link WaterMeasurementFactory}.
 */
public class WaterMeasurementFactoryTest {
    /**
     * test successful build.
     */
    @Test
    public void testSuccessfulBuild() {
        final int value = 15;
        Result<WaterMeasurement> water = WaterMeasurementFactory.create(Arrays.asList(Pair.of(WaterMeasurement.Element.CALCIUM,
            ParameterFactory.create(ParameterTypeEnum.WATER_MEASUREMENT, value).getValue())));
        assertTrue(water.isPresent());
        assertFalse(water.getValue().getMeasurement(WaterMeasurement.Element.BICARBONATE).isPresent());
        assertEquals(water.getValue().getMeasurement(WaterMeasurement.Element.CALCIUM).get().getRegistrationValue(), value);


        Collection<Pair<WaterMeasurement.Element, Parameter>> reg = Stream.<Pair<WaterMeasurement.Element, Parameter>>builder().add(Pair.of(WaterMeasurement.Element.MAGNESIUM,
            ParameterFactory.create(ParameterTypeEnum.WATER_MEASUREMENT, value).getValue())).add(Pair.of(WaterMeasurement.Element.CALCIUM,
            ParameterFactory.create(ParameterTypeEnum.WATER_MEASUREMENT, 1).getValue())).add(Pair.of(WaterMeasurement.Element.SODIUM,
            ParameterFactory.create(ParameterTypeEnum.WATER_MEASUREMENT, 0).getValue())).build().collect(Collectors.toList());

        Result<WaterMeasurement> water2 = WaterMeasurementFactory.create(reg);
        assertTrue(water2.isPresent());
        assertFalse(water2.getValue().getMeasurement(WaterMeasurement.Element.BICARBONATE).isPresent());
        assertEquals(water2.getValue().getMeasurement(WaterMeasurement.Element.MAGNESIUM).get().getRegistrationValue(), value);
        assertEquals(water2.getValue().getMeasurement(WaterMeasurement.Element.CALCIUM).get().getRegistrationValue(), 1);
        assertEquals(water2.getValue().getMeasurement(WaterMeasurement.Element.SODIUM).get().getRegistrationValue(), 0);
    }
    /**
     * test successful build.
     */
    @Test
    public void testFailedBuild() {
        final int value = 15;

        Collection<Pair<WaterMeasurement.Element, Parameter>> reg = Stream.<Pair<WaterMeasurement.Element, Parameter>>builder().add(Pair.of(WaterMeasurement.Element.CALCIUM,
            ParameterFactory.create(ParameterTypeEnum.ABV, value).getValue())).build().collect(Collectors.toList());

        Result<WaterMeasurement> water = WaterMeasurementFactory.create(reg);
        assertTrue(water.isError());

        Result<WaterMeasurement> water1 =  WaterMeasurementFactory.create(new ArrayList<>());
        assertFalse(water1.isPresent());
    }
}
