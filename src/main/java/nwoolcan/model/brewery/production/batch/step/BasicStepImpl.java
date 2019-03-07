package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.brewery.production.batch.step.utils.StepHelper;

import java.util.Date;
import java.util.Set;

/**
 * Basic implementation of {@link Step} interface.
 */
public final class BasicStepImpl extends AbstractStep {

    /**
     * Basic constructor with step type and start date of the step.
     * @param stepType step's type.
     * @param startDate step's start date.
     * @throws NullPointerException if step type or start date are null.
     */
    public BasicStepImpl(final StepType stepType, final Date startDate) {
        super(new StepInfoImpl(stepType, startDate));
    }

    /**
     * Constructor only with step type and setting step's start date with date now.
     * @param stepType step's type.
     * @throws NullPointerException if step type is null.
     */
    public BasicStepImpl(final StepType stepType) {
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
