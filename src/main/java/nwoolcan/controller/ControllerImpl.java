package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;
import nwoolcan.controller.brewery.BreweryControllerImpl;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.production.batch.QueryBatchBuilder;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;
import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller basic implementation.
 */
public final class ControllerImpl implements Controller {

    private final Brewery brewery;
    private final BreweryController breweryController;

    /**
     * Construct a controller and inject the model ({@link Brewery}) to the subcontrollers.
     * @param model the model to inject,
     */
    public ControllerImpl(final Brewery model) {
        this.brewery = model;
        this.breweryController = new BreweryControllerImpl(model);
    }

    @Override
    public BreweryController getBreweryController() {
        return this.breweryController;
    }

    @Override
    public ProductionViewModel getProductionViewModel() {
        return new ProductionViewModel(this.brewery.getBatches(new QueryBatchBuilder().build().getValue()));
    }

    @Override
    public List<MasterBatchViewModel> getBatches(final QueryBatch query) {
        return Collections.unmodifiableList(this.brewery.getBatches(query).stream()
                                                        .map(MasterBatchViewModel::new)
                                                        .collect(Collectors.toList()));
    }
}
