package nwoolcan.view.filters;

import javafx.beans.NamedArg;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import nwoolcan.view.utils.DatePickerItalianConverter;

import java.time.ZoneId;
import java.util.Date;

/**
 * Filter with a date value.
 */
public class DateFilter extends VBox {
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
    public Date getValue() {
        return Date.from(field.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
