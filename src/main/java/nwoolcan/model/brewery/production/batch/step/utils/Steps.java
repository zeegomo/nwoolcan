package nwoolcan.model.brewery.production.batch.step.utils;

import nwoolcan.model.brewery.production.batch.step.BasicStepImpl;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Date;

/**
 * Helper class for creating production steps.
 */
public final class Steps {

    private static final String CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE = " does not have a configured implementation.";
    private static final String STEP_TYPE_NULL_MESSAGE = "Step type cannot be null.";
    private static final String START_DATE_NULL_MESSAGE = "Start date cannot be null.";

    private Steps() { }

    /**
     * Returns a new {@link Step} (from step type and start date) in the Result if the {@link StepType}
     * passed by parameter if the step type has a configured implementation, otherwise an error with:
     * <ul>
     *     <li>{@link NullPointerException} if the stepType or the startDate are null.</li>
     *     <li>{@link IllegalArgumentException} if no configured implementation is found.</li>
     * </ul>
     * @param stepType the step's type.
     * @param startDate the step's start date.
     * @return a new {@link Step} is the Result with its correct implementation.
     */
    public static Result<Step> create(final StepType stepType, final Date startDate) {
        Result<Empty> res = Result.ofEmpty()
                                  .requireNonNull(stepType, STEP_TYPE_NULL_MESSAGE)
                                  .requireNonNull(startDate, START_DATE_NULL_MESSAGE);
        //only one configured implementation for now
        if (stepType instanceof StepTypeEnum) {
            return res.map(e -> new BasicStepImpl(stepType, startDate));
        }
        return res.flatMap(() -> Result.error(new IllegalArgumentException(stepType + CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE)));
    }

    /**
     * Returns a new {@link Step} (from step type and start date now) in the Result if the {@link StepType}
     * passed by parameter if the step type has a configured implementation, otherwise an error with:
     * <ul>
     *     <li>{@link NullPointerException} if the stepType or the startDate are null.</li>
     *     <li>{@link IllegalArgumentException} if no configured implementation is found.</li>
     * </ul>
     * @param stepType the step's type.
     * @return a new {@link Step} is the Result with its correct implementation.
     */
    public static Result<Step> create(final StepType stepType) {
        return create(stepType, new Date());
    }
}
