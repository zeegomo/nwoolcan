package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientType;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
 */
public final class IngredientArticleViewModel extends AbstractArticleViewModel {

    private final IngredientType ingredientType;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param ingredientArticle to be converted in {@link IngredientArticleViewModel}.
     */
    public IngredientArticleViewModel(final IngredientArticle ingredientArticle) {
        super(ingredientArticle);
        this.ingredientType = ingredientArticle.getIngredientType();
    }
    /**
     * Return the {@link IngredientType} linked to the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @return the {@link IngredientType} linked to the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     */
    public IngredientType getIngredientType() {
        return ingredientType;
    }

    @Override
    public ArticleType getArticleType() {
        return ArticleType.INGREDIENT;
    }
}
