package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import nwoolcan.view.utils.DatePickerItalianConverter;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Filter with a date value.
 */
public final class DateFilter extends GUIFilter<Date> {
    private final DatePicker field = new DatePicker();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     */
    public DateFilter(@NamedArg("title") final String title) {
        final VBox container = new VBox();
        this.getLabel().setText(title);
        container.getChildren().add(this.getLabel());
        container.getChildren().add(field);
        this.getChildren().add(container);

        this.field.setConverter(new DatePickerItalianConverter());
    }

    @Override
    public Optional<Date> getValue() {
        return Optional.ofNullable(field.getValue()).map(v -> Date.from(v.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    void resetValue() {
        this.field.setValue(null);
    }
}
