package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

import java.util.Optional;

/**
 * Filter with a boolean value.
 */
public final class BooleanFilter extends GUIFilter<Boolean> {
    private static final int SPACING = 3;
    private static final int TOP_PADDING = 20;
    private final CheckBox field = new CheckBox();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     */
    public BooleanFilter(@NamedArg("title") final String title) {
        final HBox container = new HBox(SPACING);
        container.getChildren().add(field);
        this.getLabel().setText(title);
        container.getChildren().add(this.getLabel());
        container.setPadding(new Insets(TOP_PADDING, 0, 0, 0));
        this.getChildren().add(container);
    }

    @Override
    public Optional<Boolean> getValue() {
        return Optional.of(this.field.isSelected());
    }

    @Override
    void resetValue() {
        this.field.setSelected(false);
    }
}
