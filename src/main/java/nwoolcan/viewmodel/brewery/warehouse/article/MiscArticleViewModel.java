package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
 */
public final class MiscArticleViewModel extends ArticleViewModel {

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param id of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param name of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param unitOfMeasure of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     */
    public MiscArticleViewModel(final int id,
                                final String name,
                                final UnitOfMeasure unitOfMeasure) {
        super(id, name, unitOfMeasure);
    }

    @Override
    public ArticleType getArticleType() {
        return ArticleType.MISC;
    }

}
