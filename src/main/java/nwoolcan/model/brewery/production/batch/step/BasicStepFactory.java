package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.utils.Result;

import java.util.Date;

/**
 * Step factory implementation for creating basic steps.
 */
public final class BasicStepFactory implements StepFactory {

    private static final String CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE = " does not have a configured implementation.";

    @Override
    public Result<Step> create(final StepType stepType, final Date startDate) {
        return Result.of(stepType)
                     .require(st -> st instanceof StepTypeEnum,
                         new IllegalArgumentException(stepType + CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE))
                     .map(e -> new BasicStep(stepType, startDate));
    }

    @Override
    public Result<Step> create(final StepType stepType) {
        return create(stepType, new Date());
    }
}
