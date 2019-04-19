package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.brewery.batch.step.parameter.ParameterType;
import nwoolcan.model.brewery.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.utils.Result;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Step factory implementation for creating basic steps.
 */
public final class EnumStepFactory implements StepFactory {

    private static final String CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE = " does not have a configured implementation.";

    private static final Set<StepType> MASHING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.BOILING));
    private static final Set<StepType> BOILING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.FERMENTING));
    private static final Set<StepType> FERMENTING_STEP_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(StepTypeEnum.PACKAGING, StepTypeEnum.AGING)));
    private static final Set<StepType> AGING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.PACKAGING));
    private static final Set<StepType> PACKAGING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.FINALIZED));

    private static final Set<ParameterType> MASHING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.TEMPERATURE));
    private static final Set<ParameterType> BOILING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.TEMPERATURE));
    private static final Set<ParameterType> FERMENTING_PARAMETER_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ParameterTypeEnum.TEMPERATURE, ParameterTypeEnum.ABV, ParameterTypeEnum.GRAVITY)));
    private static final Set<ParameterType> AGING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.TEMPERATURE));


    @Override
    public Result<Step> create(final StepType stepType, final Date startDate) {
        return Result.of(stepType)
                     .require(st -> st instanceof StepTypeEnum,
                         new IllegalArgumentException(stepType + CANNOT_FIND_STEP_IMPLEMENTATION_MESSAGE))
                     .map(e -> {
                         switch ((StepTypeEnum) stepType) {
                             case MASHING:
                                 return new BasicStep(stepType, startDate, MASHING_STEP_TYPES, MASHING_PARAMETER_TYPES);
                             case BOILING:
                                 return new BasicStep(stepType, startDate, BOILING_STEP_TYPES, BOILING_PARAMETER_TYPES);
                             case FERMENTING:
                                 return new BasicStep(stepType, startDate, FERMENTING_STEP_TYPES, FERMENTING_PARAMETER_TYPES);
                             case AGING:
                                 return new BasicStep(stepType, startDate, AGING_STEP_TYPES, AGING_PARAMETER_TYPES);
                             case PACKAGING:
                                 return new BasicStep(stepType, startDate, PACKAGING_STEP_TYPES, Collections.emptySet());
                             default:
                                 return new BasicStep(stepType, startDate, Collections.emptySet(), Collections.emptySet());
                         }
                     });
    }

    @Override
    public Result<Step> create(final StepType stepType) {
        return create(stepType, new Date());
    }
}
