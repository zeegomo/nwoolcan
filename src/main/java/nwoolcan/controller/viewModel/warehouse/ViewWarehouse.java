package nwoolcan.controller.viewModel.warehouse;

import nwoolcan.controller.viewModel.warehouse.stock.ViewStock;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.controller.viewModel.warehouse.article.ViewArticle;

import java.util.List;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
 */
public interface ViewWarehouse {
    /**
     * Returns a list of {@link ViewStock}, accordingly with the {@link QueryStock}.
     * @param queryStock the {@link QueryStock}.
     * @return a list of {@link ViewStock}, accordingly with the {@link QueryStock}.
     */
    List<ViewStock> getStocks(QueryStock queryStock); // TODO use querystock or something else?
    /**
     * Returns a {@link List} of {@link ViewArticle}, accordingly with the {@link QueryArticle}.
     * @param queryArticle the {@link QueryArticle}.
     * @return a {@link List} of {@link ViewArticle}, accordingly with the {@link QueryArticle}.
     */
    List<ViewArticle> getArticles(QueryArticle queryArticle); // TODO use queryarticle or something else of the view?
}
