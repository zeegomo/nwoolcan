package nwoolcan.model.brewery.batch.review.types;

import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationType;

import java.util.Collection;
import java.util.Set;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract BatchEvaluationType where each type count the same in the final evaluation.
 */
public abstract class AbstractSimpleBatchEvaluationType implements BatchEvaluationType {
    private final String name;
    private final Set<EvaluationType> categories;
    //Package private

    /**
     * Provide evaluation name, categories and a scoreFunction to calculate maximum possible score.
     * @param name the name of the evaluation type.
     * @param categories the categories.
     */
    AbstractSimpleBatchEvaluationType(final String name,
                                final Set<EvaluationType> categories) {
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
        return this.accumulate(this.categories.stream().map(EvaluationType::getMaxScore).collect(Collectors.toList()));
    }

    @Override
    public final Function<Set<Evaluation>, Integer> calculateScore() {
        return cat -> accumulate(cat.stream().map(Evaluation::getScore).collect(Collectors.toList()));
    }
    /**
     * Override this function to provide how to calculate the final score based on intermediate evaluations.
     * @param scores the scores of the intermediate evaluations.
     * @return the final score.
     */
    protected abstract int accumulate(Collection<Integer> scores);
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
