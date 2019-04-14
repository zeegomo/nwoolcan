package nwoolcan.view.utils.subview;

import javafx.beans.NamedArg;
import javafx.scene.Parent;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Contains a SubView and every call to methods are forwarded to the SubView's container.
 */
class TransparentSubViewContainer extends SubViewContainer {
    @Nullable
    private SubView container;

    TransparentSubViewContainer(@NamedArg("container") final SubView container) {
        this.container = container;
    }

    @Override
    public void overlay(final Parent view) {
        Optional.ofNullable(this.container)
                .flatMap(SubView::getContainer)
                .ifPresent(c -> c.overlay(view));
    }

    @Override
    public void substitute(final Parent view) {
        Optional.ofNullable(this.container)
                .flatMap(SubView::getContainer)
                .ifPresent(c -> c.substitute(view));
    }

    @Override
    public Result<Empty> previous() {
        return Optional.ofNullable(this.container)
                       .flatMap(SubView::getContainer)
                       .map(c -> c.previous())
                       .orElse(Result.ofEmpty());
    }
}
