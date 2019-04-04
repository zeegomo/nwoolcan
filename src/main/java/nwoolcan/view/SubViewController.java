package nwoolcan.view;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

/**
 * Controller of a subview. Exposes methods to change the current view.
 */
public abstract class SubViewController {
    /**
     * Changes the current view.
     * @param content TODO To remove in next commit
     * @param type The type of the new view
     * @param viewModel View model object (may be null)
     * @param action Action to perform on the SubViewContainer
     * @param <T> The type of the view model
     */
    private <T> void changeView(final SubView content, final ViewType type, @Nullable final T viewModel, final BiConsumer<SubViewContainer, Parent> action) {
        content.getContainer().ifPresent(c -> (viewModel == null ? ViewManager.getView(type) : ViewManager.getView(type, viewModel))
            .peek(v -> action.accept(c, v))
            .peekError(err -> new Alert(Alert.AlertType.ERROR, "Error loading " + type.toString()).showAndWait()));
    }

    /**
     * Substitutes the current view.
     * @param content TODO To remove in next commit
     * @param type The type of the new view
     */
    protected final void substituteView(final SubView content, final ViewType type) {
        this.changeView(content, type, null, SubViewContainer::substitute);
    }

    /**
     * Substitutes the current view and injects the given view model into the controller.
     * @param content TODO To remove in next commit
     * @param type The type of the new view
     * @param viewModel The view model to inject
     * @param <T> The type of the view model
     */
    protected final <T> void substituteView(final SubView content, final ViewType type, final T viewModel) {
        this.changeView(content, type, viewModel, SubViewContainer::substitute);
    }

    /**
     * Put a new overlay over the current one.
     * @param content TODO To remove in next commit
     * @param type The type of the new view
     */
    protected final void overlayView(final SubView content, final ViewType type) {
        this.changeView(content, type, null, SubViewContainer::overlay);
    }

    /**
     * Put a new overlay over the current one and injects the given view model into the controller.
     * @param content TODO To remove in next commit
     * @param type The type of the new view
     * @param viewModel The view model to inject
     * @param <T> The type of the view model
     */
    protected final <T> void overlayView(final SubView content, final ViewType type, final T viewModel) {
        this.changeView(content, type, viewModel, SubViewContainer::overlay);
    }

    /**
     * Pops the current overlay and show the previous one.
     * @param content TODO To remove in next commit
     */
    protected final void previousView(final SubView content) {
        content.getContainer()
               .ifPresent(c -> c.previous()
                                .peekError(err -> new Alert(Alert.AlertType.WARNING, "No previous found").showAndWait()));
    }
}
