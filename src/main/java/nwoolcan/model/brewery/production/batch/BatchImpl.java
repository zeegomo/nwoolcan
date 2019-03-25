package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.Step;

public class BatchImpl implements Batch {
    private final ModifiableBatchInfoImpl batchInfo;

    @Override
    public BatchInfo getBatchInfo() {
        return batchInfo;
    }

    @Override
    public Step getCurrentStep() {
        return null;
    }
}
