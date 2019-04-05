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
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Test for the {@link BreweryImpl}.
 */
public class BreweryImplTest {

    private static final String BREWERY_NAME = "Ciusseppe-Mastro-Birraio";
    private static final String OWNER_NAME = "Ciusseppe";
    private final Brewery brewery = new BreweryImpl(BREWERY_NAME, OWNER_NAME);

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
        final BeerDescription beerDescription = new BeerDescriptionImpl(OWNER_NAME, OWNER_NAME);
        final BatchMethod batchMethod = BatchMethod.ALL_GRAIN;
        final Quantity initialSize = Quantity.of(3, UnitOfMeasure.MILLILITER);
        final StepType initialStep = StepTypeEnum.PACKAGING;
        final Batch batch = new BatchBuilder(beerDescription,
                                             batchMethod,
                                             initialSize,
                                             initialStep).build().getValue();
        brewery.addBatch(batch);
        final Result<QueryBatch> queryBatchResult = new QueryBatchBuilder().build();
        Assert.assertTrue(queryBatchResult.isPresent());
        final Collection<Batch> batches = brewery.getBatches(queryBatchResult.getValue());
        Assert.assertTrue(batches.contains(batch));
    }
}
