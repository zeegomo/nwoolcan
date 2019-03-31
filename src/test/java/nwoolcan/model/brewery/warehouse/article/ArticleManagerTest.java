package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Test the {@link ArticleManager} class.
 */
public class ArticleManagerTest {

    private final ArticleManager articleManager = ArticleManager.getInstance();
    private static final String NAME = "Dummy Name";
    private static final String NEW_NAME = "New Dummy Name";
    private static final String SBORN_NEW_NAME = "New Sborning Dummy Name";
    private static final String SBORN_NAME = "Sborning Dummy Name";
    private static final UnitOfMeasure UOM = UnitOfMeasure.BOTTLE_33_CL;
    private final Article article = articleManager.createMiscArticle(NAME, UOM);
    private final BeerArticle beerArticle = articleManager.createBeerArticle(NAME, UOM);
    private final IngredientArticle ingredientArticle = articleManager.createIngredientArticle(NAME, UOM, IngredientType.HOPS);

    /**
     * Test for getInstance.
     */
    @Test
    public void getInstance() {
        Assert.assertSame(ArticleManager.getInstance(), articleManager);
    }
    /**
     * Test for checkId.
     */
    @Test
    public void checkId() {
        Assert.assertTrue(articleManager.checkId(article));
    }
    /**
     * Test for createMiscArticle.
     */
    @Test
    public void createMiscArticle() {
        Assert.assertEquals(ArticleType.MISC, article.getArticleType());
        Assert.assertEquals(NAME, article.getName());
        Assert.assertEquals(UOM, article.getUnitOfMeasure());
    }
    /**
     * Test for createBeerArticle.
     */
    @Test
    public void createBeerArticle() {
        Assert.assertEquals(ArticleType.FINISHED_BEER, beerArticle.getArticleType());
        Assert.assertEquals(NAME, beerArticle.getName());
        Assert.assertEquals(UOM, beerArticle.getUnitOfMeasure());
    }
    /**
     * Test for createIngredientArticle.
     */
    @Test
    public void createIngredientArticle() {
        Assert.assertEquals(ArticleType.INGREDIENT, ingredientArticle.getArticleType());
        Assert.assertEquals(NAME, ingredientArticle.getName());
        Assert.assertEquals(UOM, ingredientArticle.getUnitOfMeasure());
        Assert.assertEquals(IngredientType.HOPS, ingredientArticle.getIngredientType());
    }
    /**
     * Test for getArticles.
     */
    @Test
    public void getArticles() {
        final Set<Article> articles = articleManager.getArticles();
        Assert.assertTrue(articles.contains(article));
        Assert.assertTrue(articles.contains(beerArticle));
        Assert.assertTrue(articles.contains(ingredientArticle));
        final Set<Integer> articlesIds = new HashSet<>();
        for (final Article a : articles) {
            Assert.assertFalse(articlesIds.contains(a.getId()));
            articlesIds.add(a.getId());
        }
    }
    /**
     * Test for setName.
     */
    @Test
    public void setName() {
        Assert.assertTrue(articleManager.setName(article, NEW_NAME).isPresent());
        Assert.assertTrue(articleManager.setName(new ArticleImpl(0, SBORN_NEW_NAME, UOM), SBORN_NAME).isError());
    }
}
