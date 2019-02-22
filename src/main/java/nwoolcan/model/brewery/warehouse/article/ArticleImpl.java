package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.utils.Result;

import java.util.Objects;

/**
 * General implementation of Article. It can contains only article of MISC type.
 * Override your class for a particular Article implementation.
 */
public class ArticleImpl implements Article {

    private final Integer id;
    private final String name;

    /**
     * Constructor of the class. Only article of type miscellaneous can be constructed.
     * @param id the id of the new Article.
     * @param name the name of the new Article.
     */
    public ArticleImpl(final Integer id, final String name) {
        this.id = Objects.requireNonNull(id, "Id can not be null.");
        this.name = Objects.requireNonNull(name, "Name can not be null.");
        if (this.name.equals("")) {
            throw new IllegalArgumentException("Name can not be empty.");
        }
        if (this.id <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero.");
        }
    }

    @Override
    public final Integer getId() {
        return this.id;
    }
    /**
     * Returns the type of article.
     * Override this method according on the type of article which is being represented.
     * @return the type of article.
     */
    @Override
    public ArticleType getArticleType() {
        return ArticleType.MISC;
    }

    @Override
    public final String getName() {
        return this.name;
    }
    /**
     * To override this method return the linked IngredientArticle in case it is an ingredient,
     * or an error {@link Result} otherwise.
     * @return an error {@link Result} because this is not an Ingredient, this is a general article.
     */
    @Override
    public Result<IngredientArticle> toIngredientArticle() {
        return Result.error(new IllegalAccessException("This is a general Article, not an"
            + "Ingredient."));
    }
    /**
     * To override this method return the linked BeerArticle in case it is an ingredient,
     * or an error {@link Result} otherwise.
     * @return an error {@link Result} because this is not a Beer, this is a general article.
     */
    @Override
    public Result<BeerArticle> toBeerArticle() {
        return Result.error(new IllegalAccessException("This is a general Article, not a"
            + "Beer."));
    }

}
