package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleImpl;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Test;

import org.junit.Assert;

import java.util.Date;

/**
 * Test for Warehouse Implementation.
 */
public class WarehouseImplTest {

    private static final Integer ONE = 1;
    private static final Integer TEN = 10;
    private static final String NAME = "DummyName";
    private static final UnitOfMeasure UOM = UnitOfMeasure.Gram;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.Milliliter;
    private final Warehouse warehouse = new WarehouseImpl();
    private final Article article = new ArticleImpl(ONE, NAME, UOM);
    private final Quantity quantity = Quantity.of(ONE, UOM);
    private final Quantity quantity1 = Quantity.of(ONE, UOM1);
    private final Record record = new Record(quantity, Record.Action.ADDING);
    private final Record record1 = new Record(quantity, new Date(), Record.Action.ADDING);
    private final Record record2 = new Record(quantity1, Record.Action.ADDING);

    /**
     * Test the adders.
     */
    @Test
    public void testAdders() {
        Assert.assertTrue(warehouse.addArticle(article).isPresent());
        Assert.assertTrue(warehouse.addRecord(article, record).isPresent());
        Assert.assertTrue(warehouse.addRecord(article, record1).isPresent());
        Assert.assertTrue(warehouse.addRecord(article, record2).isError());
    }
}
