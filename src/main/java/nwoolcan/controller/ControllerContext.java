package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;
import nwoolcan.controller.brewery.BreweryControllerImpl;
import nwoolcan.model.brewery.BreweryImpl;

/**
 * Main Controller Singleton.
 */
public final class ControllerContext implements Controller {

    private static final BreweryController BREWERY_CONTROLLER = new BreweryControllerImpl(new BreweryImpl());

    @Override
    public BreweryController getBreweryController() {
        return BREWERY_CONTROLLER;
    }
}
