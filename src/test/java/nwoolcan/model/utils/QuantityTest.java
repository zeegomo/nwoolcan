package nwoolcan.model.utils;

import org.junit.Assert;
import org.junit.Test;

import nwoolcan.utils.test.TestUtils;

/**
 * Test quantity class.
 */
public class QuantityTest {

    /**
     * Method that tests the constructor with no unit of measure.
     */
    @Test
    public void testQuantitywithNoUnitOfMeasure() {
        final int value = 20;
        final Quantity q = Quantity.of(value, null);
        Assert.assertEquals(value, q.getValue());
        Assert.assertEquals(null, q.getUnitOfMeasure());
    }

    /**
     * Method that tests equals and hashcode methods of Quantity.
     */
    @Test
    public void testQuantityEqualsAndHashcode() {
        final int value1 = 20;
        final int value2 = 20;
        final int value3 = 50;
        final UnitOfMeasure um = null;
        final Quantity q1 = Quantity.of(value1, um);
        final Quantity q2 = Quantity.of(value2, um);
        final Quantity q3 = Quantity.of(value3, um);

        TestUtils.assertEqualsWithMessage(q1, q2);
        TestUtils.assertNotEqualsWithMessage(q1, q3);
        Assert.assertEquals(q1.hashCode(), q2.hashCode());
    }
}