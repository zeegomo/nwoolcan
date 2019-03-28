package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for BeerArticle.
 */
public class TestBeerArticle {

    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private final Integer id = 1;
    private final String name = "DummyName";

    /**
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final Batch batch = new Batch() { };
        final Article beerArticle = new BeerArticleImpl(id, name, UOM, batch);
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
        Batch batch = new Batch() { };
        final BeerArticle beerArt1 = new BeerArticleImpl(id, name, UOM, batch);
        final BeerArticle beerArt2 = new BeerArticleImpl(id, name, UOM, batch);
        final BeerArticle beerArt4 = new BeerArticleImpl(id + 1, name, UOM, batch);
        Assert.assertEquals(beerArt1, beerArt2);
        Assert.assertEquals(beerArt1, beerArt1);
        Assert.assertEquals(beerArt1, beerArt1);
        Assert.assertNotEquals(beerArt1, beerArt4);
        Assert.assertNotEquals(beerArt4, beerArt1);
    }

}
