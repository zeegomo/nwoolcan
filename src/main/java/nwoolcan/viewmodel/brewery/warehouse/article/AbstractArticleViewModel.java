package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * Abstract View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
 */
public abstract class AbstractArticleViewModel {

    private final Article article;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param article to be converted in {@link AbstractArticleViewModel}.
     */
    public AbstractArticleViewModel(final Article article) {
        this.article = article;
    }
    /**
     * Returns the id of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the id of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public int getId() {
        return article.getId();
    }
    /**
     * Returns the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public String getName() {
        return article.getName();
    }
    /**
     * Returns the {@link ArticleType} of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the {@link ArticleType} of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public abstract ArticleType getArticleType();
    /**
     * Returns the {@link UnitOfMeasure} of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the {@link UnitOfMeasure} of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return article.getUnitOfMeasure();
    }
    /**
     * Generates an {@link AbstractArticleViewModel} starting from a common {@link Article},
     * creating a specific one accordingly with the {@link ArticleType} of the {@link Article}.
     * @param article to be converted.
     * @return the converted one.
     */
    public static AbstractArticleViewModel getViewArticle(final Article article) {
        switch (article.getArticleType()) {
            case INGREDIENT:
                return new IngredientArticleViewModel(article.toIngredientArticle().getValue());
            case FINISHED_BEER:
                return new BeerArticleViewModel(article.toBeerArticle().getValue());
            case MISC:
            default:
                return new MiscArticleViewModel(article);
        }
    }
}
