package nwoolcan.application;

import javafx.scene.Parent;
import nwoolcan.utils.Result;
import nwoolcan.view.ViewType;

public interface ViewManager {
    /**
     * Loads and returns the specified view.
     * @param type The type of the view you want to get
     * @return The loaded views
     */
    public Result<Parent> getView(ViewType type);
    /**
     * Loads and returns the specified view, injecting the given view model into the controller.
     * @param type The type of the view you want to get
     * @param viewModel The object you want to inject
     * @param <T> The type of the view model
     * @return The loaded views
     */
    public <T> Result<Parent> getView(ViewType type, T viewModel);
}
