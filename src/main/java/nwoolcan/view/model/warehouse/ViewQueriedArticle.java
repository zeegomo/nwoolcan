package nwoolcan.view.model.warehouse;

import nwoolcan.view.model.warehouse.article.ViewArticle;

import java.util.List;

/**
 * Model of the view of the list of the articles.
 */
public final class ViewQueriedArticle {

    private final List<ViewArticle> articles;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.ArticleManager} of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param articles produced by a query.
     */
    ViewQueriedArticle(final List<ViewArticle> articles) {
        this.articles = articles;
    }
    /**
     * Returns a {@link List} of {@link ViewArticle}.
     * @return a {@link List} of {@link ViewArticle}.
     */
    public List<ViewArticle> getQueriedArticles() {
        return articles;
    }

}
