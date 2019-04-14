package nwoolcan.view.utils.subview;

import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/**
 * This is a container that can handle multiple overlays. You can put views on top ov others and then pop them to view the old ones (like a stack).
 * This works well together with {@link SubView}: in that case your SubView can reference its SubViewContainer.
 */
public class SubViewContainer extends AnchorPane {
    private Deque<Parent> overlays = new ArrayDeque<>();
    /**
     * The optional parent of this container.
     * If set, this container is considered transparent and his children believe they are children of parent.
     */
    @Nullable
    private SubView parent;

    /**
     * Default constructor.
     */
    public SubViewContainer() { }

    /**
     * Builds a transparent SubViewContainer: his children believe they are children of another container (parent).
     * @param parent The "fake" parent of the children of this container
     */
    public SubViewContainer(@NamedArg("parent") final SubView parent) {
        this.parent = parent;
    }

    /**
     * Shows the last overlay in the stack.
     */
    private void setCurrentView() {
        if (this.overlays.size() == 0) {
            this.setCurrentView(null);
        } else {
            this.setCurrentView(this.overlays.peek());
        }
    }

    /**
     * Shows the given view inside.
     * @param view The view to show
     */
    private void setCurrentView(@Nullable final Parent view) {
        this.getChildren().clear();
        if (view != null) {
            this.getChildren().add(view);
        }
    }

    /**
     * Adds another view on top of all the others: they still exist but are not visible.
     * @param view The new overlay
     */
    public void overlay(final Parent view) {
        this.overlays.push(view);
        if (view instanceof SubView) {
            final SubViewContainer container = Optional.ofNullable(this.parent)
                                                       .map(c -> (SubViewContainer) new TransparentSubViewContainer(c))
                                                       .orElse(this);
            ((SubView) view).setContainer(container);
        }
        this.setCurrentView();
    }

    /**
     * Sets the current view discarding all the previous overlays.
     * @param view The new view
     */
    public void substitute(final Parent view) {
        this.overlays.clear();
        this.overlay(view);
    }

    /**
     * Removes the last overlay and show the previous one.
     * @return A result with an error when trying to remove the last view
     */
    public Result<Empty> previous() {
        if (this.overlays.size() == 0) {
            return Result.error(new IllegalStateException("You were trying to remove the last view"));
        }
        this.overlays.pop();
        this.setCurrentView();
        return Result.ofEmpty();
    }

    /**
     * To get the number of overlays in this container.
     * @return The number of overlays in this container
     */
    public int getOverlaysCount() {
        return this.overlays.size();
    }
}
