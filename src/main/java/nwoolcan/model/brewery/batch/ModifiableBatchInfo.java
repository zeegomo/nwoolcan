package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.utils.Observer;

//Package-private
interface ModifiableBatchInfo extends BatchInfo, Observer<Parameter> {
}
