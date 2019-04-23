package nwoolcan.model.brewery.batch.review.types;

import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.utils.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Objects;

/**
 * BJCPBatchEvaluationType.
 */
public final class BJCPBatchEvaluationType extends AbstractSimpleBatchEvaluationType {
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

        @Override
        public String toString() {
            return StringUtils.underscoreSeparatedToHuman(this.name());
        }
    }

    /**
     * Create a new BJCP BatchEvaluationType.
     */
    BJCPBatchEvaluationType() {
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
    protected int accumulate(final Collection<Integer> scores) {
        return scores.stream().reduce(0, Integer::sum);
    }

    @Override
    public String toString() {
        return "[BJCPBatchReviewType]" + super.toString();
    }

}
