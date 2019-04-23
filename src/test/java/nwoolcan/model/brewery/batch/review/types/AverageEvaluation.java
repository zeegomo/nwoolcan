package nwoolcan.model.brewery.batch.review.types;

import nwoolcan.model.brewery.batch.review.EvaluationType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * test class.
 */
public class AverageEvaluation extends AbstractSimpleBatchEvaluationType {
    /**
     * Test enum.
     */
    public enum DummyReviewCategories implements EvaluationType {
        /**
         * Test.
         */
        CAT1("CAT1", 10),
        /**
         * Test.
         */
        CAT2("CAT2", 2);

        private final String name;
        private final int score;

        DummyReviewCategories(final String name, final int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getMaxScore() {
            return this.score;
        }
    }
    /**
     * Provide evaluation name, categories and a scoreFunction to calculate maximum possible score.
     */
    public AverageEvaluation() {
        super("test", new HashSet<>(Arrays.asList(DummyReviewCategories.values())));
    }

    @Override
    protected final int accumulate(final Collection<Integer> scores) {
        return scores.stream().reduce(0, Integer::sum) / scores.size();
    }
}
