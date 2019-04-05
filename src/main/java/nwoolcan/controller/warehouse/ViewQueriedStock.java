package nwoolcan.controller.warehouse;

import nwoolcan.controller.warehouse.stock.ViewStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;

import java.util.List;

/**
 * Model of the view of the list of the stocks.
 */
public interface ViewQueriedStock {

    /**
     * Returns a list of {@link ViewStock}, accordingly with the {@link QueryStock}.
     * @param queryStock the {@link QueryStock}.
     * @return a list of {@link ViewStock}, accordingly with the {@link QueryStock}.
     */
    List<ViewStock> getLastStocks(QueryStock queryStock);

}
