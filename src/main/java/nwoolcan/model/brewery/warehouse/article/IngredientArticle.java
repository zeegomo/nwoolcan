package nwoolcan.model.brewery.warehouse.article;

/**
 * IngredientArticle.
 */
public interface IngredientArticle extends Article {

    /**
     * Returns the {@link IngredientType} of the current IngredientArticle.
     * @return the {@link IngredientType} of the current IngredientArticle.
     */
    IngredientType getIngredientType();

}
