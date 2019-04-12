package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.utils.Result;

import java.util.Date;

/**
 * Abstract step factory interface.
 */
public interface StepFactory {

    /**
     * Returns a new {@link Step} (from step type and start date) in the Result if the {@link StepType}
     * passed by parameter if the step type has a configured implementation, otherwise an error with:
     * <ul>
     *     <li>{@link IllegalArgumentException} if no configured implementation is found.</li>
     * </ul>
     * @param stepType the step's type.
     * @param startDate the step's start date.
     * @return a new {@link Step} in the Result with its correct implementation.
     */
    Result<Step> create(StepType stepType, Date startDate);

    /**
     * Returns a new {@link Step} (from step type and start date now) in the Result if the {@link StepType}
     * passed by parameter if the step type has a configured implementation, otherwise an error with:
     * <ul>
     *     <li>{@link IllegalArgumentException} if no configured implementation is found.</li>
     * </ul>
     * @param stepType the step's type.
     * @return a new {@link Step} in the Result with its correct implementation.
     */
    Result<Step> create(StepType stepType);
}
