package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
import nwoolcan.model.utils.Quantities;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Test class for StockImpl.
 */
public class StockImplTest {

    private Stock stock;
    private Stock stock1;
    private Date expDate;
    private Record record1 = new Record(Quantity.of(TEN, UOM), Record.Action.ADDING);
    private Record record2 = new Record(Quantity.of(ONE, UOM), Record.Action.REMOVING);
    private Record record3 = new Record(Quantity.of(ONE, UOM1), Record.Action.ADDING);
    private Record record4 = new Record(Quantity.of(TEN, UOM), Record.Action.REMOVING);
    private Record record5 = new Record(Quantity.of(TEN, UOM), Record.Action.ADDING);


    private static final Integer ONE = 1;
    private static final Integer TEN = 10;
    private static final String NAME = "DummyName";
    private static final String RECORD_WITH_DIFFERENT_UOM = "Can't add a record if UOMS are not the same";
    private static final String REMOVING_RECORD_WITH_QUANTITY_NOT_AVAILABLE = "Can't add a remove"
                                                       + "record if the quantity is not available";

    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.MILLILITER;
    private static final Article ARTICLE = ArticleManager.getInstance().createMiscArticle(NAME, UOM);

    /**
     * Initialize structures.
     */
    @Before
    public void init() {
        expDate = DateUtils.addDays(new Date(), -1); // YESTERDAY
        stock = new StockImpl(ARTICLE, expDate);
        stock1 = new StockImpl(ARTICLE, null);
        Results.ofChecked(() -> Thread.sleep(TEN));
        stock.addRecord(record1);
        stock.addRecord(record2);
        stock1.addRecord(record1);
        stock1.addRecord(record2);
    }
    /**
     * Test the getters.
     */
    @Test
    public void testGettersAndRecords() {
        Assert.assertEquals(ARTICLE, stock.getArticle());
        Assert.assertTrue(Quantities.remove(record1.getQuantity(),
                                            record2.getQuantity()).isPresent());
        Assert.assertEquals(Quantities.remove(record1.getQuantity(),
                                              record2.getQuantity()).getValue(),
                                              stock.getRemainingQuantity());
        Assert.assertEquals(record2.getQuantity(), stock.getUsedQuantity());
        Assert.assertTrue(stock.getExpirationDate().isPresent());
        Assert.assertEquals(DateUtils.round(expDate, Calendar.DATE), stock.getExpirationDate().get());
        Assert.assertEquals(StockState.EXPIRED, stock.getState());
        Assert.assertTrue(stock.getCreationDate().before(stock.getLastChangeDate()));
        Assert.assertTrue(stock.getRecords().contains(record1));
        Assert.assertTrue(stock.getRecords().contains(record2));
        Assert.assertFalse(stock1.getExpirationDate().isPresent());
        Assert.assertEquals(StockState.AVAILABLE, stock1.getState());
    }
    /**
     * Test that adding an incompatible record generates an Error {@link Result}.
     */
    @Test
    public void testWrongRecord() {
        final Result<Empty> r = stock.addRecord(record3);
        final Result<Empty> r1 = stock1.addRecord(record4);
        final Result<Empty> r2 = stock.addRecord(record5);
        Assert.assertTrue(RECORD_WITH_DIFFERENT_UOM, r.isError());
        Assert.assertTrue(REMOVING_RECORD_WITH_QUANTITY_NOT_AVAILABLE, r1.isError());
        Assert.assertTrue(r2.isPresent());
    }

}
