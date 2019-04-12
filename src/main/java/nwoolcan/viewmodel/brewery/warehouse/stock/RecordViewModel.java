package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.utils.Quantity;

import java.util.Date;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
 */
public final class RecordViewModel {

    private final Quantity quantity;
    private final boolean isAdding;
    private final Date date;

    /**
     * Constructor of the View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
     * @param record to be converted in {@link RecordViewModel}
     */
    public RecordViewModel(final Record record) {
        this.quantity = record.getQuantity();
        this.isAdding = record.getAction() == Record.Action.ADDING;
        this.date = record.getDate();
    }
    /**
     * Return the amount of the transfer in or out the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @return the amount of the transfer in or out the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     */
    public Quantity getQuantity() {
        return quantity;
    }
    /**
     * Return a {@link Boolean} which is true if the {@link Quantity} has to be added.
     * @return a {@link Boolean} which is true if the {@link Quantity} has to be added.
     */

    public boolean isAdding() {
        return isAdding;
    }
    /**
     * Return the {@link Date} in which the {@link nwoolcan.model.brewery.warehouse.stock.Record} has been transferred.
     * @return the {@link Date} in which the {@link nwoolcan.model.brewery.warehouse.stock.Record} has been transferred.
     */
    public Date getDate() {
        return new Date(date.getTime());
    }
}
