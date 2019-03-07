package nwoolcan.model.brewery.production.batch.review.types;

import nwoolcan.model.brewery.production.batch.review.EvaluationType;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Objects;
/**
 * BJCPBatchEvaluationType.
 */
public final class BJCPBatchEvaluationType extends AbstractBatchEvaluationType {
    private static final String NAME = "Official BJCP Scoresheet";

    /**
     * BJCPCategories.
     */
    public enum BJCPCategories implements EvaluationType {
        /**
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

    /**
     * Create a new BJCP BatchEvaluationType.
     */
    public BJCPBatchEvaluationType() {
        super(BJCPBatchEvaluationType.NAME, new HashSet<>(Arrays.asList(BJCPCategories.values())));
    }

    @Override
    public boolean equals(final Object o) {
        return (o instanceof BJCPBatchEvaluationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NAME, Arrays.hashCode(this.getCategories().toArray()));
    }

    @Override
    public String toString() {
        return "[BJCPBatchReviewType]" + super.toString();
    }

}
