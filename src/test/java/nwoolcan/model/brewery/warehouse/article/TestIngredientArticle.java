package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

/**
 * Test for IngredientArticle.
 */
public class TestIngredientArticle {

    private static final UnitOfMeasure UOM = UnitOfMeasure.Kilogram;

    private final Integer id = 1;
    private final String name = "DummyName";

    /**
     * Method that tests the constructor with null Ingredient Type.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullIngredientType() {
        new IngredientArticleImpl(id, name, null, null);
    }
    /**
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final IngredientType ingredientType = IngredientType.FERMENTABLE;
        final Article ingredientArticle = new IngredientArticleImpl(id, name, UOM, ingredientType);
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
    /**
     * Method that tests the equals method.
     */
    @Test
    public void testEquals() {
        final IngredientArticle ingArt1
            = new IngredientArticleImpl(id, name, UOM, IngredientType.FERMENTABLE);
        final IngredientArticle ingArt2
            = new IngredientArticleImpl(id, name, UOM, IngredientType.FERMENTABLE);
        final IngredientArticle ingArt4
            = new IngredientArticleImpl(id, name, UOM, IngredientType.HOPS);
        Assert.assertEquals(ingArt1, ingArt2);
        Assert.assertEquals(ingArt1, ingArt1);
        Assert.assertEquals(ingArt1, ingArt1);
        Assert.assertNotEquals(ingArt1, ingArt4);
        Assert.assertNotEquals(ingArt4, ingArt1);
    }

}
