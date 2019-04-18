package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.brewery.batch.step.parameter.ParameterType;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Basic implementation of {@link Step} interface.
 * Package-private.
 * Create instances of this class using the factory {@link BasicStepFactory}.
 */
final class BasicStep extends AbstractStep {

    private final Set<StepType> nextStepTypes;
    private final Set<ParameterType> parameterTypes;

    /**
     * Basic constructor with step type and start date of the step.
     * Package-protected, to create a Step use use the factory {@link BasicStepFactory}.
     * @param stepType step's type.
     * @param startDate step's start date.
     */
    BasicStep(final StepType stepType,
              final Date startDate,
              final Set<StepType> nextStepTypes,
              final Set<ParameterType> parameterTypes) {
        super(new ModifiableStepInfoImpl(stepType, startDate));
        this.nextStepTypes = nextStepTypes;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public Set<StepType> getNextStepTypes() {
        return Collections.unmodifiableSet(this.nextStepTypes);
    }

    @Override
    public Set<ParameterType> getParameterTypes() {
        return Collections.unmodifiableSet(this.parameterTypes);
    }

    @Override
    public String toString() {
        return "[BasicStepImpl] " + super.toString();
    }
}
