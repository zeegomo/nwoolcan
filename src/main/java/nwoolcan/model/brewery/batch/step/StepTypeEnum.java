package nwoolcan.model.brewery.batch.step;

import nwoolcan.utils.StringUtils;

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
    FINALIZED;

    @Override
    public String getName() {
        return StringUtils.underscoreSeparatedToHuman(this.name());
    }

    @Override
    public boolean isEndType() {
        return equals(StepTypeEnum.FINALIZED);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
