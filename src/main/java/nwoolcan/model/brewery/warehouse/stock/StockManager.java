package nwoolcan.model.brewery.warehouse.stock;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager for {@link Stock} objects. It is used by tests and by the
 * {@link nwoolcan.model.brewery.warehouse.Warehouse} in order to create {@link Stock}, check the
 * id of the {@link Stock}, avoid repetitions and set name of the {@link Stock}.
 */
public final class StockManager {

    @Nullable private static StockManager instance;
    private final Map<Stock, Integer> stockToId;
    private final Map<Integer, Stock> idToStock;
    private static final int FAKE_ID = -1;
    private int nextAvailableId;

    private StockManager() {
        nextAvailableId = 1;
        stockToId = new HashMap<>();
        idToStock = new HashMap<>();
    }
    /**
     * Returns the only instance of the {@link StockManager} using a singleton pattern.
     * @return the only instance of the {@link StockManager} using a singleton pattern.
     */
    public static synchronized StockManager getInstance() {
        if (instance == null) {
            instance = new StockManager();
        }
        return instance;
    }
    /**
     * Checks the consistency of the {@link Stock}.
     * @param stock to be checked.
     * @return a boolean denoting whether the id is correct or not.
     */
    public synchronized boolean checkId(final Stock stock) {
        return true; // TODO remove comment and use the other check.
        //return stockToId.containsKey(stock) && stock.getId().equals(stockToId.get(stock));
    }

}
