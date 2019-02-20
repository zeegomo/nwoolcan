package nwoolcan.model.brewery.production.batch.review;

import java.util.Objects;
import java.util.Optional;

/**
 * CategoryEvaluation.
 */
public final class CategoryEvaluationImpl implements CategoryEvaluation {
    private final CategoryEvaluationType category;
    private final int score;
    private final Optional<String> notes;

    CategoryEvaluationImpl(final CategoryEvaluationType evaluationCategory, final int score, final Optional<String> notes) {
        this.category = evaluationCategory;
        this.score = score;
        this.notes = notes;
    }

    @Override
    public CategoryEvaluationType getCategory() {
        return this.category;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getMaxScore() {
        return category.getMaxScore();
    }

    @Override
    public Optional<String> getNotes() {
        return notes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof CategoryEvaluation) {
            CategoryEvaluation that = (CategoryEvaluation) o;
            return score == that.getScore()
                && Objects.equals(category, that.getCategory());
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(category, score);
    }
}
