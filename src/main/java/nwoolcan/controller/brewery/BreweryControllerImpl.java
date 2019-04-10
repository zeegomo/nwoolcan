package nwoolcan.controller.brewery;

import nwoolcan.controller.brewery.warehouse.WarehouseController;
import nwoolcan.controller.brewery.warehouse.WarehouseControllerImpl;
import nwoolcan.model.brewery.Brewery;

/**
 * {@link BreweryController} implementation.
 */
public final class BreweryControllerImpl implements BreweryController {

    private final WarehouseController warehouseController;
    private final Brewery brewery;
    /**
     * Constructor which creates the {@link WarehouseController}.
     * @param brewery used to create the {@link WarehouseController}.
     */
    public BreweryControllerImpl(final Brewery brewery) {
        this.brewery = brewery;
        this.warehouseController = new WarehouseControllerImpl(brewery.getWarehouse());
    }

    @Override
    public WarehouseController getWarehouseController() {
        return warehouseController;
    }
}
