package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
 */
public final class BeerArticleViewModel extends AbstractArticleViewModel {

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.BeerArticle}.
     * @param article to be converted in {@link BeerArticleViewModel}.
     */
    public BeerArticleViewModel(final BeerArticle article) {
        super(article);
    }

    @Override
    public ArticleType getArticleType() {
        return ArticleType.FINISHED_BEER;
    }

    @Override
    public String toString() {
        return "(" + getId() + ") " + getName() + " [BeerArticle]";
    }
}
