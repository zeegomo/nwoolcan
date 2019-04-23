package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;

import java.util.Objects;

// Package-Private
abstract class AbstractArticle implements Article {

    private String name;
    private final int id;
    private final UnitOfMeasure unitOfMeasure;
    private static final String NOT_INGREDIENT_ARTICLE = "This is not an Ingredient Article.";
    private static final String NOT_BEER_ARTICLE = "This is not a Beer Article.";

    AbstractArticle(final int id, final String name, final UnitOfMeasure unitOfMeasure) {
        this.id = id;
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public final int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public final UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }
    /**
     * Package private setter for the name of the {@link Article}.
     * @param newName the new name of the {@link Article}.
     * @return this for fluency.
     */
    // Package-Private
    protected AbstractArticle setName(final String newName) {
        this.name = newName;
        return this;
    }
    /**
     * To override this method return the linked IngredientArticle in case it is an ingredient,
     * or an error {@link Result} otherwise.
     * @return an error {@link Result} because this is not an Ingredient, this is a general article.
     */
    @Override
    public Result<IngredientArticle> toIngredientArticle() {
        return Result.error(new IllegalAccessException(NOT_INGREDIENT_ARTICLE));
    }
    /**
     * To override this method return the linked BeerArticle in case it is an ingredient,
     * or an error {@link Result} otherwise.
     * @return an error {@link Result} because this is not a Beer, this is a general article.
     */
    @Override
    public Result<BeerArticle> toBeerArticle() {
        return Result.error(new IllegalAccessException(NOT_BEER_ARTICLE));
    }
    /**
     * To override this method you should call Objects.hash with parameters this super class and the other fields.
     * @return the result of the xor operation between id and the hash of the name.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUnitOfMeasure(), getArticleType());
    }
    /**
     * To override this method compare all the fields of the classes.
     * @param obj the object to be compared with.
     * @return true if all the fields contains respectively the same value.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AbstractArticle)) {
            return false;
        }

        AbstractArticle other = (AbstractArticle) obj;
        return getName().equals(other.getName())
            && getUnitOfMeasure().equals(other.getUnitOfMeasure())
            && this.getArticleType().equals(other.getArticleType());
    }

}
