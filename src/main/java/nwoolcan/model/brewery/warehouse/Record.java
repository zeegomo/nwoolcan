package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.utils.Quantity;

import java.util.Date;
import java.util.Objects;

/**
 * Class which keeps track of a {@link Quantity} and a {@link Date}.
 */
public final class Record {

    private final Quantity quantity;
    private final Date date;

    /**
     * Constructor with all parameters.
     * @param quantity to be recorded.
     * @param date in which the recorded happened.
     */
    public Record(final Quantity quantity, final Date date) {
        this.quantity = Objects.requireNonNull(quantity);
        this.date = Objects.requireNonNull(date);
    }
    /**
     * Constructor which auto instantiate the current {@link Date}.
     * @param quantity to be recorded.
     */
    public Record(final Quantity quantity) {
        this.quantity = quantity;
        this.date = new Date();
    }
    /**
     * Getter for the date.
     * @return the date of the record.
     */
    Date getDate() {
        return (Date) this.date.clone();
    }
    /**
     * Getter for the quantity.
     * @return the quantity of the record.
     */
    Quantity getQuantity() {
        return this.quantity;
    }

}
