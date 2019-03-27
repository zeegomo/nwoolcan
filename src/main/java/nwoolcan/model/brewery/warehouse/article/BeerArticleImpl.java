package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;

import java.util.Objects;

/**
 * Beer article implementation.
 */
public final class BeerArticleImpl extends ArticleImpl implements BeerArticle {

    private final Batch batch;

    /**
     * Constructor which uses the constructor of the super class and sets the batch.
     * @param name the name of the beer article.
     * @param unitOfMeasure used for this article.
     * @param batch the batch linked to the beer article.
     */
    //Package protected
    BeerArticleImpl(final String name, final UnitOfMeasure unitOfMeasure, final Batch batch) {
        super(name, unitOfMeasure);
        this.batch = batch;
    }
    /**
     * Returns the {@link Result} of this {@link BeerArticle}.
     * @return the {@link Result} of this {@link BeerArticle}.
     */
    @Override
    public Result<BeerArticle> toBeerArticle() {
        return Result.of(this);
    }

    @Override
    public ArticleType getArticleType() {
        return ArticleType.FINISHED_BEER;
    }

    @Override
    public Batch getBatch() {
        return this.batch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), batch);
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj) && this.batch.equals(((BeerArticle) obj).getBatch());
    }

    @Override
    public String toString() {
        return "[BeerArticleImpl] { "
            + "Id:" + getId() + ", "
            + "Name:" + getName() + ", "
            + "UnitOfMeasure:" + getUnitOfMeasure() + ", "
            + "Batch:" + getBatch()
            + "}";
    }

}
