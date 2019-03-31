package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Id manager for {@link Article} objects. It is used by ArticleImpl to generate the
 * id and by WarehouseImpl to check the id of the elements.
 */
public final class ArticleManager {

    @Nullable private static ArticleManager instance;
    private static int fakeId = 1;
    private int nextAvailableId;
    private Map<Article, Integer> articleToId;
    private Map<Integer, Article> idToArticle;

    private ArticleManager() {
        nextAvailableId = 1;
        articleToId = new HashMap<>();
        idToArticle = new HashMap<>();
    }
    /**
     * Returns the only instance of the {@link ArticleManager} using a singleton pattern.
     * @return the only instance of the {@link ArticleManager} using a singleton pattern.
     */
    public static synchronized ArticleManager getInstance() {
        if (instance == null) {
            instance = new ArticleManager();
        }
        return instance;
    }
    /**
     * Checks the consistency of the article.
     * @param article to be checked.
     * @return a boolean denoting whether the id is correct or not.
     */
    public synchronized boolean checkId(final Article article) {
        return articleToId.containsKey(article) && article.getId().equals(articleToId.get(article));
    }
    /**
     * Creates a misc {@link Article} and insert it into the {@link ArticleManager}. If it already exists, it will be returned.
     * @param name of the {@link Article}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link Article}.
     * @return a new {@link Article} if it does not exist in the {@link ArticleManager}, otherwise the existing one.
     */
    @SuppressWarnings("NullAway")
    public synchronized Article createMiscArticle(final String name,
                                              final UnitOfMeasure unitOfMeasure) {
        Article article = new ArticleImpl(fakeId, name, unitOfMeasure); // TODO when creating an article to check it, just put a false id. This require hashing not to include the id of the article in articleImpl.
        if (!articleToId.containsKey(article)) {
            int newId = nextAvailableId++;
            article = new ArticleImpl(newId, name, unitOfMeasure); // TODO here i should put the real id: newID
            articleToId.put(article, newId);
            idToArticle.put(newId, article);
        }
        return idToArticle.get(articleToId.get(article));
    }
    /**
     * Creates a {@link BeerArticle} and insert it into the {@link ArticleManager}. If it already exists, it will be returned.
     * @param name of the {@link BeerArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link BeerArticle}.
     * @return a new {@link BeerArticle} if it does not exist in the {@link ArticleManager}, otherwise the existing one.
     */
    @SuppressWarnings("NullAway")
    public synchronized BeerArticle createBeerArticle(final String name,
                                                      final UnitOfMeasure unitOfMeasure) {
        Article beerArticle = new BeerArticleImpl(fakeId, name, unitOfMeasure); // TODO when creating an article to check it, just put a false id. This require hashing not to include the id of the article in articleImpl.
        if (!articleToId.containsKey(beerArticle)) {
            int newId = nextAvailableId++;
            beerArticle = new BeerArticleImpl(fakeId, name, unitOfMeasure); // TODO here i should put the real id: newID
            articleToId.put(beerArticle, newId);
            idToArticle.put(newId, beerArticle);
        }
        return (BeerArticle) idToArticle.get(articleToId.get(beerArticle));
    }
    /**
     * Creates a {@link IngredientArticle} and insert it into the {@link ArticleManager}. If it already exists, it will be returned.
     * @param name of the {@link IngredientArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link IngredientArticle}.
     * @param ingredientType the {@link IngredientType} of the {@link IngredientArticle}.
     * @return a new {@link IngredientArticle} if it does not exist in the {@link ArticleManager}, otherwise the existing one.
     */
    @SuppressWarnings("NullAway")
    public synchronized IngredientArticle createIngredientArticle(final String name,
                                                                  final UnitOfMeasure unitOfMeasure,
                                                                  final IngredientType ingredientType) {
        Article ingredientArticle = new IngredientArticleImpl(fakeId, name, unitOfMeasure, ingredientType);  // TODO when creating an article to check it, just put a false id. This require hashing not to include the id of the article in articleImpl.
        if (!articleToId.containsKey(ingredientArticle)) {
            int newId = nextAvailableId++;
            ingredientArticle = new IngredientArticleImpl(fakeId, name, unitOfMeasure, ingredientType); // TODO here i should put the real id: newID
            articleToId.put(ingredientArticle, newId);
            idToArticle.put(newId, ingredientArticle);
        }
        return (IngredientArticle) idToArticle.get(articleToId.get(ingredientArticle));
    }

    /**
     * Return a {@link Set} of all the managed {@link Article}.
     * @return a {@link Set} of all the managed {@link Article}.
     */
    public Set<Article> getArticles() {
        return new HashSet<>(articleToId.keySet());
    }
}
