package nwoolcan.controller;

import nwoolcan.model.brewery.BreweryImpl;

/**
 * Singleton Model context for production.
 */
public final class ProductionContext {
    private static final ProductionController SINGLETON = new ProductionControllerImpl(new BreweryImpl("Test", "Dummy"));

    /**
     * Returns the singleton controller of production.
     * @return the singleton controller of production.
     */
    public static ProductionController getController() {
        return SINGLETON;
    }

    private ProductionContext() { }
}
