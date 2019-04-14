package nwoolcan.view;

import nwoolcan.controller.Controller;

/**
 * Abstract class representing a view controller that has been injected with a {@link Controller}
 * and a {@link ViewManager}.
 */
public abstract class AbstractViewController {

    private final Controller controller;
    private final ViewManager viewManager;

    protected AbstractViewController(final Controller controller, final ViewManager viewManager) {
        this.controller = controller;
        this.viewManager = viewManager;
    }

    /**
     * Returns the injected controller.
     * @return the injected controller.
     */
    protected Controller getController() {
        return this.controller;
    }

    /**
     * Returns the injected view manager.
     * @return the injected view manager.
     */
    protected ViewManager getViewManager() {
        return this.viewManager;
    }
}
