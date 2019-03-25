package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;

public interface BatchInfoObserver {
    void update(Parameter p);
}
