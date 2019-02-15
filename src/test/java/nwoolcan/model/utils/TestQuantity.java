package nwoolcan.model.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test quantity class.
 */
public class TestQuantity {

    /**
     * Method that tests the constructor with no unit of measure.
     */
    @Test
    public void testConstructorWithNullUM() {
        final int value = 20;
        Quantity q = new QuantityImpl(value, null);
        Assert.assertEquals(value, q.getValue());
        Assert.assertEquals(null, q.getUnitOfMeasure());
    }
}
