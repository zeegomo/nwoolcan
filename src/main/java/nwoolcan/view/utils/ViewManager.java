package nwoolcan.view.utils;

import javafx.scene.Parent;
import nwoolcan.utils.Result;
import nwoolcan.view.ViewType;
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
     * Loads and returns the specified view and the associated controller.
     * @param type The type of the view you want to get
     * @param <U> The type of the controller
     * @return The loaded views
     */
    <U> Result<Pair<Parent, U>> getViewAndController(ViewType type);
    /**
     * Loads and returns the specified view and the associated controller, injecting the given view model into the controller.
     * @param type The type of the view you want to get
     * @param viewModel The object you want to inject
     * @param <T> The type of the view model
     * @param <U> The type of the controller
     * @return The loaded views
     */
    <T, U> Result<Pair<Parent, U>> getViewAndController(ViewType type, T viewModel);
}
