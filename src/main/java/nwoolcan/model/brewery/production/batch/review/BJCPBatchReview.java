package nwoolcan.model.brewery.production.batch.review;

import java.util.*;

/**
 * Official BJCP Review template.
 */
public final class BJCPBatchReview implements BatchReview {
    private static final String NAME = "Official BJCP Scoresheet";
    private static final int MAX_SCORE = 50;

    private final Collection<CategoryEvaluation> categories;
    private final int score;
    private final Optional<String> notes;
    private final String reviewer;

    BJCPBatchReview(final Collection<CategoryEvaluation> categories, final String reviewer, final Optional<String> notes) {
        this.score = categories.stream().map(CategoryEvaluation::getScore).reduce(0, (a,b) -> a+b);
        this.categories = categories;
        this.notes = notes;
        this.reviewer = reviewer;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getReviewer() {
        return this.reviewer;
    }

    @Override
    public Collection<CategoryEvaluation> getCategoryEvaluations() {
        return Collections.unmodifiableCollection(this.categories);
    }

    @Override
    public Collection<CategoryEvaluationType> getCategories() {
        return Collections.unmodifiableCollection(
            new ArrayList<>(Arrays.asList(BJCPCategoryEvaluationType.values()))
        );
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getMaxScore() {
        return MAX_SCORE;
    }

    @Override
    public Optional<String> getNotes() {
        return this.notes;
    }

    /**
     * BJCP Categories.
     */
    public enum BJCPCategoryEvaluationType implements CategoryEvaluationType {
        /**
         * Aroma.
         */
        AROMA("Aroma", 12),
        /**
         * Appearance.
         */
        APPEARANCE("Appearance", 3),
        /**
         * Flavor.
         */
        FLAVOR("Flavor", 20),
        /**
         * Mouthfeel.
         */
        MOUTHFEEL("Mouthfeel", 5),
        /**
         * Overall impression.
         */
        OVERALL_IMPRESSION("Overall Impression", 10);

        private final String name;
        private final int maxScore;

        BJCPCategoryEvaluationType(final String name, final int maxScore) {
            this.name = name;
            this.maxScore = maxScore;
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
}
