package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.Step;

/**
 * Batch.
 */
public interface Batch {
    BatchInfo getBatchInfo();
    /**
     * Return the current step.
     * @return a {@link Step}.
     */
    Step getCurrentStep();

}
