package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.brewery.production.batch.step.info.StepInfo;
import nwoolcan.model.brewery.production.batch.step.info.UnmodifiableStepInfo;
import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.production.batch.step.parameter.QueryParameter;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;

/**
 * Abstract implementation of Step interface.
 */
public abstract class AbstractStep implements Step {

    private static final String ALREADY_FINALIZED_MESSAGE = "This step is already finalized.";
    private static final String CANNOT_REGISTER_PARAMETER_MESSAGE = "Cannot register parameter if the step is finalized.";
    private static final String INVALID_PARAMETER_MESSAGE = "The parameter type is invalid for this step.";

    private final StepInfo stepInfo;
    private boolean finalized;
    private final Collection<Parameter> parameters;

    //Package-protected constructor only for inheritance.
    AbstractStep(final StepInfo stepInfo) {
        this.stepInfo = stepInfo;
        this.finalized = stepInfo.getType().equals(StepTypeEnum.Finalized);
        this.parameters = new ArrayList<>();
    }

    /**
     * Returns this object's step info that can be changed.
     * Use this method for changing step infos properties by a subclass.
     * @return this object's step info that can be changed.
     */
    protected final StepInfo getModifiableStepInfo() {
        return this.stepInfo;
    }

    @Override
    public final StepInfo getStepInfo() {
        return new UnmodifiableStepInfo(this.stepInfo);
    }

    @Override
    public final boolean isFinalized() {
        return this.finalized;
    }

    @Override
    public final Result<Empty> finalize(@Nullable final String note, final Date endDate, final Quantity remainingSize) {
        return Result.ofEmpty()
                     .require(() -> !this.isFinalized(), new IllegalStateException(ALREADY_FINALIZED_MESSAGE))
                     .flatMap(() -> this.stepInfo.setNote(note))
                     .flatMap(() -> this.stepInfo.setEndDate(endDate))
                     .flatMap(() -> this.stepInfo.setEndStepSize(remainingSize))
                     .peek(e -> this.finalized = true);
    }

    @Override
    public final Result<Collection<Parameter>> getParameters(final QueryParameter query) {

        Stream<Parameter> s = this.parameters.stream();

        if (query.getParameterType().isPresent()) {
            s = s.filter(p -> p.getType().equals(query.getParameterType().get()));
        }
        if (query.getGreaterThanValue().isPresent()) {
            s = s.filter(p -> p.getRegistrationValue().doubleValue() >= query.getGreaterThanValue().get().doubleValue());
        }
        if (query.getLessThanValue().isPresent()) {
            s = s.filter(p -> p.getRegistrationValue().doubleValue() <= query.getLessThanValue().get().doubleValue());
        }
        if (query.getExactValue().isPresent()) {
            s = s.filter(p -> p.getRegistrationValue().doubleValue() == query.getExactValue().get().doubleValue());
        }
        if (query.getStartDate().isPresent()) {
            s = s.filter(p -> p.getRegistrationDate().after(query.getStartDate().get()));
        }
        if (query.getEndDate().isPresent()) {
            s = s.filter(p -> p.getRegistrationDate().before(query.getEndDate().get()));
        }

        if (query.isSortByValue()) {
            s = s.sorted(Comparator.comparingDouble(d -> (query.isSortDescending() ? -1 : 1) * d.getRegistrationValue().doubleValue()));
        }

        if (query.isSortByDate()) {
            s = s.sorted(Comparator.comparingLong(d -> (query.isSortDescending() ? -1 : 1) * d.getRegistrationDate().getTime()));
        }

        return Result.of(s.collect(Collectors.toList()));
    }

    @Override
    public final Result<Empty> addParameter(final Parameter parameter) {
        return Result.of(parameter)
                     .require(() -> !this.isFinalized(), new IllegalStateException(CANNOT_REGISTER_PARAMETER_MESSAGE))
                     .require(p -> getParameterTypes().contains(p.getType()), new IllegalArgumentException(INVALID_PARAMETER_MESSAGE))
                     .peek(this.parameters::add)
                     .toEmpty();
    }

    /**
     * Add the class name formatter like \"[DummyStep]\" before this method when overriding.
     * @return the string representation of this class.
     */
    @Override
    public String toString() {
        return "stepInfo=" + stepInfo
            + ", finalized=" + finalized
            + ", parameters=" + Arrays.toString(parameters.toArray());
    }
}
