package nwoolcan.viewmodel.brewery.production.batch;


import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.viewmodel.brewery.production.batch.review.EvaluationViewModel;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing the detail view model of a batch.
 */
public class DetailBatchViewModel {

    private final BatchInfoViewModel batchInfo;
    private final List<MasterStepViewModel> steps;

    @Nullable
    private final EvaluationViewModel review;

    public DetailBatchViewModel(final Batch batch) {
        this.batchInfo = new BatchInfoViewModel(); //TODO change to correct implementation when done
        this.steps = batch.getSteps().stream().map(MasterStepViewModel::new).collect(Collectors.toList());
        this.review = batch.getEvaluation().map(EvaluationViewModel::new).orElse(null);
    }

    public BatchInfoViewModel getBatchInfo() {
        return this.batchInfo;
    }

    public List<MasterStepViewModel> getSteps() {
        return Collections.unmodifiableList(this.steps);
    }

    @Nullable
    public EvaluationViewModel getReview() {
        return this.review;
    }
}
