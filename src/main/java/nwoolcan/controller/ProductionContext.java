package nwoolcan.controller;

import nwoolcan.model.brewery.BreweryContext;

/**
 * Singleton Model context for production.
 */
public final class ProductionContext {
    private static final ProductionController SINGLETON = new ProductionControllerImpl(BreweryContext.getInstance());

    /**
     * Returns the singleton controller of production.
     * @return the singleton controller of production.
     */
    public static ProductionController getController() {
        return SINGLETON;
    }

    private ProductionContext() { }
}
