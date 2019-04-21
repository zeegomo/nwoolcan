package nwoolcan.model.brewery;

import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.BatchMethod;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.model.brewery.batch.misc.BeerDescription;
import nwoolcan.model.brewery.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStockBuilder;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
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
    private final BeerDescription beerDescription = new BeerDescriptionImpl(OWNER_NAME, OWNER_NAME);
    private final BatchMethod batchMethod = BatchMethod.ALL_GRAIN;
    private final Quantity initialSize = Quantity.of(3, UnitOfMeasure.BOTTLE_33_CL).getValue();
    private final StepType initialStep = StepTypeEnum.FINALIZED;
    private final Brewery brewery = new BreweryImpl();
    private final Batch batch = brewery.getBatchBuilder()
                                       .build(beerDescription, batchMethod, initialSize, initialStep)
                                       .getValue();

    private final Batch notEndedBatch = brewery.getBatchBuilder()
                                               .build(beerDescription, batchMethod, initialSize, StepTypeEnum.BOILING)
                                               .getValue();

    /**
     * Test the getter of the name of the {@link Brewery}.
     */
    @Test
    public void getBreweryName() {
        brewery.setBreweryName(BREWERY_NAME);
        Assert.assertTrue(brewery.getBreweryName().isPresent());
        Assert.assertEquals(BREWERY_NAME, brewery.getBreweryName().get());
    }
    /**
     * Test the getter of the name of the owner of the {@link Brewery}.
     */
    @Test
    public void getOwnerName() {
        brewery.setOwnerName(OWNER_NAME);
        Assert.assertTrue(brewery.getOwnerName().isPresent());
        Assert.assertEquals(OWNER_NAME, brewery.getOwnerName().get());
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
        final BeerArticle wrongBeerArticle = brewery.getWarehouse()
                                                    .createBeerArticle("WRONG",
                                                                  UnitOfMeasure.UNIT);

        final Result<Empty> wrongArticle = brewery.stockBatch(batch, wrongBeerArticle);
        Assert.assertTrue(wrongArticle.isError());
        final Result<Empty> notEnded = brewery.stockBatch(notEndedBatch, beerArticle);
        Assert.assertTrue(notEnded.isError());

        final Result<Empty> stockBatchRes = brewery.stockBatch(batch, beerArticle);
        Assert.assertTrue(stockBatchRes.isPresent());
        final Result<QueryStock> queryStockRes = new QueryStockBuilder().setArticle(AbstractArticleViewModel.getViewArticle(beerArticle)).build();
        Assert.assertTrue(queryStockRes.isPresent());
        List<Stock> stocks = brewery.getWarehouse().getStocks(queryStockRes.getValue());
        Assert.assertEquals(1, stocks.size());
        Assert.assertEquals(batch.getCurrentSize(), stocks.get(0).getRemainingQuantity());

        final Result<Empty> stockAgain = brewery.stockBatch(batch, beerArticle);
        Assert.assertTrue(stockAgain.isError());
    }
}
