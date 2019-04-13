package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.brewery.batch.step.parameter.ParameterType;

import java.util.Date;
import java.util.Set;

/**
 * Basic implementation of {@link Step} interface.
 * Package-private. Create instances using static factory {@link Steps}.
 */
final class BasicStep extends AbstractStep {

    /**
     * Basic constructor with step type and start date of the step.
     * Package-protected, to create a Step use utils class Steps.
     * @param stepType step's type.
     * @param startDate step's start date.
     */
    BasicStep(final StepType stepType, final Date startDate) {
        super(new ModifiableStepInfoImpl(stepType, startDate));
    }

    /**
     * Constructor only with step type and setting step's start date with date now.
     * Package-protected, to create a Step use utils class Steps.
     * @param stepType step's type.
     */
    BasicStep(final StepType stepType) {
        this(stepType, new Date());
    }

    @Override
    public Set<StepType> getNextStepTypes() {
        return StepHelper.getNextStepTypesOf(this.getStepInfo().getType());
    }

    @Override
    public Set<ParameterType> getParameterTypes() {
        return StepHelper.getPossibleParameterTypesOf(this.getStepInfo().getType());
    }

    @Override
    public String toString() {
        return "[BasicStepImpl] " + super.toString();
    }
}
