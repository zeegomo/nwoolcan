package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract implementation of Step interface.
 */
public abstract class AbstractStep implements Step {

    private static final String END_DATE_NULL_MESSAGE = "endDate cannot be null.";
    private static final String REMAINING_SIZE_NULL_MESSAGE = "remainingSize cannot be null.";
    private static final String ALREADY_FINALIZED_MESSAGE = "This step is already finalized.";
    private static final String PARAMETER_NULL_MESSAGE = "parameter cannot be null.";
    private static final String CANNOT_REGISTER_PARAMETER_MESSAGE = "The parameter type is invalid for this step.";

    private final StepInfo stepInfo;
    private boolean finalized;
    private final Collection<Parameter> parameters;

    //Package-protected constructor only for inheritance.
    AbstractStep(final StepInfo stepInfo) {
        this.stepInfo = stepInfo;
        this.finalized = false;
        this.parameters = new ArrayList<>();
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
    public final Result<Empty> finalize(final String note, final Date endDate, final Quantity remainingSize) {
        return Result.ofEmpty()
                     .require(() -> endDate != null, new NullPointerException(END_DATE_NULL_MESSAGE))
                     .require(() -> remainingSize != null, new NullPointerException(REMAINING_SIZE_NULL_MESSAGE))
                     .require(() -> !this.finalized, new IllegalStateException(ALREADY_FINALIZED_MESSAGE))
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
            s = s.filter(p -> p.getValue().doubleValue() > query.getGreaterThanValue().get().doubleValue());
        }
        if (query.getLessThanValue().isPresent()) {
            s = s.filter(p -> p.getValue().doubleValue() < query.getLessThanValue().get().doubleValue());
        }
        if (query.getExactValue().isPresent()) {
            s = s.filter(p -> p.getValue().doubleValue() == query.getExactValue().get().doubleValue());
        }
        if (query.getStartDate().isPresent()) {
            s = s.filter(p -> p.getDate().after(query.getStartDate().get()));
        }
        if (query.getEndDate().isPresent()) {
            s = s.filter(p -> p.getDate().before(query.getEndDate().get()));
        }

        if (query.isSortByValue()) {
            s = s.sorted((p1, p2) -> {
                if (query.isSortDescending()) {
                    Parameter tmp = p1;
                    p1 = p2;
                    p2 = tmp;
                }
                return (int) (p1.getValue().doubleValue() - p2.getValue().doubleValue());
            });
        }

        if (query.isSortByDate()) {
            s = s.sorted((p1, p2) -> {
                if (query.isSortDescending()) {
                    Parameter tmp = p1;
                    p1 = p2;
                    p2 = tmp;
                }
                return (int) (p1.getDate().getTime() - p2.getDate().getTime());
            });
        }

        return Result.of(s.collect(Collectors.toList()));
    }

    @Override
    public final Result<Empty> addParameter(final Parameter parameter) {
        Result<Empty> res = Result.ofEmpty()
                                  .require(e -> parameter != null, new NullPointerException(PARAMETER_NULL_MESSAGE))
                                  .require(e -> getParameterTypes().contains(parameter.getType()),
                                           new IllegalArgumentException(CANNOT_REGISTER_PARAMETER_MESSAGE));

        if (!res.isError()) {
            this.parameters.add(parameter);
        }

        return res;
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
