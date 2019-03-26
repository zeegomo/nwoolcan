package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Record tests.
 */
public class RecordTest {

    private static final Double NUM = 1.0d;
    /**
     * Test getters.
     */
    @Test
    public void getters() {
        final Quantity quantity = Quantity.of(NUM, UnitOfMeasure.Kilogram);
        final Date date = new Date();
        final Record.Action action = Record.Action.ADDING;
        final Record record = new Record(quantity, date, action);
        final Record record1 = new Record(quantity, action);
        Assert.assertEquals(quantity, record.getQuantity());
        Assert.assertEquals(date, record.getDate());
        Assert.assertEquals(action, record.getAction());
        Assert.assertEquals(quantity, record1.getQuantity());
        Assert.assertEquals(action, record1.getAction());
    }

}
