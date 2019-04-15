package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * Abstract View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
 */
public abstract class AbstractArticleViewModel {

    private final int id;
    private final String name;
    private final UnitOfMeasure unitOfMeasure;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param article to be converted in {@link AbstractArticleViewModel}.
     */
    public AbstractArticleViewModel(final Article article) {
        this.id = article.getId();
        this.name = article.getName();
        this.unitOfMeasure = article.getUnitOfMeasure();
    }
    /**
     * Returns the id of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the id of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public int getId() {
        return id;
    }
    /**
     * Returns the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public String getName() {
        return name;
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
        return unitOfMeasure;
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

    /**
     * Return the article type plus the ingredient type if the article is of type INGREDIENT.
     * @return the article type plus the ingredient type if the article is of type INGREDIENT.
     */
    public String getArticleTypeSummary() {
        return getArticleType().toString()
            + (getArticleType() == ArticleType.INGREDIENT
            ? " [" + ((IngredientArticleViewModel) this).getIngredientType() + "]"
            : "");
    }
}
