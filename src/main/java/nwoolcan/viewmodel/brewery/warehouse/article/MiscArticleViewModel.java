package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleType;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
 */
public final class MiscArticleViewModel extends ArticleViewModel {

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param article to be converted to {@link MiscArticleViewModel}.
     */
    public MiscArticleViewModel(final Article article) {
        super(article);
    }

    @Override
    public ArticleType getArticleType() {
        return ArticleType.MISC;
    }

}
