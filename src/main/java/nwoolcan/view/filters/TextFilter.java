package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Filter with a string value.
 */
public final class TextFilter extends GUIFilter<String> {
    private final TextField field = new TextField();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     */
    public TextFilter(@NamedArg("title") final String title) {
        final VBox container = new VBox();
        this.getChildren().add(container);
        this.getLabel().setText(title);
        container.getChildren().add(this.getLabel());
        container.getChildren().add(field);
    }

    @Override
    public Optional<String> getValue() {
        return Optional.of(this.field.getText())
                       .map(String::trim)
                       .filter(v -> !v.isEmpty());
    }

    @Override
    void resetValue() {
        this.field.setText("");
    }
}
