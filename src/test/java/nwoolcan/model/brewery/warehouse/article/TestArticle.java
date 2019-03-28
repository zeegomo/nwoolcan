package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test Article Class.
 */
public class TestArticle {

    private final String name = "DummyName";
    private static final UnitOfMeasure UOM = UnitOfMeasure.Gram;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.Unit;

    /**
     * Method that tests the constructor with empty name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyName() {
        new ArticleImpl("", UOM);
    }
    /**
     * Method that tests the getters and relative errors.
     */
    @Test
    public void testGetters() {
        final Article article = new ArticleImpl(name, UOM);
        Assert.assertEquals(name, article.getName());
        Assert.assertEquals(ArticleType.MISC, article.getArticleType());
        Assert.assertTrue(article.toIngredientArticle().isError());
        Assert.assertEquals(IllegalAccessException.class, article.toIngredientArticle()
                                                                 .getError().getClass());
        Assert.assertTrue(article.toBeerArticle().isError());
        Assert.assertEquals(IllegalAccessException.class, article.toBeerArticle()
                                                                 .getError().getClass());
        Assert.assertEquals(UOM, article.getUnitOfMeasure());

    }
    /**
     * Method that tests the equals method.
     */
    @Test
    public void testEquals() {
        final Article art1 = new ArticleImpl(name, UOM);
        final Article art2 = new ArticleImpl(name, UOM);
        final Article art4 = new ArticleImpl(name, UOM1);
        Assert.assertEquals(art1, art2);
        Assert.assertEquals(art1, art1);
        Assert.assertNotEquals(art1, art4);
    }

}
