package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.utils.Result;

/**
 * Article.
 */
public interface Article {
    /**
     * Returns the Id of the current article.
     * @return the Id of the current article.
     */
    Integer getId();

    /**
     * Returns the {@link ArticleType} linked to the Article.
     * @return the {@link ArticleType} linked to the current Article.
     */
    ArticleType getArticleType();

    /**
     * Returns the denomination of the current Article.
     * @return the denomination of the current Article.
     */
    String getName();

    /**
     * If the current {@link ArticleType} is {@link ArticleType}.INGREDIENT returns the casted object.
     * @return a {@link Result} of {@link IngredientArticle} casting the current Article.
     */
    Result<IngredientArticle> toIngredientArticle();

    /**
     * If the current {@link ArticleType} is {@link ArticleType}.FINISHED_BEER returns the casted object.
     * @return a {@link Result} of {@link BeerArticle} casting the current Article.
     */
    Result<BeerArticle> toBeerArticle();
}
