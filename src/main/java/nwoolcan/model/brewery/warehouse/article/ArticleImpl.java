package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;

import java.util.Objects;

/**
 * General implementation of Article. It can contains only article of MISC type.
 * Override your class for a particular Article implementation.
 */
public class ArticleImpl implements Article {

    private final Integer id;
    private final String name;
    private final UnitOfMeasure unitOfMeasure;
    private final ArticleIdManager idManager = ArticleIdManager.getInstance();

    /**
     * Constructor of the class. Only article of type miscellaneous can be constructed.
     * @param name the name of the new Article.
     * @param unitOfMeasure used for this article.
     */
    public ArticleImpl(final String name, final UnitOfMeasure unitOfMeasure) {
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        if (this.name.equals("")) {
            throw new IllegalArgumentException("Name can not be empty.");
        }
        this.id = idManager.getId(name, getArticleType(), unitOfMeasure);
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
        return Result.error(new IllegalAccessException("This is not Ingredient Article."));
    }
    /**
     * To override this method return the linked BeerArticle in case it is an ingredient,
     * or an error {@link Result} otherwise.
     * @return an error {@link Result} because this is not a Beer, this is a general article.
     */
    @Override
    public Result<BeerArticle> toBeerArticle() {
        return Result.error(new IllegalAccessException("This is not a Beer Article"));
    }

    @Override
    public final UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    /**
     * To override this method you should call Objects.hash with parameters this super class and the other fields.
     * @return the result of the xor operation between id and the hash of the name.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, unitOfMeasure, getArticleType());
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

        if (!(obj instanceof ArticleImpl)) {
            return false;
        }

        ArticleImpl other = (ArticleImpl) obj;
        return this.name.equals(other.getName())
            && this.unitOfMeasure.equals(other.unitOfMeasure)
            && this.getArticleType().equals(other.getArticleType());
    }

    /**
     * To override this method add also other elements of the new class.
     * @return a string representation of the class.
     */
    @Override
    public String toString() {
        return "ArticleImpl{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", unitOfMeasure=" + unitOfMeasure
            + ", articleType=" + getArticleType()
            + '}';
    }
}
