package nwoolcan.controller.batch;

import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.EvaluationType;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepDTO;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;
<<<<<<< HEAD
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDTO;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Optional;
import java.util.Set;
=======
import nwoolcan.viewmodel.brewery.production.batch.StockBatchViewModel;
>>>>>>> 71d28ecaa0bff213d524454799bf25138b59241e

/**
 * Interface representing a batch controller.
 */
public interface BatchController {

    /**
     * Returns a {@link DetailBatchViewModel} representation of the batch of id passed by parameter, if any.
     * @param batchId the batch id.
     * @return a {@link Result} bearing the representation of the batch or with an error if no batch with this id is found.
     */
    Result<DetailBatchViewModel> getDetailBatchViewModelById(int batchId);

    /**
     * Returns a {@link GoNextStepViewModel} with the informations to choose the next step to go in the batch with
     * specified id.
     * @param batchId the batch id.
     * @return a {@link Result} bearing the informations to choose choose the next step to go.
     */
    Result<GoNextStepViewModel> getGoNextStepViewModel(int batchId);
    /**
     * Goes to the next step of the batch with passed id with informations specified in the dto.
     * @param batchId the batch id.
     * @param dto the dto specifying infos to go to next step.
     * @return a {@link Result} bearing an error if operation went wrong.
     */
    Result<Empty> goToNextStep(int batchId, GoNextStepDTO dto);
    /**
     * Register an evaluation for the specified batch.
     * @param batchID the id of the batch.
     * @param newBatch the new evaluation.
     * @return a Result describing the outcome of the operation.
     */
    Result<Empty> addBatchEvaluation(int batchID, BatchEvaluationDTO newBatch);
    /**
     * Check evaluation data validity.
     * @param data the evaluation data
     * @return a Result describing the outcome of the operation.
     */
    Result<Empty> checkEvaluation(Triple<EvaluationType, Integer, Optional<String>> data);
    /**
     * Return the evaluation for specified batch if available.
     * @param batchID the batch.
     * @return the evaluation for specified batch if available.
     */
    Result<Optional<BatchEvaluationDetailViewModel>> getBatchEvaluation(int batchID);
    /**
     * Return all available {@link BatchEvaluationType}.
     * @return all available {@link BatchEvaluationType}.
     */
    Result<Set<BatchEvaluationType>> getAvailableBatchEvaluationTypes();
    /**
     * Returns the {@link StockBatchViewModel} with the necessary info to decide how to stock a batch.
     * @param batchId the batch id to request the view model from.
     * @return a {@link Result} bearing the view model or an error occurs.
     */
    Result<StockBatchViewModel> getStockBatchViewModel(int batchId);
}
