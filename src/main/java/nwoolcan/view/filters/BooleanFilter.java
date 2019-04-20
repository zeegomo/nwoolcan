package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Filter with a boolean value.
 */
public class BooleanFilter extends HBox {
    private static final int SPACING = 3;
    private final CheckBox field = new CheckBox();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     */
    public BooleanFilter(@NamedArg("title") final String title) {
        super(SPACING);
        this.getChildren().add(field);
        this.getChildren().add(new Label(title));
    }

    /**
     * Returns the filter's value.
     * @return the filter's value.
     */
    public boolean getValue() {
        return this.field.isSelected();
    }
}
