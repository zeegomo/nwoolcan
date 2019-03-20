package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.Step;

/**
 * Batch.
 */
public interface Batch {
    /**
     * Return the current step.
     * @return a {@link Step}.
     */
    Step getCurrentStep();

}
