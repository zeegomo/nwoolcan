package nwoolcan.model.brewery.production.batch.review;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * BJCPBatchReviewType.
 */
public final class BJCPBatchReviewType implements BatchReviewType {
    private static final String NAME = "Official BJCP Scoresheet";
    private static final int MAX_SCORE = 50;

    /**
     * BJCPCategories.
     */
    public enum BJCPCategories implements EvaluationType {
        /**
         * }
         * Aroma.
         */
        AROMA(12, "Aroma"),
        /**
         * Appearance.
         */
        APPEARANCE(3, "Appearance"),
        /**
         * Flavor.
         */
        FLAVOR(20, "Flavor"),
        /**
         * Mouthfeel.
         */
        MOUTHFEEL(5, "Mouthfeel"),
        /**
         * Overall Impression.
         */
        OVERALL_IMPRESSION(10, "Overall Impression");

        private final int maxScore;
        private final String name;

        BJCPCategories(final int maxScore, final String name) {
            this.maxScore = maxScore;
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getMaxScore() {
            return this.maxScore;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getMaxScore() {
        return MAX_SCORE;
    }

    @Override
    public Collection<EvaluationType> getCategories() {
        return Collections.unmodifiableCollection(
            Arrays.asList(BJCPCategories.values()));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchReviewType)) {
            return false;
        }
        BatchReviewType that = (BatchReviewType) o;
        return MAX_SCORE == that.getMaxScore()
            && Objects.equals(NAME, that.getName())
            && Objects.equals(this.getCategories(), that.getCategories());
    }

    @Override
    public int hashCode() {
        return Objects.hash(NAME, MAX_SCORE, this.getCategories());
    }

}
