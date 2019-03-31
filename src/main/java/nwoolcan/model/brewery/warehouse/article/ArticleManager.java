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
    private int nextAvailableId;
    private Map<Article, Article> articleToArticle;

    private ArticleManager() {
        nextAvailableId = 1;
        articleToArticle = new HashMap<>();
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
        return articleToArticle.containsKey(article) && article.getId() == articleToArticle.get(article).getId();
    }
    /**
     * Creates a misc {@link Article} and insert it into the {@link ArticleManager}. If it already exists, it will be returned.
     * @param name of the {@link Article}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link Article}.
     * @return a new {@link Article} if it does not exist in the {@link ArticleManager}, otherwise the existing one.
     */
    public synchronized Article createMiscArticle(final String name,
                                              final UnitOfMeasure unitOfMeasure) {
        Article article = new ArticleImpl(nextAvailableId, name, unitOfMeasure);
        return getArticle(article);
    }
    /**
     * Creates a {@link BeerArticle} and insert it into the {@link ArticleManager}. If it already exists, it will be returned.
     * @param name of the {@link BeerArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link BeerArticle}.
     * @return a new {@link BeerArticle} if it does not exist in the {@link ArticleManager}, otherwise the existing one.
     */
    public synchronized BeerArticle createBeerArticle(final String name,
                                                      final UnitOfMeasure unitOfMeasure) {
        BeerArticle beerArticle = new BeerArticleImpl(nextAvailableId, name, unitOfMeasure);
        return (BeerArticle) getArticle(beerArticle);
    }
    /**
     * Creates a {@link IngredientArticle} and insert it into the {@link ArticleManager}. If it already exists, it will be returned.
     * @param name of the {@link IngredientArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link IngredientArticle}.
     * @param ingredientType the {@link IngredientType} of the {@link IngredientArticle}.
     * @return a new {@link IngredientArticle} if it does not exist in the {@link ArticleManager}, otherwise the existing one.
     */
    public synchronized IngredientArticle createIngredientArticle(final String name,
                                                                  final UnitOfMeasure unitOfMeasure,
                                                                  final IngredientType ingredientType) {
        IngredientArticle ingredientArticle = new IngredientArticleImpl(nextAvailableId, name, unitOfMeasure, ingredientType);
        return (IngredientArticle) getArticle(ingredientArticle);
    }
    /**
     * Return a {@link Set} of all the managed {@link Article}.
     * @return a {@link Set} of all the managed {@link Article}.
     */
    public Set<Article> getArticles() {
        return new HashSet<>(articleToArticle.keySet());
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
        articleToArticle.remove(article);
        ((AbstractArticle) article).setName(newName);
        if (articleToArticle.containsKey(article)) {
            ((AbstractArticle) article).setName(oldName);
            articleToArticle.put(article, article);
            return Result.error(new IllegalArgumentException(ARTICLE_WITH_NEW_NAME_ALREADY_REGISTERED));
        }
        articleToArticle.put(article, article);
        return Result.of(article);
    }
    /**
     * It checks whether the article is already in the map. If it is, it returns the one in the map.
     * If it is not, it adds it to the map and returns itself.
     * @param article to be checked.
     * @return the parameter if it is not in the map. Otherwise the corresponding into the map.
     */
    private synchronized Article getArticle(final Article article) {
        if (!articleToArticle.containsKey(article)) {
            nextAvailableId++;
            articleToArticle.put(article, article);
            return article;
        }
        return articleToArticle.get(article);
    }

}
