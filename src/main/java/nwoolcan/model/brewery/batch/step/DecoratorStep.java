package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Abstract class for decorating a step.
 */
public abstract class DecoratorStep extends AbstractStep {

    private final AbstractStep decorated;

    /**
     * Basic decorator constructor.
     * @param decorated the decorated step implementation.
     */
    public DecoratorStep(final AbstractStep decorated) {
        super(new ModifiableStepInfoImpl(decorated.getStepInfo().getType(),
                decorated.getStepInfo().getStartDate()),
            decorated.getNextStepTypes(),
            decorated.getRegistrationParameterTypes());
        this.decorated = decorated;
    }

    /**
     * Performs a check to know if this step can be finalized with this data.
     * Returns a {@link Result} with en error if this step cannot be finalized with this finalization data.
     * @param note the finalization note.
     * @param endDate the finalization end date.
     * @param remainingSize the finalization remaining size.
     * @return a {@link Result} with errors if this step cannot be finalized with this finalization data.
     */
    @Override
    protected Result<Empty> checkFinalizationData(@Nullable final String note, final Date endDate, final Quantity remainingSize) {
        return decorated.checkFinalizationData(note, endDate, remainingSize);
    }

    /**
     * Performs a check to know if this parameter can be registered in this step.
     * Returns a {@link Result} with en error if if this parameter cannot be registered in this step.
     * @param parameter the registering parameter
     * @return a {@link Result} with en error if if this parameter cannot be registered in this step.
     */
    @Override
    protected Result<Empty> checkRegisteringParameter(final Parameter parameter) {
        return decorated.checkRegisteringParameter(parameter);
    }
}
