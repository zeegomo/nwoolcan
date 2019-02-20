package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.util.*;

/**
 * BJCP builder.
 */
public final class BJCPBatchReviewBuilder {
    private static final Set<CategoryEvaluationType> POSSIBLE_EVALUATION_CATEGORIES = new HashSet<>(Arrays.asList(BJCPBatchReview.BJCPEvaluationCategories.values()));
    private final Set<CategoryEvaluation> evaluations = new HashSet<>();
    private String reviewer;
    private Optional<String> notes;
    private boolean built = false;

    /**
     * Add evaluation for a category.
     * @param category category.
     * @param score score.
     * @param notes notes.
     */
    public Result<BJCPBatchReviewBuilder> addEvaluation(final CategoryEvaluationType category, final int score, final Optional<String> notes) {
        return Results.requireNonNull(category)
            .require(cat -> cat.getMaxScore() >= score && score >= 0)
            .require(POSSIBLE_EVALUATION_CATEGORIES::contains)
            .map(cat -> {
                this.evaluations.add(new CategoryEvaluationImpl(cat, score, notes));
                return this;
            });
    }
    /**
     * Add reviewer for the review.
     * @param reviewer reviewer
     */
    public Result<BJCPBatchReviewBuilder> addReviewer(final String reviewer) {
        return Results.requireNonNull(reviewer)
            .map(rev -> {
                this.reviewer = rev;
                return this;
            });
    }
    /**
     * Add notes for the review.
     * @param notes notes
     */
    public Result<BJCPBatchReviewBuilder> addNotes(final Optional<String> notes) {
        return Results.requireNonNull(notes)
            .map(n -> {
                this.notes = n;
                return this;
            });
    }
    /**
     * Return a {@link Result} holding a {@link BJCPBatchReview} if everything went well, otherwise a {@link Result} holding an Exception.
     * @return a {@link Result<BJCPBatchReview>}
     */
    public Result<BJCPBatchReview> build() {
        return Results.requireNonNull(reviewer)
            .map(r -> this)
            .require(builder -> builder.evaluations.size() == BJCPBatchReview.BJCPEvaluationCategories.values().length)
            .require(builder -> !built)
            .map(builder -> new BJCPBatchReview(builder.evaluations, builder.reviewer, builder.notes));
    }


}
