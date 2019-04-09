package nwoolcan.controller.brewery;

import nwoolcan.controller.brewery.warehouse.WarehouseController;

/**
 * General controller of the {@link nwoolcan.model.brewery.Brewery}.
 */
public interface BreweryController {

    /**
     * Return the {@link WarehouseController}.
     * @return the {@link WarehouseController}.
     */
    WarehouseController getWarehouseController();

}
