package nwoolcan.controller.batch;

import nwoolcan.controller.ControllerUtils;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.production.batch.DetailBatchViewModel;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepDTO;
import nwoolcan.viewmodel.brewery.production.batch.GoNextStepViewModel;

import java.util.Date;

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
}
