package nwoolcan.controller;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.production.batch.QueryBatchBuilder;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//Package-private.
final class ProductionControllerImpl implements ProductionController {

    private final Brewery brewery;

    //Package-private.
    ProductionControllerImpl(final Brewery brewery) {
        this.brewery = brewery;
    }

    @Override
    public ProductionViewModel getViewModel() {
        return new ProductionViewModel(this.brewery.getBatches(new QueryBatchBuilder().build().getValue()));
    }

    @Override
    public List<MasterBatchViewModel> getBatches(final QueryBatch query) {
        return Collections.unmodifiableList(this.brewery.getBatches(query).stream()
                                                        .map(MasterBatchViewModel::new)
                                                        .collect(Collectors.toList()));
    }
}
