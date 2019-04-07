package nwoolcan.viewmodel.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
 */
public final class BeerArticleViewModel extends ArticleViewModel {

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @param id of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @param name of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @param unitOfMeasure of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     */
    public BeerArticleViewModel(final int id,
                                final String name,
                                final UnitOfMeasure unitOfMeasure) {
        super(id, name, unitOfMeasure);
    }

    @Override
    public ArticleType getArticleType() {
        return ArticleType.FINISHED_BEER;
    }
}
