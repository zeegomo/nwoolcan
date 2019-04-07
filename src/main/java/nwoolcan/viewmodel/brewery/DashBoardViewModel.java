package nwoolcan.viewmodel.brewery;

/**
 * View-Model interface with methods callable to obtain general information about the warehouse.
 * There will be information about:
 * <ul>
 *     <li>Number of {@link nwoolcan.model.brewery.production.batch.Batch} which are in progress;</li>
 *     <li>{@link nwoolcan.model.brewery.warehouse.stock.Stock} which are going to expire;</li>
 *     <li>{@link nwoolcan.model.brewery.warehouse.stock.Stock} which have a low {@link nwoolcan.model.utils.Quantity}.</li>
 * </ul>
 */
public final class DashBoardViewModel {

    private final int numberOfOngoingBatches;

    /**
     * Constructor of the model of main view of the application.
     * @param numberOfOngoingBatches the number of batches currently started which are not finished yet.
     */
    public DashBoardViewModel(final int numberOfOngoingBatches) {
        this.numberOfOngoingBatches = numberOfOngoingBatches;
    }

    /**
     * Returns the number of ongoing {@link nwoolcan.model.brewery.production.batch.Batch}.
     * @return the number of ongoing {@link nwoolcan.model.brewery.production.batch.Batch}.
     */
    public int getNumberOfOngoingBatches() {
        return numberOfOngoingBatches;
    }


}
