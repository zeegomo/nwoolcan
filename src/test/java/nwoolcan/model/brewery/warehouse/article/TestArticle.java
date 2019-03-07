package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test Article Class.
 */
public class TestArticle {

    private final Integer id = 1;
    private final Integer negId = -1;
    private final String name = "DummyName";
    private static final UnitOfMeasure UOM = UnitOfMeasure.Kilogram;

    /**
     * Method that tests the constructor with null name.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNameNull() {
        new ArticleImpl(id, null, UOM);
    }
    /**
     * Method that tests the constructor with null id.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithIdNull() {
        new ArticleImpl(null, name, UOM);
    }
    /**
     * Method that tests the constructor with both null id and name.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullParameters() {
        new ArticleImpl(null, null, null);
    }
    /**
     * Method that tests the constructor with both null empty name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyName() {
        new ArticleImpl(id, "", UOM);
    }
    /**
     * Method that tests the constructor with both null empty name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeId() {
        new ArticleImpl(negId, name, UOM);
    }
    /**
     * Method that tests the getters and relative errors.
     */
    @Test
    public void testGetters() {
        final Article article = new ArticleImpl(id, name, UOM);
        Assert.assertEquals(id, article.getId());
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
        final Article art1
            = new ArticleImpl(id, name, UOM);
        final Article art2
            = new ArticleImpl(id, name, UOM);
        final Article art4
            = new ArticleImpl(id + 1, name, UOM);
        Assert.assertEquals(art1, art2);
        Assert.assertEquals(art1, art1);
        Assert.assertEquals(art1, art1);
        Assert.assertNotEquals(art1, art4);
        Assert.assertNotEquals(art4, art1);
    }

}
