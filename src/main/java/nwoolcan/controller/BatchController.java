package nwoolcan.controller;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.model.brewery.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.batch.review.Evaluation;
import nwoolcan.model.brewery.batch.review.EvaluationFactory;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDTO;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationDetailViewModel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BatchController {

    private final Brewery brewery;

    /**
     * Basic constructor with reference to the {@link Brewery} model.
     *
     * @param model the model to use as reference.
     */
    public BatchController(final Brewery model) {
        this.brewery = model;
    }

    public Result<Batch> getBatchById(final int batchId) {
        final QueryBatch querySingleBatch = new QueryBatchBuilder().setMinId(batchId)
                                                                   .setMaxId(batchId)
                                                                   .build().getValue();
        return Result.of(brewery.getBatches(querySingleBatch))
                     .require(batches -> batches.size() == 1)
                     .map(batches -> batches.iterator().next());
    }

    /**
     * Return the evaluation for specified batch if available.
     * @param batchID the batch.
     * @return the evaluation for specified batch if available.
     */
    public Result<Optional<BatchEvaluationDetailViewModel>> getBatchEvaluation(final int batchID) {
        return getBatchById(batchID).map(batch -> batch.getEvaluation().map(BatchEvaluationDetailViewModel::new));
    }

    /**
     * Return all available {@link BatchEvaluationType};
     * @return all available {@link BatchEvaluationType};
     */
    public Result<Set<BatchEvaluationType>> getAvailableBatchEvaluationTypes() {
        return BatchEvaluationBuilder.getAvailableBatchEvaluationTypes();
    }

    /**
     * Register an evaluation for the specified batch.
     * @param batchID the id of the batch.
     * @param newBatch the new evaluation.
     * @return a Result describing the outcome of the operation.
     */
    public Result<Empty> addBatchEvaluation(final int batchID, final BatchEvaluationDTO newBatch) {
        return newBatch.getCategories()
                       .stream()
                       .map(cat -> EvaluationFactory.create(cat.getLeft(), cat.getMiddle(), cat.getRight().orElse(null)))
                       .<Result<Set<Evaluation>>>reduce(
                           Result.of(new HashSet<>()),
                           (res, cat) -> res.require(cat::isPresent, cat.getError())
                                 .peek(list -> list.add(cat.getValue())),
                           (res1, res2) -> res1.require(res2::isPresent, res2.getError())
                                    .peek(list -> list.addAll(res2.orElse(HashSet::new))))
                       .flatMap(cat -> {
                           BatchEvaluationBuilder builder = new BatchEvaluationBuilder();
                           newBatch.getNotes().ifPresent(builder::addNotes);
                           newBatch.getReviewer().ifPresent(builder::addReviewer);
                           return new BatchEvaluationBuilder().build(newBatch.getBatchEvaluationType(), cat)
                                                              .map(eval -> getBatchById(batchID).map(b -> b.setEvaluation(eval)));
                       })
                       .toEmpty();
    }

}
