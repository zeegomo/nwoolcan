package nwoolcan.model.brewery.batch.review;

import nwoolcan.model.brewery.batch.review.types.BatchEvaluationScannerImpl;
import nwoolcan.model.brewery.batch.review.types.BatchEvaluationTypeScanner;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;


/**
 * {@link BatchEvaluation} builder.
 * Handles all error checking for the construction of a new {@link BatchEvaluation}.
 */

public final class BatchEvaluationBuilder {
    private static final String INVALID_CATEGORIES_MESSAGE = "Invalid categories";
    private static final BatchEvaluationTypeScanner SCANNER = new BatchEvaluationScannerImpl();

    @Nullable
    private String reviewer;
    @Nullable
    private String notes;

    /**
     * Return a collection of all available {@link BatchEvaluationType} types.
     *
     * @return a collection of all available Review types.
     */
    public static Result<Set<BatchEvaluationType>> getAvailableBatchEvaluationTypes() {
        return SCANNER.getAvailableBatchEvaluationTypes();
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
     * Return a {@link Result} holding a {@link BatchEvaluation} if everything went well, otherwise a
     * {@link Result} holding an {@link Exception}.
     * Automatically resets after use.
     *
     * @param type the {@link BatchEvaluationType} of this {@link BatchEvaluation}
     *             (implements the Strategy pattern)
     * @param categories the evaluations.
     * @return a {@link Result}
     */
    public Result<BatchEvaluation> build(final BatchEvaluationType type, final Set<Evaluation> categories) {
        return this.checkEvaluationsTypeValidity(type, categories)
                   .<BatchEvaluation>map(builder -> new BatchEvaluationImpl(type,
                       categories,
                       Optional.ofNullable(builder.reviewer),
                       Optional.ofNullable(builder.notes)))
                   .peek(batchEvaluation -> this.reset());
    }

    private void reset() {
        this.notes = null;
        this.reviewer = null;
    }

    private Result<BatchEvaluationBuilder> checkEvaluationsTypeValidity(final BatchEvaluationType type, final Set<Evaluation> evaluations) {
        Collection<EvaluationType> catTypes = evaluations
            .stream()
            .map(Evaluation::getEvaluationType)
            .collect(Collectors.toList());
        return Result.of(this)
                     .require(() -> catTypes.containsAll(type.getCategories())
                         && type.getCategories().containsAll(catTypes),
                         new IllegalArgumentException(INVALID_CATEGORIES_MESSAGE));
    }


    private static final class BatchEvaluationImpl extends EvaluationImpl implements BatchEvaluation {
        private final Set<Evaluation> categories;
        private final Optional<String> reviewer;

        //Package private
        private BatchEvaluationImpl(final BatchEvaluationType type, final Set<Evaluation> categories, final Optional<String> reviewer, final Optional<String> notes) {
            super(type, type.calculateScore().apply(categories), notes);
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

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            BatchEvaluationImpl that = (BatchEvaluationImpl) o;
            return categories.equals(that.categories)
                && reviewer.equals(that.reviewer);
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
