package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.utils.Result;

import java.util.Date;

/**
 * Helper class for creating production steps.
 */
public final class Steps {

    private static final String CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE = " does not have a configured implementation.";

    private Steps() { }

    /**
     * Returns a new {@link Step} (from step type and start date) in the Result if the {@link StepType}
     * passed by parameter if the step type has a configured implementation, otherwise an error with:
     * <ul>
     *     <li>{@link IllegalArgumentException} if no configured implementation is found.</li>
     * </ul>
     * @param stepType the step's type.
     * @param startDate the step's start date.
     * @return a new {@link Step} is the Result with its correct implementation.
     */
    public static Result<Step> create(final StepType stepType, final Date startDate) {
        //only one configured implementation for now
        if (stepType instanceof StepTypeEnum) {
            return Result.of(new BasicStepImpl(stepType, startDate));
        }
        return Result.error(new IllegalArgumentException(stepType + CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE));
    }

    /**
     * Returns a new {@link Step} (from step type and start date now) in the Result if the {@link StepType}
     * passed by parameter if the step type has a configured implementation, otherwise an error with:
     * <ul>
     *     <li>{@link IllegalArgumentException} if no configured implementation is found.</li>
     * </ul>
     * @param stepType the step's type.
     * @return a new {@link Step} is the Result with its correct implementation.
     */
    public static Result<Step> create(final StepType stepType) {
        return create(stepType, new Date());
    }
}
