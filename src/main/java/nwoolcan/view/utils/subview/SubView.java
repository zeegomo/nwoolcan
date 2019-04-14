package nwoolcan.view.utils.subview;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * This is a container that can be put inside a {@link SubViewContainer}.
 */
public final class SubView extends ScrollPane {
    @Nullable
    private SubViewContainer container;

    /**
     * Basic constructor for fitting the SubView to its container.
     */
    public SubView() {
        super();
        this.setFitToWidth(true);
        AnchorPane.setRightAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setBottomAnchor(this, 0.0);
    }

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
