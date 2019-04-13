package nwoolcan.model.brewery.batch.step;

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
     * Step representing a finalized batch.
     */
    FINALIZED,
    /**
     * Step representing a stocked batch.
     */
    STOCKED;

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public boolean isEndType() {
        return equals(StepTypeEnum.FINALIZED) || equals(StepTypeEnum.STOCKED);
    }
}
