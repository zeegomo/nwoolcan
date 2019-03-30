package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.BatchBuilder;
import nwoolcan.model.brewery.production.batch.BatchMethod;
import nwoolcan.model.brewery.production.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for BeerArticle.
 */
public class TestBeerArticle {

    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.UNIT;
    private final String name = "DummyName";

    private final Batch batch = new BatchBuilder(
        new BeerDescriptionImpl("Test beer", "Test style"),
        BatchMethod.ALL_GRAIN,
        Quantity.of(1000, UnitOfMeasure.MILLILITER),
        StepTypeEnum.MASHING
    ).build().getValue();

    /**
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final Article beerArticle = new BeerArticleImpl(name, UOM, batch);
        Assert.assertEquals(ArticleType.FINISHED_BEER, beerArticle.getArticleType());
        Assert.assertTrue(beerArticle.toBeerArticle().isPresent());
        Assert.assertEquals(BeerArticleImpl.class, beerArticle.toBeerArticle()
                                                              .getValue()
                                                              .getClass());
        Assert.assertEquals(batch, beerArticle.toBeerArticle().getValue().getBatch());
    }
    /**
     * Method that tests the equals method.
     */
    @Test
    public void testEquals() {
        final BeerArticle beerArt1 = new BeerArticleImpl(name, UOM, batch);
        final BeerArticle beerArt2 = new BeerArticleImpl(name, UOM, batch);
        final BeerArticle beerArt4 = new BeerArticleImpl(name, UOM1, batch);
        Assert.assertEquals(beerArt1, beerArt2);
        Assert.assertEquals(beerArt1, beerArt1);
        Assert.assertNotEquals(beerArt1, beerArt4);
    }

}
