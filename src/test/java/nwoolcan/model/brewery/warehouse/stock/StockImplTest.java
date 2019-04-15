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

    private static final StockManager STOCK_MANAGER = StockManager.getInstance();
    private Stock stock;
    private Stock stock1;
    private Stock stock2;
    private Stock stock3;
    private Date expDate;
    private Record record1 = new Record(Quantity.of(TEN, UOM1).getValue(), Record.Action.ADDING);
    private Record record2 = new Record(Quantity.of(ONE, UOM1).getValue(), Record.Action.REMOVING);
    private Record record3 = new Record(Quantity.of(ONE, UOM1).getValue(), Record.Action.ADDING);
    private Record record4 = new Record(Quantity.of(TEN, UOM).getValue(), Record.Action.REMOVING);
    private Record record5 = new Record(Quantity.of(TEN, UOM).getValue(), Record.Action.ADDING);

    private static final int ONE = 1;
    private static final int TEN = 10;
    private static final String NAME = "DummyNameFirst";
    private static final String RECORD_WITH_DIFFERENT_UOM = "Can't add a record if UOMS are not the same";
    private static final String REMOVING_RECORD_WITH_QUANTITY_NOT_AVAILABLE = "Can't add a remove "
                                                       + "record if the quantity is not available";

    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.LITER;
    private static final Article ARTICLE = ArticleManager.getInstance().createMiscArticle(NAME, UOM);
    private static final Article ARTICLE1 = ArticleManager.getInstance().createMiscArticle(NAME, UOM1);

    /**
     * Initialize structures.
     */
    @Before
    public void init() {
        expDate = DateUtils.addDays(new Date(), -1); // YESTERDAY

        Result<Stock> stockResult = STOCK_MANAGER.createStock(ARTICLE, expDate);
        Assert.assertTrue(stockResult.isPresent());
        stock = stockResult.getValue();
        Result<Stock> stockResult1 = STOCK_MANAGER.createStock(ARTICLE, null);
        Assert.assertTrue(stockResult1.isPresent());
        stock1 = stockResult1.getValue();
        Results.ofChecked(() -> Thread.sleep(TEN));
        Result<Stock> stockResult2 = STOCK_MANAGER.createStock(ARTICLE1, expDate);
        Assert.assertTrue(stockResult2.isPresent());
        stock2 = stockResult2.getValue();
        Results.ofChecked(() -> Thread.sleep(TEN));
        Result<Stock> stockResult3 = STOCK_MANAGER.createStock(ARTICLE1, null);
        Assert.assertTrue(stockResult3.isPresent());
        stock3 = stockResult3.getValue();
    }
    /**
     * Test the getters.
     */
    @Test
    public void testGettersAndRecords() {
        stock2.addRecord(record1);
        stock2.addRecord(record2);
        stock3.addRecord(record1);
        stock3.addRecord(record2);
        Assert.assertEquals(ARTICLE, stock.getArticle());
        Assert.assertTrue(Quantities.remove(record1.getQuantity(),
                                            record2.getQuantity()).isPresent());
        Assert.assertEquals(Quantities.remove(record1.getQuantity(),
                                              record2.getQuantity()).getValue(),
                                              stock2.getRemainingQuantity());
        Assert.assertEquals(record2.getQuantity(), stock2.getUsedQuantity());
        Assert.assertTrue(stock2.getExpirationDate().isPresent());
        Assert.assertEquals(DateUtils.truncate(expDate, Calendar.DATE), stock2.getExpirationDate().get());
        Assert.assertEquals(StockState.EXPIRED, stock2.getState());
        Assert.assertTrue(stock2.getCreationDate().before(stock.getLastChangeDate()));
        Assert.assertTrue(stock2.getRecords().contains(record1));
        Assert.assertTrue(stock2.getRecords().contains(record2));
        Assert.assertFalse(stock3.getExpirationDate().isPresent());
        Assert.assertEquals(StockState.AVAILABLE, stock3.getState());
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
