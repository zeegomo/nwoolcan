package nwoolcan.model.brewery.production.batch.step;

/**
 * Enum for describing the various steps that a beer has to go through production.
 */
public enum StepType {
    /**
     * Classic steps.
     */
    Mashing, Boiling, Fermenting, Packaging, Aging, Finalized;
}
