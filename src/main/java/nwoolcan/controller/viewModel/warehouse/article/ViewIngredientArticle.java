package nwoolcan.controller.viewModel.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.IngredientType;

public interface ViewIngredientArticle extends ViewArticle {

    @Override
    default ArticleType getArticleType() { // TODO change with a view object?
        return ArticleType.INGREDIENT;
    }
    IngredientType getIngredientType(); //TODO change with a view object?
}
