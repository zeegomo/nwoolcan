package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

/**
 * Filter with a date value.
 * @param <T> the type of the items inside the combobox.
 */
public final class SelectFilter<T> extends GUIFilter<T> {
    private final ComboBox<T> field = new ComboBox<>();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     * @param placeholder the text tu put inside the combobox when nothing is selected.
     * @param items the items tu put inside the combobox.
     */
    public SelectFilter(@NamedArg("title") final String title, @NamedArg("placeholder") final String placeholder, @NamedArg("items") final List<T> items) {
        final VBox container = new VBox();
        this.getChildren().add(container);
        container.getChildren().add(new Label(title));
        container.getChildren().add(this.field);
        this.field.getItems().addAll(items);
        this.field.setPromptText(placeholder);
    }

    @Override
    public Optional<T> getValue() {
        return Optional.ofNullable(this.field.getValue());
    }

    @Override
    void resetValue() {
        this.field.setValue(null);
    }
}
