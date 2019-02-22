package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

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
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final Batch batch = new Batch() { };
        final Article beerArticle = new BeerArticleImpl(id, name, batch);
        Assert.assertEquals(ArticleType.FINISHED_BEER, beerArticle.getArticleType());
        Objects.requireNonNull(beerArticle.toBeerArticle());
        Assert.assertTrue(beerArticle.toBeerArticle().isPresent());
        Assert.assertEquals(BeerArticleImpl.class, beerArticle.toBeerArticle().getValue().getClass());
        Assert.assertEquals(batch, beerArticle.toBeerArticle().getValue().getBatch());
    }

}
