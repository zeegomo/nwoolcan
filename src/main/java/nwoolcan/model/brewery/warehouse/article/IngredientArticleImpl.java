package nwoolcan.model.brewery.warehouse.article;

import java.util.Objects;

import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;

/**
 * Ingredient Article implementation.
 */
public final class IngredientArticleImpl extends ArticleImpl implements IngredientArticle {

    private final IngredientType ingredientType;

    /**
     * Constructor of the class IngredientArticleImpl.
     * @param name the name of the ingredient article.
     * @param unitOfMeasure used for this article.
     * @param ingredientType the type of ingredient article.
     */
    public IngredientArticleImpl(final String name,
                          final UnitOfMeasure unitOfMeasure,
                          final IngredientType ingredientType) {
        super(name, unitOfMeasure);
        this.ingredientType = ingredientType;
    }
    /**
     * Returns the {@link Result} of this {@link IngredientArticle}.
     * @return the {@link Result} of this {@link IngredientArticle}.
     */
    @Override
    public Result<IngredientArticle> toIngredientArticle() {
        return Result.of(this);
    }

    @Override
    public ArticleType getArticleType() {
        return ArticleType.INGREDIENT;
    }

    @Override
    public IngredientType getIngredientType() {
        return this.ingredientType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ingredientType);
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj) && this.ingredientType == ((IngredientArticleImpl) obj).getIngredientType();
    }

    @Override
    public String toString() {
        return "[IngredientArticleImpl] { "
            + "Id:" + getId() + ", "
            + "Name:" + getName() + ", "
            + "IngredientType:" + getIngredientType().toString() + ", "
            + "UnitOfMeasure:" + getUnitOfMeasure()
            + "}";
    }

}
