package nwoolcan.view.model.warehouse.article;

import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.utils.UnitOfMeasure;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
 */
public final class IngredientArticleViewModel extends ArticleViewModel {

    private final IngredientType ingredientType;

    /**
     * Constructor of the view version of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param id of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param name of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param unitOfMeasure of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @param ingredientType of the {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     */
    public IngredientArticleViewModel(final int id,
                                      final String name,
                                      final UnitOfMeasure unitOfMeasure,
                                      final IngredientType ingredientType) {
        super(id, name, unitOfMeasure);
        this.ingredientType = ingredientType;
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
