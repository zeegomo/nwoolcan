package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.types.BatchEvaluationTypeScanner;
import nwoolcan.model.brewery.production.batch.review.types.BatchEvaluationScannerImpl;
import nwoolcan.utils.Result;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;


/**
 * {@link BatchEvaluation} builder.
 * Handles all error checking for the construction of a new {@link BatchEvaluation}.
 */

public final class BatchEvaluationBuilder {
    private static final String INVALID_SCORE_MESSAGE = "Score for one or more categories is not valid";
    private static final String INVALID_CATEGORIES_MESSAGE = "Invalid categories";
    private static final String BUILDER_USED_MESSAGE = "This builder has already built";
    private static final BatchEvaluationTypeScanner SCANNER = new BatchEvaluationScannerImpl();

    private final Set<Evaluation> evaluations;

    @Nullable
    private String reviewer;
    @Nullable
    private String notes;
    private boolean built;
    private BatchEvaluationType batchEvaluationType;

    /**
     * Return a collection of all available {@link BatchEvaluationType} types.
     *
     * @return a collection of all available Review types.
     */
    public static Result<Set<BatchEvaluationType>> getAvailableBatchEvaluationTypes() {
        return SCANNER.getAvailableBatchEvaluationTypes();
    }
    /**
     * Create a {@link BatchEvaluationBuilder}.
     *
     * @param batchEvaluationType the {@link BatchEvaluationType} of this {@link BatchEvaluation}
     *                        (implements the Strategy pattern)
     * @param evaluations the evaluations.
     */
    public BatchEvaluationBuilder(final BatchEvaluationType batchEvaluationType, final Set<Evaluation> evaluations) {
        this.batchEvaluationType = batchEvaluationType;
        this.evaluations = new HashSet<>(evaluations);
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
     * Return whether this builder can build another instance or have to reset.
     * @return true if this builder can build, false otherwise.
     */
    public boolean canBuild() {
        return !this.built;
    }
    /**
     * Return a {@link Result} holding a {@link BatchEvaluation} if everything went well, otherwise a
     * {@link Result} holding an {@link Exception}.
     *
     * @return a {@link Result}
     */
    public Result<BatchEvaluation> build() {
        return Result.of(this)
            .require(BatchEvaluationBuilder::checkEvaluationsTypeValidity,
                new IllegalArgumentException(INVALID_CATEGORIES_MESSAGE))
            .require(BatchEvaluationBuilder::canBuild,
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
     * @param evaluations the {@link Evaluation} set for this review.
     * @return this
     */
    public BatchEvaluationBuilder reset(final BatchEvaluationType newType, final Set<Evaluation> evaluations) {
        this.evaluations.clear();
        this.evaluations.addAll(evaluations);
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


    private static final class BatchEvaluationImpl extends EvaluationImpl implements BatchEvaluation {
        private final Set<Evaluation> categories;
        private final Optional<String> reviewer;

        //Package private
        private BatchEvaluationImpl(final BatchEvaluationType type, final Set<Evaluation> categories, final Optional<String> reviewer, final Optional<String> notes) {
            super(type, categories.stream().map(Evaluation::getScore).reduce(0, Integer::sum), notes);
            this.categories = categories;
            this.reviewer = reviewer;
        }

        @Override
        public BatchEvaluationType getEvaluationType() {
            return (BatchEvaluationType) super.getEvaluationType();
        }

        @Override
        public Optional<String> getReviewer() {
            return this.reviewer;
        }

        @Override
        public Set<Evaluation> getCategoryEvaluations() {
            return Collections.unmodifiableSet(this.categories);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof BatchEvaluationImpl)) {
                return false;
            }
            BatchEvaluationImpl that = (BatchEvaluationImpl) o;
            return this.getScore() == that.getScore()
                && Objects.equals(categories, that.getCategoryEvaluations())
                && Objects.equals(this.getNotes(), that.getNotes())
                && Objects.equals(reviewer, that.getReviewer())
                && Objects.equals(this.getEvaluationType(), that.getEvaluationType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(categories, reviewer, super.hashCode());
        }

        @Override
        public String toString() {
            return "[BatchEvaluation] {"
                + "score: " + this.getScore() + ", "
                + "maxScore: " + this.getEvaluationType().getMaxScore() + ", "
                + "type: " + this.getEvaluationType().toString() + " "
                + "}";
        }
    }

}
