package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test the Stock manager.
 */
public class StockManagerTest {

    private final StockManager stockInstance = StockManager.getInstance();
    private final ArticleManager articInstance = ArticleManager.getInstance();
    private static final String GIAMPIERO = "Giampiero Dummy Name";
    private static final UnitOfMeasure UOM = UnitOfMeasure.UNIT;
    private final Article giampiArtic = articInstance.createMiscArticle(GIAMPIERO, UOM);
    private static final Integer WRONG_ID = -1;

    /**
     * Test getInstance returns exactly the same instance.
     */
    @Test
    public void getInstance() {
        Assert.assertSame(StockManager.getInstance(), stockInstance);
    }
    /**
     * Check id. Id has to be the same if stock is the same. Id should not get verified if the
     * stock is not built by the stock manager.
     */
    @Test
    public void checkId() {
        final Stock wrongStock = new StockImpl(WRONG_ID, giampiArtic, null);
        final Result<Stock> testStockCorrectResult = stockInstance.createStock(giampiArtic, null);
        Assert.assertTrue(testStockCorrectResult.isPresent());
        Stock testStockCorrect = testStockCorrectResult.getValue();
        Assert.assertTrue(stockInstance.checkId(testStockCorrect));
        Assert.assertFalse(stockInstance.checkId(wrongStock));
    }
    /**
     * Test create a stock should return the same stock if built with same parameters.
     */
    @Test
    public void createStock() {
        final Date date = new Date();
        final Result<Stock> resStock1 = stockInstance.createStock(giampiArtic, date);
        final Result<Stock> resStock2 = stockInstance.createStock(giampiArtic, date);
        Assert.assertTrue(resStock1.isPresent());
        Assert.assertTrue(resStock2.isPresent());
        Assert.assertEquals(resStock1.getValue(), resStock2.getValue());
    }
    /**
     * Test create a beer stock should return the same stock if built with same parameters.
     */
    @Test
    public void createBeerStock() {

    }
    /**
     * Test get stock. It should return all the stock registered.
     */
    @Test
    public void getStocks() {
    }
}
