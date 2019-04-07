package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * Abstract View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
 */
public abstract class ArticleViewModel {

    private final int id;
    private final String name;
    private final UnitOfMeasure unitOfMeasure;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param id of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @param unitOfMeasure of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public ArticleViewModel(final int id,
                            final String name,
                            final UnitOfMeasure unitOfMeasure) {
        this.id = id;
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
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
}