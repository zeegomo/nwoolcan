package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Interface for handling a beer batch.
 */
public interface Batch {

    /**
     * Returns the batch id.
     * @return the batch id.
     */
    int getId();
    /**
     * Returns the info of this batch.
     * @return the info of this batch.
     */
    BatchInfo getBatchInfo();
    /**
     * Returns the current production step of the batch.
     * @return the current production step of the batch.
     */
    Step getCurrentStep();
    /**
     * Returns the current batch size as a {@link Quantity}.
     * @return the current batch size.
     */
    Quantity getCurrentSize();
    /**
     * Returns a Result containing a list describing all production steps
     * that happened, in chronological order.
     * @return a list describing all production steps that happened.
     */
    List<Step> getSteps();
    /**
     * Goes to the next step of type passed by parameter.
     * If the current step has not been finalized yet, it finalizes it with empty note, date now
     * and end size equal to the current size.
     * Returns a Result with an error of type:
     * <ul>
     *     <li>{@link IllegalStateException} if the batch is in ended state.</li>
     *     <li>{@link IllegalArgumentException} if the next step type cannot be after the current step type or
     *     if the next step type cannot be created.</li>
     * </ul>
     * @param nextStepType the next step type.
     * @return a Result with an error if one of above conditions meet.
     */
    Result<Empty> moveToNextStep(StepType nextStepType);
    /**
     * Returns true if the current batch is in ended state.
     * @return true if the current batch is in ended state.
     */
    boolean isEnded();
    /**
     * Sets a possible evaluation to the batch.
     * Returns a Result with an error of type:
     * <ul>
     *     <li>{@link IllegalStateException} if the batch is not in ended state.</li>
     * </ul>
     * @param evaluation the evaluation of the batch.
     * @return a Result with an error if one of above conditions meet.
     */
    Result<Empty> setEvaluation(BatchEvaluation evaluation);
    /**
     * Returns the BatchEvaluation of this batch if it has one.
     * @return the BatchEvaluation of this batch if it has one.
     */
    Optional<BatchEvaluation> getEvaluation();
    /**
     * Returns true if the current batch has been stocked.
     * @return true if the current batch has been stocked.
     */
    boolean isStocked();
    /**
     * Stock a batch into the supplied {@link BeerStock}.
     * Checks that the batch can be stocked (eg. is ended and not already stocked) and
     * that the {@link BeerArticle} passed by parameter has same unit of measure of the current
     * batch size.
     * @param article the article to check its unit of measure.
     * @param supplier a supplier called only if all checks pass. The stock is populated with a record of quantity equals
     *                 to the current size of the batch.
     * @return a {@link Result} bearing an error if above checks fail.
     */
    Result<Empty> stockBatchInto(BeerArticle article, Supplier<BeerStock> supplier);
    /**
     * Returns the stock reference of this batch if it has been stocked,
     * an empty optional otherwise.
     * @return an {@link Optional} with the stock if this batch has been stocked.
     */
    Optional<Integer> getStockIdReference();
}
