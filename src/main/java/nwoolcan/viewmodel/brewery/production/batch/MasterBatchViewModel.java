package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.utils.Quantity;

import java.util.Date;

/**
 * DTO representing a batch in its master table.
 */
public class MasterBatchViewModel {

    private final int id;
    private final String beerDescriptionName;
    private final String beerStyleName;
    private final String batchMethodName;
    private final String currentStepName;
    private final Date startDate;
    private final Quantity initialBatchSize;
    private final Quantity currentBatchSize;
    private final boolean isEnded;

    /**
     * Constructor with decorator.
     * @param batch the batch to use a data.
     */
    public MasterBatchViewModel(final Batch batch) {
        this.id = batch.getId();
        this.beerDescriptionName = batch.getBatchInfo().getBeerDescription().getName();
        this.beerStyleName = batch.getBatchInfo().getBeerDescription().getStyle();
        this.batchMethodName = batch.getBatchInfo().getMethod().getName();
        this.currentStepName = batch.getCurrentStep().getStepInfo().getType().getName();
        this.startDate = batch.getSteps().stream().findFirst().get().getStepInfo().getStartDate();
        this.initialBatchSize = batch.getBatchInfo().getBatchSize();
        this.currentBatchSize = batch.getCurrentSize();
        this.isEnded = batch.isEnded();
    }

    /**
     * Returns the batch id.
     * @return the batch id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the batch beer description name.
     * @return the batch beer description name.
     */
    public String getBeerDescriptionName() {
        return this.beerDescriptionName;
    }

    /**
     * Returns the batch beer style name.
     * @return the batch beer style name.
     */
    public String getBeerStyleName() {
        return this.beerStyleName;
    }

    /**
     * Returns the batch batch method name.
     * @return the batch batch method name.
     */
    public String getBatchMethodName() {
        return this.batchMethodName;
    }

    /**
     * Returns the batch current step name.
     * @return the batch current step name.
     */
    public String getCurrentStepName() {
        return this.currentStepName;
    }

    /**
     * Returns the batch start date.
     * @return the batch start date.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Returns a {@link Quantity} representing the batch initial size.
     * @return the batch initial size.
     */
    public Quantity getInitialBatchSize() {
        return this.initialBatchSize;
    }

    /**
     * Returns a {@link Quantity} representing the batch current size.
     * @return the batch current size.
     */
    public Quantity getCurrentBatchSize() {
        return this.currentBatchSize;
    }

    /**
     * Returns true if the batch is in ended state, false otherwise.
     * @return true if the batch is in ended state, false otherwise.
     */
    public boolean isEnded() {
        return this.isEnded;
    }
}
