package nwoolcan.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

/**
 * Sets up the whole JavaFX application.
 */
public final class ViewManager {
    /**
     * Returns a maps from ViewType to actual views loaded from FXML.
     * @return The loaded views
     */
    public static Result<Parent> getView(final ViewType type) {
        return Results.ofChecked(() -> FXMLLoader.load(ViewManager.class.getResource(type.getResourceName())));
    }

    public static <T> Result<Parent> getView(final ViewType type, T viewModel) {
        final FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(type.getResourceName()));
        return Results.ofChecked(() -> {
            final Parent parent = loader.load();
            loader.<InitializableController<T>>getController().initData(viewModel);
            return parent;
        });
    }
}
