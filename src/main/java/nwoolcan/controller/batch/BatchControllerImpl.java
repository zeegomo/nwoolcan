package nwoolcan.controller.batch;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.QueryBatchBuilder;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;

import java.util.Optional;

/**
 * Basic implementation of {@link BatchController} interface.
 */
public final class BatchControllerImpl implements BatchController {

    private static final String BATCH_NOT_FOUND_MESSAGE = "Cannot find the batch with id: ";
    private final Brewery model;

    /**
     * Basic constructor with reference to the {@link Brewery} model.
     * @param model the model to use as reference.
     */
    public BatchControllerImpl(final Brewery model) {
        this.model = model;
    }

    @Override
    public Result<DetailBatchViewModel> getDetailBatchViewModelById(final int batchId) {
        return this.getBatchById(batchId)
                     .map(DetailBatchViewModel::new);
    }

    @Override
    public Result<GoNextStepViewModel> getGoNextStepViewModel(final int batchId) {
        return this.getBatchById(batchId)
                   .map(GoNextStepViewModel::new);
    }

    private Result<Batch> getBatchById(final int batchId) {
        final Optional<Batch> batch = this.model.getBatches(new QueryBatchBuilder().setMinId(batchId)
                                                                                   .setMaxId(batchId)
                                                                                   .build()
                                                                                   .getValue())
                                                .stream()
                                                .findAny();

        return Result.of(batch)
                     .require(Optional::isPresent, new IllegalArgumentException(BATCH_NOT_FOUND_MESSAGE + batchId))
                     .map(Optional::get);
    }
}
