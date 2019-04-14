package nwoolcan.viewmodel.brewery.production.batch;


import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View model representing a detailed step.
 */
public final class DetailBatchViewModel {

    private final int id;
    private final BatchInfoViewModel batchInfo;
    private final List<MasterStepViewModel> steps;

    @Nullable
    private final EvaluationViewModel review;

    /**
     * Basic constructor with decorator-like pattern.
     * @param batch the batch to get the data from.
     */
    public DetailBatchViewModel(final Batch batch) {
        this.id = batch.getId();
        this.batchInfo = new BatchInfoViewModel(); //TODO change to correct implementation when done
        this.steps = batch.getSteps().stream().map(MasterStepViewModel::new).collect(Collectors.toList());
        this.review = batch.getEvaluation().map(EvaluationViewModel::new).orElse(null);
    }

    /**
     * Returns the batch id.
     * @return the batch id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the batch info view model of the batch.
     * @return the batch info view model of the batch.
     */
    public BatchInfoViewModel getBatchInfo() {
        return this.batchInfo;
    }

    /**
     * Returns a list of {@link MasterStepViewModel} each one representing a step in production of the batch.
     * @return a list of {@link MasterStepViewModel} each one representing a step in production of the batch.
     */
    public List<MasterStepViewModel> getSteps() {
        return Collections.unmodifiableList(this.steps);
    }

    /**
     * Returns the evaluation view model of the batch representing its review (null if none).
     * @return the evaluation view model of the batch representing its review (null if none).
     */
    @Nullable
    public EvaluationViewModel getReview() {
        return this.review;
    }
}
