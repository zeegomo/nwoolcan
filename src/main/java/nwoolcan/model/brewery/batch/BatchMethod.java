package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.batch.step.StepTypeEnum;
import nwoolcan.utils.StringUtils;

/**
 * Batch production methods.
 */
public enum BatchMethod {
    /**
     * All grain.
     */
    ALL_GRAIN("All grain", StepTypeEnum.MASHING),
    /**
     * Partial mash.
     */
    PARTIAL_MASH("Partial Mash", StepTypeEnum.MASHING),
    /**
     * Extract.
     */
    EXTRACT("Exctract", StepTypeEnum.BOILING);

    private final String name;
    private final StepTypeEnum initialStep;

    BatchMethod(final String name, final StepTypeEnum initialStep) {
        this.name = name;
        this.initialStep = initialStep;
    }
    /**
     * Return a {@link String} representation of the name of the method.
     * @return a {@link String}.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the initial {@link StepType} of the {@link BatchMethod}.
     * @return the initial {@link StepType} of the {@link BatchMethod}.
     */
    public StepType getInitialStep() {
        return this.initialStep;
    }

    @Override
    public String toString() {
        return StringUtils.underscoreSeparatedToHuman(this.name());
    }
}
