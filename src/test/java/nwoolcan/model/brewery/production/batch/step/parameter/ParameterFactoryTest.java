package nwoolcan.model.brewery.production.batch.step.parameter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for parameters.
 */
public class ParameterFactoryTest {

    /**
     * Test creating parameters.
     */
    @Test
    public void testCreations() {
        final double d1 = 1.5;
        final double d2 = -1;
        final double d3 = 34.7;

        Assert.assertTrue(ParameterFactory.create(ParameterTypeEnum.TEMPERATURE, d1).isPresent());
        Assert.assertTrue(ParameterFactory.create(ParameterTypeEnum.TEMPERATURE, d2).isPresent());
        Assert.assertTrue(ParameterFactory.create(ParameterTypeEnum.TEMPERATURE, d3).isPresent());

        Assert.assertTrue(ParameterFactory.create(ParameterTypeEnum.ABV, d1).isPresent());
        Assert.assertTrue(ParameterFactory.create(ParameterTypeEnum.ABV, d2).isError());
    }
}
