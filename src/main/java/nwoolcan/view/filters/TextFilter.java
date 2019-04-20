package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Filter with a string value.
 */
public class TextFilter extends VBox implements GUIFilter<String> {
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
    @Override
    public Optional<String> getValue() {
        return Optional.of(this.field.getText())
                       .map(String::trim)
                       .filter(v -> !v.isEmpty());
    }
}
