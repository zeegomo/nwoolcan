package nwoolcan.view;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import nwoolcan.controller.Controller;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

/**
 * Controller of a subview. Exposes methods to change the current view.
 */
public abstract class SubViewController extends AbstractViewController {

    /**
     * Creates itself and gets injected.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    protected SubViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    /**
     * Changes the current view.
     * @param type The type of the new view
     * @param viewModel View model object (may be null)
     * @param action Action to perform on the SubViewContainer
     * @param <T> The type of the view model
     */
    private <T> void changeView(final ViewType type, @Nullable final T viewModel, final BiConsumer<SubViewContainer, Parent> action) {
        this.getSubView().getContainer().ifPresent(c ->
                (viewModel == null ? this.getViewManager().getView(type) : this.getViewManager().getView(type, viewModel))
            .peek(v -> action.accept(c, v))
            .peekError(err -> new Alert(Alert.AlertType.ERROR, "Error loading " + type.toString()).showAndWait()));
    }

    /**
     * Substitutes the current view.
     * @param type The type of the new view
     */
    protected final void substituteView(final ViewType type) {
        this.changeView(type, null, SubViewContainer::substitute);
    }

    /**
     * Substitutes the current view and injects the given view model into the controller.
     * @param type The type of the new view
     * @param viewModel The view model to inject
     * @param <T> The type of the view model
     */
    protected final <T> void substituteView(final ViewType type, final T viewModel) {
        this.changeView(type, viewModel, SubViewContainer::substitute);
    }

    /**
     * Put a new overlay over the current one.
     * @param type The type of the new view
     */
    protected final void overlayView(final ViewType type) {
        this.changeView(type, null, SubViewContainer::overlay);
    }

    /**
     * Put a new overlay over the current one and injects the given view model into the controller.
     * @param type The type of the new view
     * @param viewModel The view model to inject
     * @param <T> The type of the view model
     */
    protected final <T> void overlayView(final ViewType type, final T viewModel) {
        this.changeView(type, viewModel, SubViewContainer::overlay);
    }

    /**
     * Pops the current overlay and show the previous one.
     */
    protected final void previousView() {
        this.getSubView().getContainer()
               .ifPresent(c -> c.previous()
                                .peekError(err -> new Alert(Alert.AlertType.WARNING, "No previous found").showAndWait()));
    }

    /**
     * Override this so that it returns your {@link SubView} object from FXML (it should be only one, the main container of your view).
     * @return The main container of the view, a {@link SubView} object
     */
    protected abstract SubView getSubView();
}
