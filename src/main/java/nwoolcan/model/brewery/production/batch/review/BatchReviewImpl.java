package nwoolcan.model.brewery.production.batch.review;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * BatchReviewImpl.
 */
public final class BatchReviewImpl implements BatchReview {
    private final Collection<Evaluation> categories;
    private final int score;
    private final Optional<String> notes;
    private final Optional<String> reviewer;
    private final BatchReviewType type;

    BatchReviewImpl(final BatchReviewType type, final Collection<Evaluation> categories, final Optional<String> reviewer, final Optional<String> notes) {
        this.score = categories.stream().map(Evaluation::getScore).reduce(0, Integer::sum);
        this.categories = categories;
        this.notes = notes;
        this.reviewer = reviewer;
        this.type = type;
    }

    @Override
    public BatchReviewType getEvaluationType() {
        return this.type;
    }

    @Override
    public Optional<String> getReviewer() {
        return this.reviewer;
    }

    @Override
    public Collection<Evaluation> getCategoryEvaluations() {
        return Collections.unmodifiableCollection(this.categories);
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public Optional<String> getNotes() {
        return this.notes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchReview)) {
            return false;
        }
        BatchReview that = (BatchReview) o;
        return score == that.getScore()
            && Objects.equals(categories, that.getCategoryEvaluations())
            && Objects.equals(notes, that.getNotes())
            && Objects.equals(reviewer, that.getReviewer())
            && Objects.equals(type, that.getEvaluationType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories, score, notes, reviewer, type);
    }
}
