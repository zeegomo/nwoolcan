package nwoolcan.model.brewery.batch.review.types;

import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Dummy Review for testing.
 */
public enum DummyEvaluation implements BatchEvaluationType {
    /**
     * Test only.
     */
    DUMMY_REVIEW;
    enum DummyReviewCategories implements EvaluationType {
        CAT1("CAT1", 10),
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

    @Override
    public Set<EvaluationType> getCategories() {
        return new HashSet<>(Arrays.asList(DummyReviewCategories.values()));
    }

    @Override
    public Function<Set<Evaluation>, Integer> calculateScore() {
        return cat -> cat.stream().map(Evaluation::getScore).reduce(0, Integer::sum);
    }

    @Override
    public String getName() {
        return "DUMMY";
    }

    @Override
    public int getMaxScore() {
        return 10 + 2;
    }
}
