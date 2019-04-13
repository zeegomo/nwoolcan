package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.brewery.batch.step.parameter.ParameterType;
import nwoolcan.model.brewery.batch.step.parameter.ParameterTypeEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Basic implementation of {@link Step} interface.
 * Package-private.
 * Create instances of this class using the factory {@link BasicStepFactory}.
 */
final class BasicStep extends AbstractStep {

    private static final Set<StepType> MASHING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.BOILING));
    private static final Set<StepType> BOILING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.FERMENTING));
    private static final Set<StepType> FERMENTING_STEP_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(StepTypeEnum.PACKAGING, StepTypeEnum.AGING)));
    private static final Set<StepType> AGING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.PACKAGING));
    private static final Set<StepType> PACKAGING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.FINALIZED));
    private static final Set<StepType> FINALIZED_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.STOCKED));

    private static final Set<ParameterType> MASHING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.TEMPERATURE));
    private static final Set<ParameterType> BOILING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.TEMPERATURE));
    private static final Set<ParameterType> FERMENTING_PARAMETER_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ParameterTypeEnum.TEMPERATURE, ParameterTypeEnum.ABV)));
    private static final Set<ParameterType> AGING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.TEMPERATURE));

    /**
     * Basic constructor with step type and start date of the step.
     * Package-protected, to create a Step use use the factory {@link BasicStepFactory}.
     * @param stepType step's type.
     * @param startDate step's start date.
     */
    BasicStep(final StepType stepType, final Date startDate) {
        super(new ModifiableStepInfoImpl(stepType, startDate));
    }

    /**
     * Constructor only with step type and setting step's start date with date now.
     * Package-protected, to create a Step use the factory {@link BasicStepFactory}.
     * @param stepType step's type.
     */
    BasicStep(final StepType stepType) {
        this(stepType, new Date());
    }

    @Override
    public Set<StepType> getNextStepTypes() {
        if (this.getStepInfo().getType().equals(StepTypeEnum.MASHING)) {
            return MASHING_STEP_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.BOILING)) {
            return BOILING_STEP_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.FERMENTING)) {
            return FERMENTING_STEP_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.AGING)) {
            return AGING_STEP_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.PACKAGING)) {
            return PACKAGING_STEP_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.FINALIZED)) {
            return FINALIZED_STEP_TYPES;
        }
        return Collections.emptySet();
    }

    @Override
    public Set<ParameterType> getParameterTypes() {
        if (this.getStepInfo().getType().equals(StepTypeEnum.MASHING)) {
            return MASHING_PARAMETER_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.BOILING)) {
            return BOILING_PARAMETER_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.FERMENTING)) {
            return FERMENTING_PARAMETER_TYPES;
        }
        if (this.getStepInfo().getType().equals(StepTypeEnum.AGING)) {
            return AGING_PARAMETER_TYPES;
        }
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return "[BasicStepImpl] " + super.toString();
    }
}
