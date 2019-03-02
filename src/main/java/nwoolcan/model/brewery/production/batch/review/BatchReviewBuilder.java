package nwoolcan.model.brewery.production.batch.review;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link BatchReview} builder.
 * Handles all error checking for the construction of a new {@link BatchReview}.
 */

public final class BatchReviewBuilder {
    private static final String INVALID_SCORE_MESSAGE = "Score for one or more categories is not valid";
    private static final String INVALID_CATEGORIES_MESSAGE = "Invalid categories";
    private static final String BUILDER_USED_MESSAGE = "This builder has already built";

    private final Set<Evaluation> evaluations = new HashSet<>();
    private String reviewer;
    private String notes;
    private boolean built;
    private BatchReviewType batchReviewType;

    /**
     * Return a collection of all available {@link BatchReviewType} types.
     *
     * @return a collection of all available Review types.
     */
    public static Result<Collection<BatchReviewType>> getAvailableBatchReviewTypes() {
        return Results.ofCloseable(() ->  new ClassGraph().enableAllInfo().scan(), scanResult -> {
            ClassInfoList widgetClasses = scanResult.getClassesImplementing(BatchReviewType.class.getName());
            System.out.println(widgetClasses.size());
            return widgetClasses
                .loadClasses(BatchReviewType.class)
                .stream()
                .flatMap(review -> {
                    if (review.isEnum()) {
                        return Arrays.stream(review.getEnumConstants()).map(Result::of);
                    } else {
                        return Stream.of(Results.ofChecked(review::newInstance));
                    }
                })
                .filter(Result::isPresent)
                .map(Result::getValue)
                .collect(Collectors.toList());
        });
    }

    /**
     * Create a {@link BatchReviewBuilder}.
     *
     * @param batchReviewType the {@link BatchReviewType} of this {@link BatchReview}
     *                        (implements the Strategy pattern)
     */
    public BatchReviewBuilder(final BatchReviewType batchReviewType) {
        reset(batchReviewType);
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
     * Return whether this builder is already built.
     * @return whether this builder is already built.
     */
    public boolean isBuilt() {
        return this.built;
    }

    /**
     * Return a {@link Result} holding a {@link BatchReview} if everything went well, otherwise a
     * {@link Result} holding an {@link Exception}.
     *
     * @return a {@link Result<BatchReview>}
     */
    public Result<BatchReview> build() {
        return Result.of(this)
            .require(BatchReviewBuilder::checkEvaluationsTypeValidity,
                new IllegalArgumentException(INVALID_CATEGORIES_MESSAGE))
            .require(BatchReviewBuilder::checkScoreValidity,
                new IllegalAccessException(INVALID_SCORE_MESSAGE))
            .require(builder -> !builder.built,
                new IllegalStateException(BUILDER_USED_MESSAGE))
            .peek(builder -> builder.built = true)
            .map(builder -> new BatchReviewImpl(builder.batchReviewType,
                builder.evaluations,
                Optional.ofNullable(builder.reviewer),
                Optional.ofNullable(builder.notes)));
    }

    /**
     * Resets the builder.
     *
     * @param newType the {@link BatchReviewType} of the new review.
     * @return this
     */
    public BatchReviewBuilder reset(final BatchReviewType newType) {
        this.evaluations.clear();
        this.notes = null;
        this.reviewer = null;
        this.built = false;
        this.batchReviewType = newType;
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

    private boolean checkScoreValidity() {
        return this.evaluations
            .stream()
            .allMatch(cat -> cat.getScore() >= 0
                && cat.getScore() <= cat.getEvaluationType().getMaxScore());
    }
}
