package nwoolcan.model.brewery.production.batch.step;

import java.util.Set;

/**
 * Mashing step implementation.
 */
public class MashingStep extends AbstractStep {

    MashingStep(final StepInfo stepInfo) {
        super(stepInfo);
    }

    @Override
    public final Set<StepType> getNextStepTypes() {
        return null;
    }

    @Override
    public final Set<ParameterType> getParameterTypes() {
        return null;
    }
}
