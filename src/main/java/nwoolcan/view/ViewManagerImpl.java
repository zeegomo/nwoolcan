package nwoolcan.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.lang.reflect.Constructor;

/**
 * View manager impl that can inject a controller and itself to the view controllers constructors.
 * After loaded the view controller can inject a specific view model to the view controller.
 */
public final class ViewManagerImpl implements ViewManager {

    private static final String NO_DESIGNATED_CONSTRUCTOR_FOUND_MESSAGE = "No designated constructor found for view controller.";

    private final Controller controller;

    /**
     * Creates a view manager that injects the passed {@link Controller}.
     * @param controller the controller to be injected.
     */
    public ViewManagerImpl(final Controller controller) {
        this.controller = controller;
    }

    private void injectIntoController(final FXMLLoader loader) {
        loader.setControllerFactory(c -> {
            final Constructor<?>[] constructors = c.getConstructors();

            try {
                //Search for injection constructor
                for (final Constructor<?> cons : constructors) {
                    if (cons.getParameters().length == 2 && cons.getParameters()[0].getType() == Controller.class
                        && cons.getParameters()[1].getType() == ViewManager.class) {
                        return cons.newInstance(controller, this);
                    }
                }

                //Search for empty constructor
                for (final Constructor<?> cons : constructors) {
                    if (cons.getParameters().length == 0) {
                        return cons.newInstance();
                    }
                }
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }

            throw new IllegalArgumentException(NO_DESIGNATED_CONSTRUCTOR_FOUND_MESSAGE);
        });
    }

    /**
     * Loads and returns the specified view.
     * @param type The type of the view you want to get
     * @return The loaded views
     */
    @Override
    public Result<Parent> getView(final ViewType type) {
        return Results.ofChecked(() -> {
            final FXMLLoader loader = new FXMLLoader(ViewType.class.getResource(type.getResourceName()));
            this.injectIntoController(loader);
            return loader.load();
        });
    }

    /**
     * Loads and returns the specified view, injecting the given view model into the controller.
     * @param type The type of the view you want to get
     * @param viewModel The object you want to inject
     * @param <T> The type of the view model
     * @return The loaded views
     */
    @Override
    public <T> Result<Parent> getView(final ViewType type, final T viewModel) {
        final FXMLLoader loader = new FXMLLoader(ViewType.class.getResource(type.getResourceName()));
        return Results.ofChecked(() -> {
            this.injectIntoController(loader);
            final Parent parent = loader.load();
            loader.<InitializableController<T>>getController().initData(viewModel);
            return parent;
        });
    }
}
