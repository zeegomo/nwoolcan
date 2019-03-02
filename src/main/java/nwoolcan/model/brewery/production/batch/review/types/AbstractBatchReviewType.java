package nwoolcan.model.brewery.production.batch.review.types;

import nwoolcan.model.brewery.production.batch.review.BatchReviewType;
import nwoolcan.model.brewery.production.batch.review.EvaluationType;

import java.util.Collection;
import java.util.Collections;

/**
 * Abstract BatchReviewType.
 */
public abstract class AbstractBatchReviewType implements BatchReviewType {
    private final String name;
    private final Collection<EvaluationType> categories;

    AbstractBatchReviewType(final String name, final Collection<EvaluationType> categories) {
        this.name = name;
        this.categories = categories;
    }

    @Override
    public final Collection<EvaluationType> getCategories() {
        return Collections.unmodifiableCollection(this.categories);
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final int getMaxScore() {
        return this.categories
            .stream()
            .map(EvaluationType::getMaxScore)
            .reduce(0, Integer::sum);
    }
}
