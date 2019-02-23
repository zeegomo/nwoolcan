package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.utils.Result;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link BatchReview} builder.
 * Handles all error checking for the construction of a new {@link BatchReview}.
 */

public final class BatchReviewBuilder {
    private final Set<Evaluation> evaluations = new HashSet<>();
    private String reviewer;
    private String notes;
    private final BatchReviewType batchReviewType;

    /**
     * Return a collection of all available {@link BatchReviewType} types.
     *
     * @return a collection of all available Review types.
     */
    public static Collection<BatchReviewType> getAvailableBatchReviewTypes() {
        //ClassGraph
        return null;
    }

    /**
     * Create a {@link BatchReviewBuilder}.
     *
     * @param batchReviewType the {@link BatchReviewType} of this {@link BatchReview} (implements the Strategy pattern)
     */
    public BatchReviewBuilder(final BatchReviewType batchReviewType) {
        this.batchReviewType = batchReviewType;
    }

    /**
     * Add evaluation for a category with notes.
     *
     * @param type  category.
     * @param score score.
     * @param notes notes.
     * @return this.
     */
    public BatchReviewBuilder addEvaluation(final EvaluationType type, final int score, final String notes) {
        this.evaluations.add(new EvaluationImpl(type, score, Optional.of(notes)));
        return this;
    }

    /**
     * Add evaluation for a category without notes.
     *
     * @param type  category.
     * @param score score.
     * @return this.
     */
    public BatchReviewBuilder addEvaluation(final EvaluationType type, final int score) {
        this.evaluations.add(new EvaluationImpl(type, score, Optional.empty()));
        return this;
    }

    /**
     * Add notes for the review.
     *
     * @param notes notes.
     * @return this.
     */
    public BatchReviewBuilder addNotes(final String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Add reviewer for the review.
     *
     * @param reviewer reviewer.
     * @return this.
     */
    public BatchReviewBuilder addReviewer(final String reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    /**
     * Return a {@link Result} holding a {@link BatchReview} if everything went well, otherwise a {@link Result} holding an {@link Exception}.
     *
     * @return a {@link Result<BatchReview>}
     */
    public Result<BatchReview> build() {
        return Result.of(this)
            .require(BatchReviewBuilder::checkEvaluationsTypeValidity)
            .require(builder -> builder.evaluations
                .stream()
                .allMatch(cat -> cat.getScore() >= 0
                    && cat.getScore() <= cat.getEvaluationType().getMaxScore()))
            .map(builder -> new BatchReviewImpl(builder.batchReviewType,
                builder.evaluations,
                Optional.ofNullable(builder.reviewer),
                Optional.ofNullable(builder.notes)));
    }

    /**
     * Resets the builder but not the {@link BatchReviewType}.
     *
     * @return this
     */
    public BatchReviewBuilder reset() {
        this.evaluations.clear();
        this.notes = null;
        this.reviewer = null;
        return this;
    }

    private boolean checkEvaluationsTypeValidity() {
        Collection<EvaluationType> catTypes = this.evaluations
            .stream()
            .map(Evaluation::getEvaluationType)
            .collect(Collectors.toList());
        return catTypes.containsAll(this.batchReviewType.getCategories())
            && this.batchReviewType.getCategories().containsAll(catTypes);
    }
}
