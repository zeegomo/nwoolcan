package nwoolcan.controller.warehouse;

import nwoolcan.controller.warehouse.article.ViewArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;

import java.util.List;

/**
 * Model of the view of the list of the articles.
 */
public interface ViewQueriedArticle {

    /**
     * Returns a {@link List} of {@link ViewArticle}, accordingly with the {@link QueryArticle}.
     * @param queryArticle the {@link QueryArticle}.
     * @return a {@link List} of {@link ViewArticle}, accordingly with the {@link QueryArticle}.
     */
    List<ViewArticle> getLastArticles(QueryArticle queryArticle);

}
