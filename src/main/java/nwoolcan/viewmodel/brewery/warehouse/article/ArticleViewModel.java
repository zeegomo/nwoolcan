package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * Abstract View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
 */
public abstract class ArticleViewModel {

    private final Article article;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param article to be converted in {@link ArticleViewModel}.
     */
    public ArticleViewModel(final Article article) {
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
}
