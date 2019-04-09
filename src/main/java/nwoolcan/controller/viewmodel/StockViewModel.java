package nwoolcan.controller.viewmodel;

/**
 * Dummy class for stock view model.
 */
public final class StockViewModel {

    private final int id;
    private final String name;


    /**
     * Dummy constructor.
     * @param id dummy id.
     * @param name dummy name.
     */
    public StockViewModel(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the stock id.
     * @return the stock id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the stock name.
     * @return the stock name.
     */
    public String getName() {
        return name;
    }
}
