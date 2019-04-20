package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.utils.StringUtils;

/**
 * Contains the possible state of the Stock.
 */
public enum StockState {

    /**
     * In production, available to be used and already consumed.
     */
    AVAILABLE, USED_UP, EXPIRED;

    @Override
    public String toString() {
        return StringUtils.underscoreSeparatedToHuman(super.toString());
    }

}
