package nwoolcan.model.brewery.warehouse.article;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test Article Class.
 */
public class TestArticle {

    private final Integer id = 1;
    private final Integer negId = -1;
    private final String name = "DummyName";

    /**
     * Method that tests the constructor with null name.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNameNull() {
        new ArticleImpl(id, null);
    }
    /**
     * Method that tests the constructor with null id.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithIdNull() {
        new ArticleImpl(null, name);
    }
    /**
     * Method that tests the constructor with both null id and name.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullParameters() {
        new ArticleImpl(null, null);
    }
    /**
     * Method that tests the constructor with both null empty name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyName() {
        new ArticleImpl(id, "");
    }
    /**
     * Method that tests the constructor with both null empty name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeId() {
        new ArticleImpl(negId, name);
    }
    /**
     * Method that tests the getters and relative errors.
     */
    @Test
    public void testGetters() {
        final Article article = new ArticleImpl(id, name);
        Assert.assertEquals(id, article.getId());
        Assert.assertEquals(name, article.getName());
        Assert.assertEquals(ArticleType.MISC, article.getArticleType());
        Assert.assertTrue(article.toIngredientArticle().isError());
        Assert.assertEquals(IllegalAccessException.class, article.toIngredientArticle()
                                                                 .getError().getClass());
        Assert.assertTrue(article.toBeerArticle().isError());
        Assert.assertEquals(IllegalAccessException.class, article.toBeerArticle()
                                                                 .getError().getClass());

    }
    /**
     * Method that tests the equals method.
     */
    @Test
    public void testEquals() {
        final Article art1
            = new ArticleImpl(id, name);
        final Article art2
            = new ArticleImpl(id, name);
        final Article art3 = art1;
        final Article art4
            = new ArticleImpl(id + 1, name);
        Assert.assertEquals(art1, art2);
        Assert.assertEquals(art1, art1);
        Assert.assertEquals(art1, art3);
        Assert.assertNotEquals(art1, art4);
        Assert.assertNotEquals(art4, art3);
    }

}
