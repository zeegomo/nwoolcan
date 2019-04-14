package nwoolcan.model.brewery.batch.review.types;

import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.EvaluationType;

import java.util.Set;
import java.util.Collections;

/**
 * Abstract BatchEvaluationType.
 */
public abstract class AbstractBatchEvaluationType implements BatchEvaluationType {
    private final String name;
    private final Set<EvaluationType> categories;

    //Package private
    AbstractBatchEvaluationType(final String name, final Set<EvaluationType> categories) {
        this.name = name;
        this.categories = categories;
    }

    @Override
    public final Set<EvaluationType> getCategories() {
        return Collections.unmodifiableSet(this.categories);
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
    /**
     * Add the class name formatter like \"[DummyReviewType]\" before this method when overriding.
     * @return the string representation of this class.
     */
    @Override
    public String toString() {
        return " { "
            + "name: " + this.name + ", "
            + "maxScore: " + this.getMaxScore() + " "
            + "}";
    }
}
