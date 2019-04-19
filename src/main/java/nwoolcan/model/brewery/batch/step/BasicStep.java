package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.batch.step.parameter.ParameterType;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Set;

/**
 * Basic implementation of {@link Step} interface.
 * Package-private.
 * Create instances of this class using the factory {@link EnumStepFactory}.
 */
final class BasicStep extends AbstractStep {

    /**
     * Basic constructor with step type and start date of the step.
     * Package-protected, to create a Step use the factory {@link EnumStepFactory}.
     * @param stepType step's type.
     * @param startDate step's start date.
     */
    BasicStep(final StepType stepType,
              final Date startDate,
              final Set<StepType> nextStepTypes,
              final Set<ParameterType> registrationParameterTypes) {
        super(new ModifiableStepInfoImpl(stepType, startDate), nextStepTypes, registrationParameterTypes);
    }

    @Override
    protected Result<Empty> checkFinalizationData(@Nullable final String note, final Date endDate, final Quantity remainingSize) {
        return Result.ofEmpty();
    }

    @Override
    protected Result<Empty> checkRegisteringParameter(final Parameter parameter) {
        return Result.ofEmpty();
    }

    @Override
    public String toString() {
        return "[BasicStepImpl] " + super.toString();
    }
}
