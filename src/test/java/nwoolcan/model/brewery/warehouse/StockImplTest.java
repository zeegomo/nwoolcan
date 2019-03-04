package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleImpl;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
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
    private static final Integer ID = 1;
    private static final Integer ONE = 1;
    private static final Integer TEN = 1;
    private static final String NAME = "DummyName";
    private static final Article ARTICLE = new ArticleImpl(ID, NAME);
    private static final UnitOfMeasure UOM = UnitOfMeasure.Kilogram;

    /**
     * Initialize structures.
     */
    @Before
    public void init() {
        expDate = new Date();
        stock = new StockImpl(ID, ARTICLE, expDate);
        stock.addRecord(new Record(Quantity.of(TEN, UOM), Record.Action.ADDING));
        stock.addRecord(new Record(Quantity.of(ONE, UOM), Record.Action.REMOVING));
        stock1 = new StockImpl(ID, ARTICLE);
        stock1.addRecord(new Record(Quantity.of(TEN, UOM), Record.Action.ADDING));
        stock1.addRecord(new Record(Quantity.of(ONE, UOM), Record.Action.REMOVING));

    }

    /**
     * Test the getters.
     */
    @Test
    public void testGetters() {

    }

}
