package nwoolcan.controller.viewmodel;

/**
 * Data used inside a Warehouse view.
 */
public final class WarehouseViewModel {
    private String name;

    /**
     * Sets the name of this object.
     * @param name The name
     */
    public WarehouseViewModel(final String name) {
        this.name = name;
    }

    /**
     * Gets the name of this object.
     * @return The name
     */
    public String getName() {
        return this.name;
    }
}
