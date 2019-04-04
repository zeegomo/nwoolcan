package nwoolcan.view;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import nwoolcan.utils.Empty;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewContainer;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public abstract class SubViewController {
    private <T> void changeView(final SubView content, final ViewType type, @Nullable final T viewModel, final BiConsumer<SubViewContainer, Parent> action) {
        content.getContainer().ifPresent(c -> (viewModel == null ? ViewManager.getView(type) : ViewManager.getView(type, viewModel))
            .peek(v -> action.accept(c, v))
            .peekError(err -> new Alert(Alert.AlertType.ERROR, "Error loading " + type.toString()).showAndWait()));
    }

    protected final void substituteView(final SubView content, final ViewType type) {
        this.changeView(content, type, null, SubViewContainer::substitute);
    }

    protected final <T> void substituteView(final SubView content, final ViewType type, final T viewModel) {
        this.changeView(content, type, viewModel, SubViewContainer::substitute);
    }

    protected final void overlayView(final SubView content, final ViewType type) {
        this.changeView(content, type, null, SubViewContainer::overlay);
    }

    protected final <T> void overlayView(final SubView content, final ViewType type, final T viewModel) {
        this.changeView(content, type, viewModel, SubViewContainer::overlay);
    }

    protected final void previousView(final SubView content) {
        content.getContainer()
               .ifPresent(c -> c.previous()
                                .peekError(err -> new Alert(Alert.AlertType.WARNING, "No previous found").showAndWait()));
    }
}
