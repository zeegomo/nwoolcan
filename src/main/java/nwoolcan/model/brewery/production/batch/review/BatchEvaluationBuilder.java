package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.utils.Result;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * {@link BatchEvaluation} builder.
 * Handles all error checking for the construction of a new {@link BatchEvaluation}.
 */

public final class BatchEvaluationBuilder {
    private static final String INVALID_SCORE_MESSAGE = "Score for one or more categories is not valid";
    private static final String INVALID_CATEGORIES_MESSAGE = "Invalid categories";
    private static final String BUILDER_USED_MESSAGE = "This builder has already built";
    private static final BatchEvaluationScanner SCANNER = new BatchEvaluationScannerImpl();

    private final Set<Evaluation> evaluations = new HashSet<>();

    private String reviewer;
    private String notes;
    private boolean built;
    private BatchEvaluationType batchEvaluationType;

    /**
     * Return a collection of all available {@link BatchEvaluationType} types.
     *
     * @return a collection of all available Review types.
     */
    public static Result<Collection<BatchEvaluationType>> getAvailableBatchReviewTypes() {
        return SCANNER.getAvailableBatchReviewTypes();
    }

    /**
     * Create a {@link BatchEvaluationBuilder}.
     *
     * @param batchEvaluationType the {@link BatchEvaluationType} of this {@link BatchEvaluation}
     *                        (implements the Strategy pattern)
     */
    public BatchEvaluationBuilder(final BatchEvaluationType batchEvaluationType) {
        reset(batchEvaluationType);
    }

    /**
     * Add evaluation for a category with notes.
     *
     * @param type  category.
     * @param score score.
     * @param notes notes.
     * @return this.
     */
    public BatchEvaluationBuilder addEvaluation(final EvaluationType type, final int score, final String notes) {
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
    public BatchEvaluationBuilder addEvaluation(final EvaluationType type, final int score) {
        this.evaluations.add(new EvaluationImpl(type, score, Optional.empty()));
        return this;
    }

    /**
     * Add notes for the review.
     *
     * @param notes notes.
     * @return this.
     */
    public BatchEvaluationBuilder addNotes(final String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Add reviewer for the review.
     *
     * @param reviewer reviewer.
     * @return this.
     */
    public BatchEvaluationBuilder addReviewer(final String reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    /**
     * Return whether this builder is already built.
     * @return whether this builder is already built.
     */
    public boolean isBuilt() {
        return this.built;
    }

    /**
     * Return a {@link Result} holding a {@link BatchEvaluation} if everything went well, otherwise a
     * {@link Result} holding an {@link Exception}.
     *
     * @return a {@link Result< BatchEvaluation >}
     */
    public Result<BatchEvaluation> build() {
        return Result.of(this)
            .require(BatchEvaluationBuilder::checkEvaluationsTypeValidity,
                new IllegalArgumentException(INVALID_CATEGORIES_MESSAGE))
            .require(BatchEvaluationBuilder::checkScoreValidity,
                new IllegalAccessException(INVALID_SCORE_MESSAGE))
            .require(builder -> !builder.built,
                new IllegalStateException(BUILDER_USED_MESSAGE))
            .peek(builder -> builder.built = true)
            .map(builder -> new BatchEvaluationImpl(builder.batchEvaluationType,
                builder.evaluations,
                Optional.ofNullable(builder.reviewer),
                Optional.ofNullable(builder.notes)));
    }

    /**
     * Resets the builder.
     *
     * @param newType the {@link BatchEvaluationType} of the new review.
     * @return this
     */
    public BatchEvaluationBuilder reset(final BatchEvaluationType newType) {
        this.evaluations.clear();
        this.notes = null;
        this.reviewer = null;
        this.built = false;
        this.batchEvaluationType = newType;
        return this;
    }

    private boolean checkEvaluationsTypeValidity() {
        Collection<EvaluationType> catTypes = this.evaluations
            .stream()
            .map(Evaluation::getEvaluationType)
            .collect(Collectors.toList());
        return catTypes.containsAll(this.batchEvaluationType.getCategories())
            && this.batchEvaluationType.getCategories().containsAll(catTypes);
    }

    private boolean checkScoreValidity() {
        return this.evaluations
            .stream()
            .allMatch(cat -> cat.getScore() >= 0
                && cat.getScore() <= cat.getEvaluationType().getMaxScore());
    }
}
