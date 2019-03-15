package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.brewery.production.batch.step.info.StepInfoImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterType;
import nwoolcan.model.brewery.production.batch.step.utils.StepHelper;

import java.util.Date;
import java.util.Set;

/**
 * Basic implementation of {@link Step} interface.
 */
public final class BasicStep extends AbstractStep {

    /**
     * Basic constructor with step type and start date of the step.
     * Package-protected, to create a Step use utils class Steps.
     * @param stepType step's type.
     * @param startDate step's start date.
     */
    BasicStep(final StepType stepType, final Date startDate) {
        super(new StepInfoImpl(stepType, startDate));
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
