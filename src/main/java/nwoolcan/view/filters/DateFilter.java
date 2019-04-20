package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import nwoolcan.view.utils.DatePickerItalianConverter;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Filter with a date value.
 */
public class DateFilter extends VBox implements GUIFilter<Date> {
    private final DatePicker field = new DatePicker();

    /**
     * Create a new TextFilter.
     * @param title the title of the filter.
     */
    public DateFilter(@NamedArg("title") final String title) {
        this.getChildren().add(new Label(title));
        this.getChildren().add(field);

        this.field.setConverter(new DatePickerItalianConverter());
    }

    /**
     * Returns the filter's value.
     * @return the filter's value.
     */
    @Override
    public Optional<Date> getValue() {
        return Optional.ofNullable(field.getValue()).map(v -> Date.from(v.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
