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
     * Method that tests the getters.
     */
    @Test
    public void testGetters() {
        final Article article = new ArticleImpl(id, name);
        Assert.assertEquals(id, article.getId());
        Assert.assertEquals(name, article.getName());
        Assert.assertEquals(ArticleType.MISC, article.getArticleType());
        /*
         * Missing test for toIngredientArticle and toBeerArticle because Result is not implemented yet.
         */
    }

}
