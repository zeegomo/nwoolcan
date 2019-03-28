package nwoolcan.model.brewery.production.batch.step;

/**
 * Enum for describing the various steps that a beer has to go through production.
 */
public enum StepTypeEnum implements StepType {
    /**
     * Mashing step.
     */
    MASHING,
    /**
     * Boiling step.
     */
    BOILING,
    /**
     * Fermenting step.
     */
    FERMENTING,
    /**
     * Packaging step.
     */
    PACKAGING,
    /**
     * Aging step.
     */
    AGING,
    /**
     * Placeholder step representing a finalized batch.
     */
    FINALIZED;

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public boolean isEndType() {
        return equals(StepTypeEnum.FINALIZED);
    }
}
