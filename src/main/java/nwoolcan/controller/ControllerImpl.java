package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;
import nwoolcan.controller.brewery.BreweryControllerImpl;
import nwoolcan.model.brewery.Brewery;

public class ControllerImpl implements Controller {

    private final Brewery model;
    private final BreweryController breweryController;

    public ControllerImpl(final Brewery model) {
        this.model = model;
        this.breweryController = new BreweryControllerImpl(model);
    }

    @Override
    public BreweryController getBreweryController() {
        return this.breweryController;
    }
}
