package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.step.StepType;

import java.util.Collections;
import java.util.Set;

/**
 * View model representing the view for going to next step in a batch.
 */
public final class GoNextStepViewModel {

    private final int batchId;
    private final Set<StepType> nextPossibleStepTypes;

    /**
     * Basic constructor with decorator-like pattern.
     * @param batch the batch to get data from.
     */
    public GoNextStepViewModel(final Batch batch) {
        this.batchId = batch.getId();
        this.nextPossibleStepTypes = Collections.unmodifiableSet(batch.getCurrentStep().getNextStepTypes());
    }

    /**
     * Returns the batch id reference.
     * @return the batch id reference.
     */
    public int getBatchId() {
        return this.batchId;
    }

    /**
     * Returns a set representing the next possible step types that can be after the current one.
     * @return a set representing the next possible step types that can be after the current one.
     */
    public Set<StepType> getNextPossibleStepTypes() {
        return this.nextPossibleStepTypes;
    }
}
