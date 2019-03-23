package nwoolcan.model.brewery.production.batch.step;

/**
 * Enum for describing the various steps that a beer has to go through production.
 */
public enum StepTypeEnum implements StepType {
    /**
     * Mashing step.
     */
    Mashing,
    /**
     * Boiling step.
     */
    Boiling,
    /**
     * Fermenting step.
     */
    Fermenting,
    /**
     * Packaging step.
     */
    Packaging,
    /**
     * Aging step.
     */
    Aging,
    /**
     * Placeholder step representing a finalized batch.
     */
    Finalized;

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public boolean isEndType() {
        return equals(StepTypeEnum.Finalized);
    }
}
