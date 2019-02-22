package nwoolcan.model.brewery.warehouse.article;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

/**
 * Test for IngredientArticle.
 */
public class TestIngredientArticle {
    private final Integer id = 1;
    private final String name = "DummyName";

    /**
     * Method that tests the constructor with null Ingredient Type.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullIngredientType() {
        new IngredientArticleImpl(id, name, null);
    }
    /**
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final IngredientType ingredientType = IngredientType.FERMENTABLE;
        final Article ingredientArticle = new IngredientArticleImpl(id, name, ingredientType);
        Assert.assertEquals(ArticleType.INGREDIENT, ingredientArticle.getArticleType());
        Objects.requireNonNull(ingredientArticle.toIngredientArticle());
        Assert.assertTrue(ingredientArticle.toIngredientArticle().isPresent());
        Assert.assertEquals(IngredientArticleImpl.class, ingredientArticle.toIngredientArticle()
                                                                          .getValue()
                                                                          .getClass());
        Assert.assertEquals(ingredientType, ingredientArticle.toIngredientArticle()
                                                             .getValue()
                                                             .getIngredientType());
    }

}
