package nwoolcan.viewmodel.brewery.warehouse.stock;

import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
 */
public final class RecordViewModel {

    private final QuantityViewModel quantity;
    private final Record.Action action;
    private final Date date;

    /**
     * Constructor of the View-Model representation of the {@link nwoolcan.model.brewery.warehouse.stock.Record}.
     * @param record to be converted in {@link RecordViewModel}
     */
    public RecordViewModel(final Record record) {
        this.quantity = new QuantityViewModel(record.getQuantity());
        this.action = record.getAction();
        this.date = record.getDate();
    }
    /**
     * Return the amount of the transfer in or out the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @return the amount of the transfer in or out the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     */
    public QuantityViewModel getQuantity() {
        return quantity;
    }
    /**
     * Return a {@link Boolean} which is true if the {@link nwoolcan.model.utils.Quantity} has to be added.
     * @return a {@link Boolean} which is true if the {@link nwoolcan.model.utils.Quantity} has to be added.
     */

    public Record.Action getAction() {
        return action;
    }
    /**
     * Return the {@link Date} in which the {@link nwoolcan.model.brewery.warehouse.stock.Record} has been transferred.
     * @return the {@link Date} in which the {@link nwoolcan.model.brewery.warehouse.stock.Record} has been transferred.
     */
    public String getDate() {
        return dateFormatted(date);
    }

    static String dateFormatted(final Date date) {
        return DateFormatUtils.format(date, "dd-MM-yyyy HH:mm");
    }
}
