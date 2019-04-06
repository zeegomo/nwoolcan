package nwoolcan.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

/**
 * Sets up the whole JavaFX application.
 */
public final class ViewManager {
    private ViewManager() { }

    /**
     * Loads and returns the specified view.
     * @param type The type of the view you want to get
     * @return The loaded views
     */
    public static Result<Parent> getView(final ViewType type) {
        return Results.ofChecked(() -> FXMLLoader.load(ViewManager.class.getResource(type.getResourceName())));
    }

    /**
     * Loads and returns the specified view, injecting the given view model into the controller.
     * @param type The type of the view you want to get
     * @param viewModel The object you want to inject
     * @param <T> The type of the view model
     * @return The loaded views
     */
    public static <T> Result<Parent> getView(final ViewType type, final T viewModel) {
        final FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(type.getResourceName()));
        return Results.ofChecked(() -> {
            final Parent parent = loader.load();
            loader.<InitializableController<T>>getController().initData(viewModel);
            return parent;
        });
    }
}
