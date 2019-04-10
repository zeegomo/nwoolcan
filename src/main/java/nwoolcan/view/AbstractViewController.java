package nwoolcan.view;

import nwoolcan.application.ViewManagerImpl;
import nwoolcan.controller.Controller;

public abstract class AbstractViewController {

    private final Controller controller;
    private final ViewManagerImpl viewManager;

    AbstractViewController(final Controller controller, final ViewManagerImpl viewManager) {
        this.controller = controller;
        this.viewManager = viewManager;
    }

    public Controller getController() {
        return this.controller;
    }

    public ViewManagerImpl getViewManager() {
        return this.viewManager;
    }
}
