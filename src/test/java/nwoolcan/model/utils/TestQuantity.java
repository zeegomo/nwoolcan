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
    public void testQuantitywithNoUnitOfMeasure() {
        final int value = 20;
        final Quantity q = Quantity.of(value, null);
        Assert.assertEquals(value, q.getValue());
        Assert.assertEquals(null, q.getUnitOfMeasure());
    }
}
