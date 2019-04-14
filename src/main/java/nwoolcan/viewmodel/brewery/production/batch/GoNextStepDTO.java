package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.utils.Quantity;

import javax.annotation.Nullable;

/**
 * Class representing a DTO to go to next step in batch.
 */
public class GoNextStepDTO {

    private final StepType nextStepType;
    private final String notes;

    @Nullable
    private final Quantity endSize;

    /**
     * Basic constructor.
     * @param nextStepType next step type.
     * @param notes possible notes.
     * @param endSize possible end size.
     */
    public GoNextStepDTO(final StepType nextStepType, final String notes, @Nullable final Quantity endSize) {
        this.nextStepType = nextStepType;
        this.notes = notes;
        this.endSize = endSize;
    }

    /**
     * Returns the next step type.
     * @return the next step type.
     */
    public StepType getNextStepType() {
        return nextStepType;
    }

    /**
     * Returns the possible notes (empty if none).
     * @return the possible notes (empty if none).
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Returns the possible end size (null if none).
     * @return the possible end size (null if none).
     */
    @Nullable
    public Quantity getEndSize() {
        return endSize;
    }
}
