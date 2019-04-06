package nwoolcan.view.model.warehouse;

import nwoolcan.view.model.warehouse.article.ArticleViewModel;

import java.util.List;

/**
 * Model of the view of the list of the articles.
 */
public final class QueriedArticleViewModel {

    private final List<ArticleViewModel> articles;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.ArticleManager} of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param articles produced by a query.
     */
    public QueriedArticleViewModel(final List<ArticleViewModel> articles) {
        this.articles = articles;
    }
    /**
     * Returns a {@link List} of {@link ArticleViewModel}.
     * @return a {@link List} of {@link ArticleViewModel}.
     */
    public List<ArticleViewModel> getQueriedArticles() {
        return articles;
    }

}
