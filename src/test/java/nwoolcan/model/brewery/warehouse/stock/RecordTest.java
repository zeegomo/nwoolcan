package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Record tests.
 */
public class RecordTest {

    private static final int NUM = 1;
    /**
     * Test getters.
     */
    @Test
    public void getters() {
        final Quantity quantity = Quantity.of(NUM, UnitOfMeasure.Gram);
        final Date date = new Date();
        final Record.Action action = Record.Action.ADDING;
        final Record record = new Record(quantity, date, action);
        final Record record1 = new Record(quantity, action);
        Assert.assertEquals(quantity, record.getQuantity());
        Assert.assertEquals(DateUtils.round(date, Calendar.SECOND), record.getDate());
        Assert.assertEquals(action, record.getAction());
        Assert.assertEquals(quantity, record1.getQuantity());
        Assert.assertEquals(action, record1.getAction());
    }

}
