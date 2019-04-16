package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for BeerArticle.
 */
public class TestBeerArticleImpl {

    private final ArticleManager articleManager = new ArticleManager();
    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.UNIT;
    private static final String NAME = "DummyName";

    /**
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final Article beerArticle = articleManager.createBeerArticle(NAME, UOM);
        Assert.assertEquals(ArticleType.FINISHED_BEER, beerArticle.getArticleType());
        Assert.assertTrue(beerArticle.toBeerArticle().isPresent());
        Assert.assertEquals(BeerArticleImpl.class, beerArticle.toBeerArticle()
                                                              .getValue()
                                                              .getClass());
    }
    /**
     * Method that tests the equals method.
     */
    @Test
    public void testEquals() {
        final BeerArticle beerArt1 = articleManager.createBeerArticle(NAME, UOM);
        final BeerArticle beerArt2 = articleManager.createBeerArticle(NAME, UOM);
        final BeerArticle beerArt4 = articleManager.createBeerArticle(NAME, UOM1);
        Assert.assertEquals(beerArt1, beerArt2);
        Assert.assertEquals(beerArt1, beerArt1);
        Assert.assertNotEquals(beerArt1, beerArt4);
    }

}
