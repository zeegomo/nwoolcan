package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.IdGenerator;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.BatchBuilder;
import nwoolcan.model.brewery.batch.BatchMethod;
import nwoolcan.model.brewery.batch.misc.BeerDescription;
import nwoolcan.model.brewery.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
        final BeerDescription beerDescription = new BeerDescriptionImpl(GIAMPIERO, GIAMPIERO);
        final BatchMethod batchMethod = BatchMethod.ALL_GRAIN;
        final Quantity initialSize = Quantity.of(3, UnitOfMeasure.LITER).getValue();
        final StepType initialStep = StepTypeEnum.PACKAGING;
        final Batch batch = new BatchBuilder(
            new IdGenerator() {
                private int nextId = 0;

                @Override
                public int getNextId() {
                    return nextId++;
                }
            }
        ).build(beerDescription, batchMethod, initialSize, initialStep).getValue();
        final Date date = new Date();
        final BeerArticle beerArticle = articInstance.createBeerArticle(GIAMPIERO, UnitOfMeasure.BOTTLE_33_CL);
        final Result<BeerStock> beerStockResult = stockInstance.createBeerStock(beerArticle, date, batch);
        final Result<BeerStock> beerStockResult1 = stockInstance.createBeerStock(beerArticle, date, batch);
        Assert.assertTrue(beerStockResult.isPresent());
        Assert.assertTrue(beerStockResult1.isPresent());
        final BeerStock beerStock = beerStockResult.getValue();
        final BeerStock beerStock1 = beerStockResult1.getValue();
        Assert.assertSame(beerStock.getId(), beerStock1.getId());
        Assert.assertFalse(stockInstance.getStocks().isEmpty());
    }
    /**
     * Test get stock. It should return all the stock registered.
     */
    @Test
    public void getStocks() {
        final Set<Stock> stocks = stockInstance.getStocks();
        final Set<Integer> stocksIds = new HashSet<>();
        for (final Stock s : stocks) {
            Assert.assertFalse(stocksIds.contains(s.getId()));
            stocksIds.add(s.getId());
        }
    }
}
