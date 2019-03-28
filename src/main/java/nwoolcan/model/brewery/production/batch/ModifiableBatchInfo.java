package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.utils.Observer;

//Package-private
interface ModifiableBatchInfo extends BatchInfo, Observer<Parameter> {
}
