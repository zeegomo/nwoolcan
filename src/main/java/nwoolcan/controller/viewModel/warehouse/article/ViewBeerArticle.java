package nwoolcan.controller.viewModel.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;

public interface ViewBeerArticle extends ViewArticle {

    @Override
    default ArticleType getArticleType() {
        return ArticleType.FINISHED_BEER;
    }
}
