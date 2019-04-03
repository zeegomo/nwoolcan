package nwoolcan.view.subview;

import javafx.scene.layout.Pane;

import javax.annotation.Nullable;
import java.util.Optional;

public class SubView extends Pane {
    @Nullable
    private SubViewContainer container;

    // Package private
    void setContainer(final SubViewContainer container) {
        this.container = container;
    }

    public Optional<SubViewContainer> getContainer() {
        return Optional.ofNullable(this.container);
    }

}
