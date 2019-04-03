package nwoolcan.view.subview;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;

public class SubViewContainer extends Pane {
    private Deque<Parent> overlays = new ArrayDeque<>();

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
            ((SubView)view).setContainer(this);
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
     * To get the number of overlays in this container
     * @return The number of overlays in this container
     */
    public int getOverlaysCount() {
        return this.overlays.size();
    }
}
