package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Optional;

/**
 * Filter with a boolean value.
 */
public final class BooleanFilter extends GUIFilter<Boolean> {
    private static final int SPACING = 3;
    private final CheckBox field = new CheckBox();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     */
    public BooleanFilter(@NamedArg("title") final String title) {
        final HBox container = new HBox(SPACING);
        this.getChildren().add(new HBox());
        container.getChildren().add(field);
        container.getChildren().add(new Label(title));
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
