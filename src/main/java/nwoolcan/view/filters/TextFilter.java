package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Filter with a string value.
 */
public class TextFilter extends VBox {
    private final TextField field = new TextField();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     */
    public TextFilter(@NamedArg("title") final String title) {
        this.getChildren().add(new Label(title));
        this.getChildren().add(field);
    }

    /**
     * Returns the filter's value.
     * @return the filter's value.
     */
    public String getValue() {
        return this.field.getText();
    }
}
