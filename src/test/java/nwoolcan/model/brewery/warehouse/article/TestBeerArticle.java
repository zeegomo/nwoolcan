package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for BeerArticle.
 */
public class TestBeerArticle {

    private final Integer id = 1;
    private final String name = "DummyName";

    /**
     * Method that tests the constructor with both null id and name.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullBatch() {
        new BeerArticleImpl(id, name, null);
    }
    /**
     * Method that tests the getters.
     */
    @Test
    public void testGetters() {
        final Batch batch = new Batch() { };
        final BeerArticle beerArticle = new BeerArticleImpl(id, name, batch);
        Assert.assertEquals(ArticleType.FINISHED_BEER, beerArticle.getArticleType());
        Assert.assertEquals(batch, beerArticle.getBatch());
        /*
         * Missing test for toBeerArticle because Result is not implemented yet.
         */
    }

}
