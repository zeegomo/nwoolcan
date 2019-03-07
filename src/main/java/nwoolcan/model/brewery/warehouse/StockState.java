package nwoolcan.model.brewery.warehouse;

/**
 * Contains the possible state of the Stock.
 */
public enum StockState {

    /**
     * In production, available to be used and already consumed.
     */
    AVAILABLE, USED_UP, EXPIRED;

}
