package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Interface for handling a production step.
 */
public interface Step {

    /**
     * Returns an unmodifiable {@link StepInfo} containing information about this step.
     * @return an unmodifiable {@link StepInfo} containing information about this step.
     */
    StepInfo getStepInfo();

    /**
     * Returns a {@link Set} of all possible next step types that can happen after this step.
     * It depends on the current step type and it's implementation.
     * @return a {@link Set} of all possible next step types that can happen after this step.
     */
    Set<StepType> getNextStepTypes();

    /**
     * Returns a {@link Set} of all possible parameter types that can be registered in this kind of step.
     * It depends on the current step type and it's implementation.
     * @return a {@link Set} of all possible parameter types that can be registered in this kind of step.
     */
    Set<ParameterType> getParameterTypes();

    /**
     * Returns true if the step is finalized (so it has finished), false otherwise.
     * @return true if the step is finalized (so it has finished), false otherwise.
     */
    boolean isFinalized();

    /**
     * Finalize this step with possible notes, the end date of the finalization and the remaining size of the batch.
     * Returns a {@link Result} with a:
     * <ul>
     *     <li>{@link NullPointerException} if the endDate or the remainingSize is null.</li>
     *     <li>{@link IllegalArgumentException} if the endDate is before the step start date.</li>
     *     <li>{@link IllegalStateException} if the step has already been finalized.</li>
     * </ul>
     * @param note the possible notes of this step.
     * @param endDate the end date of this step (when finalization occurred).
     * @param remainingSize the remaining size quantity of the batch in production.
     * @return a {@link Result} that can contain an error cited before.
     */
    Result<Empty> finalize(String note, Date endDate, Quantity remainingSize);

    /**
     * Returns a {@link Result} with a collection of {@link Parameter} describing the registered parameters of this step.
     * The parameters can be filtered and rearranged by specifying a (mandatory) {@link QueryParameter} that
     * needs to be build correctly with its builder.
     * The result can contain an error of type:
     * <ul>
     *     <li>{@link NullPointerException} if the query object is null.</li>
     *     <li>{@link IllegalArgumentException} if the object query is inconsistent.</li>
     *     <li>{@link RuntimeException} if there are errors while executing the query.</li>
     * </ul>
     * @param query the query object that specifies how to query the parameters.
     * @return a {@link Result} with a collection of {@link Parameter} describing the registered parameters of this step.
     */
    Result<Collection<Parameter>> getParameters(QueryParameter query);

    /**
     * Registers a {@link Parameter} to this step and adds it to its collection.
     * Returns a {@link Result} that can contain an error of type:
     * <ul>
     *     <li>{@link NullPointerException} if the parameter is null.</li>
     *     <li>{@link IllegalArgumentException} if the parameter type cannot be registered in this step.</li>
     *     <li>{@link RuntimeException} if there are errors while adding the parameter.</li>
     * </ul>
     * @param parameter the parameter to be registered.
     * @return a {@link Result} that can contain an error cited before.
     */
    Result<Empty> addParameter(Parameter parameter);
}
