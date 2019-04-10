package nwoolcan.view;

import nwoolcan.application.ViewManager;
import nwoolcan.controller.Controller;

public abstract class AbstractViewController {

    private final Controller controller;
    private final ViewManager viewManager;

    AbstractViewController(final Controller controller, final ViewManager viewManager) {
        this.controller = controller;
        this.viewManager = viewManager;
    }

    public Controller getController() {
        return this.controller;
    }

    public ViewManager getViewManager() {
        return this.viewManager;
    }
}
