package nwoolcan.viewmodel.warehouse.stock;

import nwoolcan.model.utils.Quantity;

import java.util.Date;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
 */
public final class RecordViewModel {

    private final Quantity quantity;
    private final Boolean isAdding;
    private final Date date;

    /**
     * Constructor of the View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
     * @param quantity of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
     * @param isAdding is true if the quantity has to be added to the total of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
     * @param date of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
     */
    public RecordViewModel(final Quantity quantity, final Boolean isAdding, final Date date) {
        this.quantity = quantity;
        this.isAdding = isAdding;
        this.date = date;
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
    public Boolean isAdding() {
        return isAdding;
    }
    /**
     * Return the {@link Date} in which the {@link nwoolcan.model.brewery.warehouse.stock.Record} has been transferred.
     * @return the {@link Date} in which the {@link nwoolcan.model.brewery.warehouse.stock.Record} has been transferred.
     */
    public Date getDate() {
        return date;
    }
}
