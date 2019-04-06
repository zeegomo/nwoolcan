package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.BatchBuilder;
import nwoolcan.model.brewery.production.batch.BatchMethod;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.production.batch.QueryBatchBuilder;
import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStockBuilder;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * Test for the {@link BreweryImpl}.
 */
public class BreweryImplTest {

    private static final String BREWERY_NAME = "Ciusseppe-Mastro-Birraio";
    private static final String OWNER_NAME = "Ciusseppe";
    private final Brewery brewery = new BreweryImpl(BREWERY_NAME, OWNER_NAME);
    private final BeerDescription beerDescription = new BeerDescriptionImpl(OWNER_NAME, OWNER_NAME);
    private final BatchMethod batchMethod = BatchMethod.ALL_GRAIN;
    private final Quantity initialSize = Quantity.of(3, UnitOfMeasure.BOTTLE_33_CL);
    private final StepType initialStep = StepTypeEnum.FINALIZED;
    private final Batch batch = new BatchBuilder(beerDescription,
                                         batchMethod,
                                         initialSize,
                                         initialStep).build().getValue();

    /**
     * Test the getter of the name of the {@link Brewery}.
     */
    @Test
    public void getBreweryName() {
        Assert.assertEquals(BREWERY_NAME, brewery.getBreweryName());
    }
    /**
     * Test the getter of the name of the owner of the {@link Brewery}.
     */
    @Test
    public void getOwnerName() {
        Assert.assertEquals(OWNER_NAME, brewery.getOwnerName());
    }
    /**
     * Test that once a {@link Batch} is added, it is present in the {@link Collection}
     * of {@link Batch} of the {@link Brewery}.
     */
    @Test
    public void addBatch() {
        brewery.addBatch(batch);
        final Result<QueryBatch> queryBatchResult = new QueryBatchBuilder().build();
        Assert.assertTrue(queryBatchResult.isPresent());
        final Collection<Batch> batches = brewery.getBatches(queryBatchResult.getValue());
        Assert.assertTrue(batches.contains(batch));
    }

    /**
     * Test the stockBatch method.
     */
    @Test
    public void testStockBatch() {
        final BeerArticle beerArticle = brewery.getWarehouse()
                                               .createBeerArticle("SUPERSBORNARTICLE",
                                                                  UnitOfMeasure.BOTTLE_33_CL);
        final Result<Empty> stockBatchRes = brewery.stockBatch(batch, beerArticle, null);
        Assert.assertTrue(stockBatchRes.isPresent());
        final Result<QueryStock> queryStockRes = new QueryStockBuilder().setArticle(beerArticle).build();
        Assert.assertTrue(queryStockRes.isPresent());
        List<Stock> stocks = brewery.getWarehouse().getStocks(queryStockRes.getValue());
        Assert.assertFalse(stocks.isEmpty());
    }
}
