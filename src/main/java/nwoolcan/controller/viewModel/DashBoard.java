package nwoolcan.controller.viewModel;

/**
 * View-Model interface with methods callable to obtain general information about the warehouse.
 * There will be information about:
 * <ul>
 *     <li>Number of {@link nwoolcan.model.brewery.production.batch.Batch} which are in progress;</li>
 *     <li>{@link nwoolcan.model.brewery.warehouse.stock.Stock} which are going to expire;</li>
 *     <li>{@link nwoolcan.model.brewery.warehouse.stock.Stock} which have a low {@link nwoolcan.model.utils.Quantity}.</li>
 * </ul>
 */
public interface DashBoard {

    /**
     * Returns the number of ongoing {@link nwoolcan.model.brewery.production.batch.Batch}.
     * @return the number of ongoing {@link nwoolcan.model.brewery.production.batch.Batch}.
     */
    int getNumberOfOngoingBatches();


}
