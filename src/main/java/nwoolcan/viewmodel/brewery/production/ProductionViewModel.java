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

    private final Collection<Batch> batches;

    /**
     * Basic constructor with a collection of batches.
     * @param batches the batches to represent in the production view.
     */
    public ProductionViewModel(final Collection<Batch> batches) {
        this.batches = Collections.unmodifiableCollection(batches);
    }

    /**
     * Returns a list of all batches in production.
     * @return a list of all batches in production.
     */
    public List<MasterBatchViewModel> getBatches() {
        return this.batches.stream()
                           .map(MasterBatchViewModel::new)
                           .collect(Collectors.toList());
    }

    /**
     * Returns a map containing, for each style, how many batches have that style.
     * @return a map containing, for each style, how many batches have that style.
     */
    public Map<String, Long> getStylesFrequency() {
        return this.batches.stream()
                           .map(b -> b.getBatchInfo().getBeerDescription().getStyle())
                           .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }

    /**
     * Returns the total number of batches.
     * @return the total number of batches.
     */
    public long getNBatches() {
        return this.batches.size();
    }

    /**
     * Returns the number of batches that are still in progress.
     * @return the number of batches that are still in progress.
     */
    public long getNInProgressBatches() {
        return this.batches.stream()
                           .filter(b -> !b.isEnded())
                           .count();
    }

    /**
     * Returns the number of ended batches.
     * @return the number of ended batches.
     */
    public long getNEndedBatches() {
        return this.batches.stream()
                           .filter(Batch::isEnded)
                           .count();
    }
}
