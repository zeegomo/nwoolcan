package nwoolcan.controller.viewModel.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;

/**
 * View-Model interface with methods callable to obtain information about the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public interface ViewMiscArticle extends ViewArticle {
    @Override
    default ArticleType getArticleType() {
        return ArticleType.MISC;
    }
}
