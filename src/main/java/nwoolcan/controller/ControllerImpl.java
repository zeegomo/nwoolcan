package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;
import nwoolcan.controller.brewery.BreweryControllerImpl;
import nwoolcan.model.brewery.Brewery;

/**
 * Controller basic implementation.
 */
public final class ControllerImpl implements Controller {

    private final BreweryController breweryController;

    /**
     * Construct a controller and inject the model ({@link Brewery}) to the subcontrollers.
     * @param model the model to inject,
     */
    public ControllerImpl(final Brewery model) {
        this.breweryController = new BreweryControllerImpl(model);
    }

    @Override
    public BreweryController getBreweryController() {
        return this.breweryController;
    }
}
