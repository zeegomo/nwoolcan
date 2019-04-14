package nwoolcan.viewmodel.brewery.production.batch;


import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.viewmodel.brewery.production.batch.review.BatchEvaluationViewModel;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * View model representing a detailed step.
 */
public final class DetailBatchViewModel {

    private final int id;
    private final BatchInfoViewModel batchInfo;
    private final List<MasterStepViewModel> steps;
    private final Set<StepType> nextStepTypes;

    @Nullable
    private final BatchEvaluationViewModel review;

    /**
     * Basic constructor with decorator-like pattern.
     * @param batch the batch to get the data from.
     */
    public DetailBatchViewModel(final Batch batch) {
        this.id = batch.getId();
        this.batchInfo = new BatchInfoViewModel(); //TODO change to correct implementation when done
        this.steps = batch.getSteps().stream().map(MasterStepViewModel::new).collect(Collectors.toList());
        this.nextStepTypes = Collections.unmodifiableSet(batch.getCurrentStep().getNextStepTypes());
        this.review = batch.getEvaluation().map(BatchEvaluationViewModel::new).orElse(null);
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
     * Returns a set describing what steps type can come next of the current step of the batch.
     * @return a set describing what steps type can come next of the current step of the batch.
     */
    public Set<StepType> getNextStepTypes() {
        return this.nextStepTypes;
    }

    /**
     * Returns the evaluation view model of the batch representing its review (null if none).
     * @return the evaluation view model of the batch representing its review (null if none).
     */
    @Nullable
    public BatchEvaluationViewModel getReview() {
        return this.review;
    }
}
