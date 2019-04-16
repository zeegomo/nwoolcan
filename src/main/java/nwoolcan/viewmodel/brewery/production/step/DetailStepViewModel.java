package nwoolcan.viewmodel.brewery.production.step;

import nwoolcan.model.brewery.batch.step.Step;
import nwoolcan.model.brewery.batch.step.parameter.ParameterType;
import nwoolcan.model.brewery.batch.step.parameter.QueryParameterBuilder;
import nwoolcan.viewmodel.brewery.utils.QuantityViewModel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * View model representing a {@link Step} in detail.
 */
public final class DetailStepViewModel {

    private final int batchId;
    private final String typeName;
    private final String notes;
    private final Date startDate;

    @Nullable
    private final Date endDate;
    @Nullable
    private final QuantityViewModel endSize;

    private final boolean finalized;

    private final List<ParameterViewModel> registeredParameters;
    private final List<ParameterType> possibleParametersToRegister;

    /**
     * Basic constructor with decorator-like pattern.
     * @param batchId the step's batch id.
     * @param step the step to get data from.
     */
    public DetailStepViewModel(final int batchId, final Step step) {
        this.batchId = batchId;
        this.typeName = step.getStepInfo().getType().getName();
        this.notes = step.getStepInfo().getNote().orElse("");
        this.startDate = new Date(step.getStepInfo().getStartDate().getTime());
        this.endDate = step.getStepInfo().getEndDate().map(d -> new Date(d.getTime())).orElse(null);
        this.endSize = step.getStepInfo().getEndStepSize().map(QuantityViewModel::new).orElse(null);
        this.finalized = step.isFinalized();
        this.registeredParameters = step.getParameters(new QueryParameterBuilder().sortByDate(true)
                                                                                  .sortDescending(true)
                                                                                  .build().getValue())
                                        .stream()
                                        .map(ParameterViewModel::new)
                                        .collect(Collectors.toList());
        this.possibleParametersToRegister = new ArrayList<>(step.getParameterTypes());
    }

    /**
     * Returns this step's batch id.
     * @return this step's batch id.
     */
    public int getBatchId() {
        return this.batchId;
    }

    /**
     * Returns the step type name.
     * @return the step type name.
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * Returns the registered notes (string empty if none).
     * @return the registered notes.
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Returns the start date of the step.
     * @return the start date of the step.
     */
    public Date getStartDate() {
        return new Date(this.startDate.getTime());
    }

    /**
     * Returns the end date of the step (null if none).
     * @return the end date of the step.
     */
    @Nullable
    public Date getEndDate() {
        return Optional.ofNullable(this.endDate).map(d -> new Date(d.getTime())).orElse(null);
    }

    /**
     * Returns a {@link QuantityViewModel} representing the end size of the step (null if none).
     * @return the end size of the step.
     */
    @Nullable
    public QuantityViewModel getEndSize() {
        return this.endSize;
    }

    /**
     * Returns true if the step is finalized, false otherwise.
     * @return true if the step is finalized, false otherwise.
     */
    public boolean isFinalized() {
        return finalized;
    }

    /**
     * Returns a list of {@link ParameterViewModel} representing the registered parameters in the step.
     * @return the registered parameters in the step.
     */
    public List<ParameterViewModel> getRegisteredParameters() {
        return this.registeredParameters;
    }

    /**
     * Returns all the possible parameters type that can be registered in this step.
     * @return all the possible parameters type that can be registered in this step.
     */
    public List<ParameterType> getPossibleParametersToRegister() {
        return possibleParametersToRegister;
    }
}
