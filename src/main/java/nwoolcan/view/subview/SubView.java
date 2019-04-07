package nwoolcan.view.subview;

import javafx.scene.layout.AnchorPane;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * This is a container that can be put inside a {@link SubViewContainer}.
 */
public final class SubView extends AnchorPane {
    @Nullable
    private SubViewContainer container;

    // Package private
    void setContainer(final SubViewContainer container) {
        this.container = container;
    }

    /**
     * Gets the {@link SubViewContainer} (if any) of this SubView.
     * @return the {@link SubViewContainer} of this SubView, or an empty {@link Optional}
     */
    public Optional<SubViewContainer> getContainer() {
        return Optional.ofNullable(this.container);
    }

}
