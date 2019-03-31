package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manager for {@link Article} objects. It is used by tests and by the
 * {@link nwoolcan.model.brewery.warehouse.Warehouse} in order to create {@link Article}, check the
 * id of the {@link Article}, avoid repetitions and set name of the {@link Article}.
 */
public final class ArticleManager {

    @Nullable private static ArticleManager instance;
    private static final String ARTICLE_NOT_REGISTERED = "The article was not registered. You can not change its name.";
    private static final String ARTICLE_WITH_NEW_NAME_ALREADY_REGISTERED = "Changing the name to this article would produce an article which already exists.";
    private static final int FAKE_ID = -1;
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
        Article article = new ArticleImpl(FAKE_ID, name, unitOfMeasure);
        if (!articleToId.containsKey(article)) {
            int newId = nextAvailableId++;
            article = new ArticleImpl(newId, name, unitOfMeasure);
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
        Article beerArticle = new BeerArticleImpl(FAKE_ID, name, unitOfMeasure);
        if (!articleToId.containsKey(beerArticle)) {
            int newId = nextAvailableId++;
            beerArticle = new BeerArticleImpl(newId, name, unitOfMeasure);
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
        Article ingredientArticle = new IngredientArticleImpl(FAKE_ID, name, unitOfMeasure, ingredientType);
        if (!articleToId.containsKey(ingredientArticle)) {
            int newId = nextAvailableId++;
            ingredientArticle = new IngredientArticleImpl(newId, name, unitOfMeasure, ingredientType);
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
    /**
     * Setter for the name of the {@link Article}.
     * @param article the {@link Article} to which we want to change the name.
     * @param newName the new name to be assigned to the {@link Article}.
     * @return a {@link Result} of {@link Article} for fluency.
     */
    public synchronized Result<Article> setName(final Article article, final String newName) {
        if (!checkId(article)) {
            return Result.error(new IllegalArgumentException(ARTICLE_NOT_REGISTERED));
        }
        String oldName = article.getName();
        int id = article.getId();
        articleToId.remove(article);
        ((AbstractArticle) article).setName(newName);
        if (articleToId.containsKey(article)) {
            ((AbstractArticle) article).setName(oldName);
            articleToId.put(article, id);
            return Result.error(new IllegalArgumentException(ARTICLE_WITH_NEW_NAME_ALREADY_REGISTERED));
        }
        articleToId.put(article, id);
        idToArticle.put(id, article); // TODO check if it is necessary.
        return Result.of(article);
    }

}
