package nwoolcan.controller.viewModel.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.utils.UnitOfMeasure;

public interface ViewArticle {
    int getId();
    String getName();
    ArticleType getArticleType(); // TODO change with a view object?

    UnitOfMeasure getUnitOfMeasure(); // TODO change with a view object?
}
