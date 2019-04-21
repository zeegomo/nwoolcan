package nwoolcan.viewmodel.brewery.production;

import nwoolcan.viewmodel.brewery.production.batch.MasterBatchViewModel;
import nwoolcan.model.brewery.batch.Batch;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DTO representing the view model for the production general view.
 */
public class ProductionViewModel {

    private final List<MasterBatchViewModel> batches;
    private final Map<String, Long> methodsFrequency;
    private final long nBatches;
    private final long nInProgressBatches;
    private final long nEndedBatches;
    private final long nEndedNotStockedBatches;
    private final long nStockedBatches;

    /**
     * Basic constructor with a collection of batches.
     * @param batches the batches to represent in the production view.
     */
    public ProductionViewModel(final Collection<Batch> batches) {
        this.batches = batches.stream()
                              .map(MasterBatchViewModel::new)
                              .collect(Collectors.toList());
        this.methodsFrequency = batches.stream()
                                       .map(b -> b.getBatchInfo().getMethod().getName())
                                       .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        this.nBatches = batches.size();
        this.nInProgressBatches = batches.stream()
                                         .filter(b -> !b.isEnded())
                                         .count();
        this.nEndedBatches = batches.stream()
                                    .filter(Batch::isEnded)
                                    .count();
        this.nEndedNotStockedBatches = batches.stream()
                                              .filter(b -> b.isEnded() && !b.isStocked())
                                              .count();
        this.nStockedBatches = batches.stream()
                                      .filter(Batch::isStocked)
                                      .count();
    }

    /**
     * Returns a list of all batches in production.
     * @return a list of all batches in production.
     */
    public List<MasterBatchViewModel> getBatches() {
        return this.batches;
    }

    /**
     * Returns a map containing, for each style, how many batches have that style.
     * @return a map containing, for each style, how many batches have that style.
     */
    public Map<String, Long> getMethodsFrequency() {
        return Collections.unmodifiableMap(this.methodsFrequency);
    }

    /**
     * Returns the total number of batches.
     * @return the total number of batches.
     */
    public long getNBatches() {
        return this.nBatches;
    }

    /**
     * Returns the number of batches that are still in progress.
     * @return the number of batches that are still in progress.
     */
    public long getNInProgressBatches() {
        return this.nInProgressBatches;
    }

    /**
     * Returns the number of ended batches.
     * @return the number of ended batches.
     */
    public long getNEndedBatches() {
        return this.nEndedBatches;
    }

    /**
     * Returns the number of ended batches, but not stocked.
     * @return the number of ended batches, but not stocked.
     */
    public long getNEndedNotStockedBatches() {
        return this.nEndedNotStockedBatches;
    }

    /**
     * Returns the number of stocked batches.
     * @return the number of stocked batches.
     */
    public long getNStockedBatches() {
        return this.nStockedBatches;
    }
}
