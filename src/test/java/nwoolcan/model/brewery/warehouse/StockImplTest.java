package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleImpl;
import nwoolcan.model.utils.Quantities;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    private static final Integer ID = 1;
    private static final Integer ONE = 1;
    private static final Integer TEN = 10;
    private static final String NAME = "DummyName";
    private static final UnitOfMeasure UOM = UnitOfMeasure.Kilogram;
    private static final Article ARTICLE = new ArticleImpl(ID, NAME, UOM);

    /**
     * Initialize structures.
     */
    @Before
    public void init() {
        expDate = new Date();
        stock = new StockImpl(ID, ARTICLE, expDate);
        stock.addRecord(record1);
        stock.addRecord(record2);
        stock1 = new StockImpl(ID, ARTICLE);
        stock1.addRecord(record1);
        stock1.addRecord(record2);
    }
    /**
     * Test the getters.
     */
    @Test
    public void testGettersAndRecords() {
        Assert.assertEquals(ID, stock.getId());
        Assert.assertEquals(ARTICLE, stock.getArticle());
        Assert.assertTrue(Quantities.remove(record1.getQuantity(), record2.getQuantity()).isPresent());
        Assert.assertEquals(Quantities.remove(record1.getQuantity(), record2.getQuantity()).getValue(),
                            stock.getRemainingQuantity());
        Assert.assertEquals(record2.getQuantity(), stock.getUsedQuantity());
        Assert.assertTrue(stock.getExpirationDate().isPresent());
        Assert.assertEquals(expDate, stock.getExpirationDate().get());
        Assert.assertEquals(StockState.AVAILABLE, stock.getState());
        Assert.assertTrue(stock.getCreationDate().toInstant().isBefore(stock.getLastChangeDate().toInstant()));
        Assert.assertTrue(stock.getCreationDate().before(stock.getLastChangeDate()));
        Assert.assertTrue(stock.getRecords().contains(record1));
        Assert.assertTrue(stock.getRecords().contains(record2));
        Assert.assertFalse(stock1.getExpirationDate().isPresent());
    }

}
