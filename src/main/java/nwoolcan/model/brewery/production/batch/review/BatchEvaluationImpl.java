package nwoolcan.model.brewery.production.batch.review;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link BatchEvaluation}.
 */
public final class BatchEvaluationImpl extends EvaluationImpl implements BatchEvaluation {
    private final Collection<Evaluation> categories;
    private final Optional<String> reviewer;

    BatchEvaluationImpl(final BatchEvaluationType type, final Collection<Evaluation> categories, final Optional<String> reviewer, final Optional<String> notes) {
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
    public Collection<Evaluation> getCategoryEvaluations() {
        return Collections.unmodifiableCollection(this.categories);
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
