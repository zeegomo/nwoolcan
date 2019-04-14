package nwoolcan.view;

import javafx.scene.Parent;
import nwoolcan.utils.Result;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Interfaces representing a view manager.
 */
public interface ViewManager {
    /**
     * Loads and returns the specified view.
     * @param type The type of the view you want to get
     * @return The loaded views
     */
    Result<Parent> getView(ViewType type);
    /**
     * Loads and returns the specified view, injecting the given view model into the controller.
     * @param type The type of the view you want to get
     * @param viewModel The object you want to inject
     * @param <T> The type of the view model
     * @return The loaded views
     */
    <T> Result<Parent> getView(ViewType type, T viewModel);

    /**
     *
     * @param type
     * @param viewModel
     * @param cl
     * @param <T>
     * @param <U>
     * @return
     */
    public <T, U> Result<Pair<Parent, U>> getView(ViewType type, T viewModel, Class<U> cl);
}
