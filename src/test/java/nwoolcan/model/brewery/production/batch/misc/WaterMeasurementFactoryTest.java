package nwoolcan.model.brewery.production.batch.misc;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement.Element;
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
            new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value))));
        assertTrue(water.isPresent());
        assertFalse(water.getValue().getMeasurement(Element.BICARBONATE).isPresent());
        assertEquals(water.getValue().getMeasurement(Element.CALCIUM).get().getRegistrationValue(), value);


        Collection<Pair<Element, Parameter>> reg = Stream.<Pair<Element, Parameter>>builder().add(Pair.of(Element.MAGNESIUM,
            new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, value))).add(Pair.of(Element.CALCIUM,
            new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 1))).add(Pair.of(Element.SODIUM,
            new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 0))).build().collect(Collectors.toList());

        Result<WaterMeasurement> water2 = WaterMeasurementFactory.create(reg);
        assertTrue(water2.isPresent());
        assertFalse(water2.getValue().getMeasurement(Element.BICARBONATE).isPresent());
        assertEquals(water2.getValue().getMeasurement(Element.MAGNESIUM).get().getRegistrationValue(), value);
        assertEquals(water2.getValue().getMeasurement(Element.CALCIUM).get().getRegistrationValue(), 1);
        assertEquals(water2.getValue().getMeasurement(Element.SODIUM).get().getRegistrationValue(), 0);
    }
    /**
     * test successful build.
     */
    @Test
    public void testFailedBuild() {
        final int value = 15;

        Collection<Pair<Element, Parameter>> reg = Stream.<Pair<Element, Parameter>>builder().add(Pair.of(Element.CALCIUM,
            new ParameterImpl(ParameterTypeEnum.ABV, value))).build().collect(Collectors.toList());

        Result<WaterMeasurement> water = WaterMeasurementFactory.create(reg);
        assertTrue(water.isError());

        Result<WaterMeasurement> water1 =  WaterMeasurementFactory.create(new ArrayList<>());
        assertFalse(water1.isPresent());
    }
}
