package nwoolcan.viewmodel.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;

import java.util.Date;

/**
 * DTO representing a batch in its master table.
 */
public class MasterBatchViewModel {

    private final Batch batch;

    /**
     * Constructor with decorator.
     * @param batch the batch to use a data.
     */
    public MasterBatchViewModel(final Batch batch) {
        this.batch = batch;
    }

    /**
     * Returns the batch id.
     * @return the batch id.
     */
    public int getId() {
        return this.batch.getId();
    }

    /**
     * Returns the batch beer description name.
     * @return the batch beer description name.
     */
    public String getBeerDescriptionName() {
        return this.batch.getBatchInfo().getBeerDescription().getName();
    }

    /**
     * Returns the batch beer style name.
     * @return the batch beer style name.
     */
    public String getBeerStyleName() {
        return this.batch.getBatchInfo().getBeerDescription().getStyle();
    }

    /**
     * Returns the batch batch method name.
     * @return the batch batch method name.
     */
    public String getBatchMethodName() {
        return this.batch.getBatchInfo().getMethod().getName();
    }

    /**
     * Returns the batch current step name.
     * @return the batch current step name.
     */
    public String getCurrentStepName() {
        return this.batch.getCurrentStep().getStepInfo().getType().getName();
    }

    /**
     * Returns the batch start date.
     * @return the batch start date.
     */
    public Date getStartDate() {
        return this.batch.getSteps().stream().findFirst().get().getStepInfo().getStartDate();
    }

    /**
     * Returns a {@link QuantityViewModel} representing the batch initial size.
     * @return the batch initial size.
     */
    public QuantityViewModel getInitialBatchSize() {
        return new QuantityViewModel(this.batch.getBatchInfo().getBatchSize());
    }

    /**
     * Returns a {@link QuantityViewModel} representing the batch current size.
     * @return the batch current size.
     */
    public QuantityViewModel getCurrentBatchSize() {
        return new QuantityViewModel(this.batch.getCurrentSize());
    }

    /**
     * Returns true if the batch is in ended state, false otherwise.
     * @return true if the batch is in ended state, false otherwise.
     */
    public boolean isEnded() {
        return this.batch.isEnded();
    }
}
