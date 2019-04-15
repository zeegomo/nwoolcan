package nwoolcan.controller.batch;

import nwoolcan.controller.ControllerUtils;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationFactory;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepDTO;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDTO;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Basic implementation of {@link BatchController} interface.
 */
public final class BatchControllerImpl implements BatchController {

    private static final String BATCH_NOT_FOUND_MESSAGE = "Cannot find the batch with id: ";
    private final ControllerUtils utils;

    /**
     * Basic constructor with reference to the {@link Brewery} model.
     * @param model the model to use as reference.
     */
    public BatchControllerImpl(final Brewery model) {
        this.utils = new ControllerUtils(model);
    }

    @Override
    public Result<DetailBatchViewModel> getDetailBatchViewModelById(final int batchId) {
        return this.utils.getBatchById(batchId)
                         .map(DetailBatchViewModel::new);
    }

    @Override
    public Result<GoNextStepViewModel> getGoNextStepViewModel(final int batchId) {
        return this.utils.getBatchById(batchId)
                         .map(GoNextStepViewModel::new);
    }

    @Override
    public Result<Empty> goToNextStep(final int batchId, final GoNextStepDTO dto) {
        Result<Batch> res =  this.utils.getBatchById(batchId);

        if (dto.finalizeBeforeGoingToNext()) {
            res = res.flatMap(b -> {
                         //did this for nullaway
                         if (dto.getEndSize() == null) {
                             return Result.error(new IllegalArgumentException());
                         }
                         return b.getCurrentStep().finalize(
                             dto.getNotes().isEmpty() ? null : dto.getNotes(),
                             new Date(),
                             dto.getEndSize()).map(e -> b);
                     });
        }

        return res.flatMap(b -> b.moveToNextStep(dto.getNextStepType()));
    }

    @Override
    public Result<Empty> addBatchEvaluation(final int batchID, final BatchEvaluationDTO newBatch) {
        return newBatch.getCategories()
                       .stream()
                       .map(cat -> EvaluationFactory.create(cat.getLeft(), cat.getMiddle(), cat.getRight().orElse(null)))
            .<Result<Set<Evaluation>>>reduce(
                Result.of(new HashSet<>()),
                (res, cat) -> res.require(cat::isPresent, cat::getError)
                                 .peek(list -> list.add(cat.getValue())),
                (res1, res2) -> res1.require(res2::isPresent, res2::getError)
                                    .peek(list -> list.addAll(res2.orElse(HashSet::new))))
            .flatMap(cat -> {
                BatchEvaluationBuilder builder = new BatchEvaluationBuilder();
                newBatch.getNotes().ifPresent(builder::addNotes);
                newBatch.getReviewer().ifPresent(builder::addReviewer);
                return new BatchEvaluationBuilder().build(newBatch.getBatchEvaluationType(), cat)
                                                   .map(eval -> utils.getBatchById(batchID).map(b -> b.setEvaluation(eval)));
            })
            .toEmpty();
    }
    @Override
    public Result<Optional<BatchEvaluationDetailViewModel>> getBatchEvaluation(final int batchID) {
        return utils.getBatchById(batchID).map(batch -> batch.getEvaluation().map(BatchEvaluationDetailViewModel::new));
    }
}
