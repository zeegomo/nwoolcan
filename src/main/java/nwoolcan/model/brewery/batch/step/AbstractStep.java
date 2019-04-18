package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.batch.step.parameter.QueryParameter;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Observer;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract implementation of Step interface.
 */
abstract class AbstractStep implements Step {

    private static final String ALREADY_FINALIZED_MESSAGE = "This step is already finalized.";
    private static final String CANNOT_REGISTER_PARAMETER_MESSAGE = "Cannot register parameter if the step is finalized.";
    private static final String INVALID_PARAMETER_MESSAGE = "The parameter type is invalid for this step.";

    private final ModifiableStepInfo stepInfo;
    private boolean finalized;
    private final Collection<Parameter> parameters;
    private final Collection<Observer<Parameter>> observers;

    //Package-private constructor only for inheritance.
    AbstractStep(final ModifiableStepInfo stepInfo) {
        this.stepInfo = stepInfo;
        this.finalized = stepInfo.getType().isEndType();
        this.parameters = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Returns this object's step info that can be changed.
     * Use this method for changing step infos properties by a subclass.
     * @return this object's step info that can be changed.
     */
    protected final ModifiableStepInfo getModifiableStepInfo() {
        return this.stepInfo;
    }

    /**
     * Performs a check to know if this step can be finalized with this data.
     * Returns a {@link Result} with en error if this step cannot be finalized with this finalization data.
     * @param note the finalization note.
     * @param endDate the finalization end date.
     * @param remainingSize the finalization remaining size.
     * @return a {@link Result} with errors if this step cannot be finalized with this finalization data.
     */
    protected abstract Result<Empty> checkFinalizationData(@Nullable String note, Date endDate, Quantity remainingSize);

    /**
     * Performs a check to know if this parameter can be registered in this step.
     * Returns a {@link Result} with en error if if this parameter cannot be registered in this step.
     * @param parameter the registering parameter
     * @return a {@link Result} with en error if if this parameter cannot be registered in this step.
     */
    protected abstract Result<Empty> checkRegisteringParameter(Parameter parameter);

    @Override
    public final StepInfo getStepInfo() {
        return new StepInfoImpl(this.stepInfo);
    }

    @Override
    public final boolean isFinalized() {
        return this.finalized;
    }

    @Override
    public final Result<Empty> finalize(@Nullable final String note, final Date endDate, final Quantity remainingSize) {
        return this.checkFinalizationData(note, endDate, remainingSize)
                   .require(() -> !this.isFinalized(), new IllegalStateException(ALREADY_FINALIZED_MESSAGE))
                   .flatMap(() -> this.stepInfo.setEndDate(endDate))
                   .peek(e -> {
                       this.stepInfo.setNote(note);
                       this.stepInfo.setEndStepSize(remainingSize);
                       this.finalized = true;
                   });
    }

    @Override
    public final Collection<Parameter> getParameters(final QueryParameter query) {
        Stream<Parameter> s = this.parameters.stream();
        final List<Predicate<Parameter>> filters = new ArrayList<>();

        query.getParameterType().ifPresent(pt -> filters.add(p -> p.getType().equals(pt)));
        query.getGreaterThanValue().ifPresent(gtv -> filters.add(p -> p.getRegistrationValue().doubleValue() >= gtv.doubleValue()));
        query.getLessThanValue().ifPresent(ltv -> filters.add(p -> p.getRegistrationValue().doubleValue() <= ltv.doubleValue()));
        query.getExactValue().ifPresent(ev -> filters.add(p -> p.getRegistrationValue().doubleValue() == ev.doubleValue()));
        query.getStartDate().ifPresent(sd -> filters.add(p -> !sd.after(p.getRegistrationDate())));
        query.getEndDate().ifPresent(ed -> filters.add(p -> !ed.before(p.getRegistrationDate())));

        for (final Predicate<Parameter> f : filters) {
            s = s.filter(f);
        }

        if (query.isSortByValue()) {
            s = s.sorted(Comparator.comparingDouble(d -> (query.isSortDescending() ? -1 : 1) * d.getRegistrationValue().doubleValue()));
        }

        if (query.isSortByDate()) {
            s = s.sorted(Comparator.comparingLong(d -> (query.isSortDescending() ? -1 : 1) * d.getRegistrationDate().getTime()));
        }

        return s.collect(Collectors.toList());
    }

    @Override
    public final Result<Empty> registerParameter(final Parameter parameter) {
        return this.checkRegisteringParameter(parameter)
                   .map(e -> parameter)
                   .require(() -> !this.isFinalized(), new IllegalStateException(CANNOT_REGISTER_PARAMETER_MESSAGE))
                   .require(p -> getParameterTypes().contains(p.getType()), new IllegalArgumentException(INVALID_PARAMETER_MESSAGE))
                   .peek(this.parameters::add)
                   .peek(p -> this.observers.forEach(o -> o.update(p)))
                   .toEmpty();
    }

    @Override
    public final void addParameterObserver(final Observer<Parameter> obs) {
        this.observers.add(obs);
    }

    /**
     * Add the class name formatter like \"[DummyStep]\" before this method when overriding.
     * @return the string representation of this class.
     */
    @Override
    public String toString() {
        return "StepInfo=" + stepInfo
            + ", finalized=" + finalized
            + ", parameters=" + Arrays.toString(parameters.toArray());
    }
}
