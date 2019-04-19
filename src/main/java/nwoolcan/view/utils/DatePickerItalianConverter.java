package nwoolcan.view.utils;

import javafx.util.StringConverter;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DatePickerItalianConverter extends StringConverter<LocalDate> {
    private static final String FORMAT_PATTERN = "dd/MM/yyyy";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(FORMAT_PATTERN);

    @Override
    public String toString(final LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    @Override
    public @Nullable LocalDate fromString(final String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}
