package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.utils.Result;

import java.util.Objects;

/**
 * Beer article implementation.
 */
public final class BeerArticleImpl extends ArticleImpl implements BeerArticle {

    private final Batch batch;

    /**
     * Constructor which uses the constructor of the super class and sets the batch.
     * @param id the id of the beer article.
     * @param name the name of the beer article.
     * @param batch the batch linked to the beer article.
     */
    public BeerArticleImpl(final Integer id, final String name, final Batch batch) {
        super(id, name);
        this.batch = Objects.requireNonNull(batch);
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
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BeerArticleImpl)) {
            return false;
        }

        BeerArticleImpl other = (BeerArticleImpl) obj;

        return this.batch.equals(other.getBatch())
            && this.getName().equals(other.getName())
            && this.getId().equals(other.getId());
    }

    @Override
    public String toString() {
        return "[BeerArticleImpl] { "
            + "Id:" + getId() + ", "
            + "Name:" + getName() + ", "
            + "Batch:" + getBatch().toString() + " "
            + "}";
    }

}
