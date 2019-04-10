package nwoolcan.controller;

import nwoolcan.controller.brewery.BreweryController;

/**
 * Controller.
 */
public interface Controller {
    /**
     * Return the {@link BreweryController}.
     * @return the {@link BreweryController}.
     */
    BreweryController getBreweryController();
}
