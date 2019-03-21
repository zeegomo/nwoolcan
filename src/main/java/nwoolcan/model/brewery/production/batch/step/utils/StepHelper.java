package nwoolcan.model.brewery.production.batch.step.utils;

import nwoolcan.model.brewery.production.batch.step.parameter.ParameterType;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

/**
 * Helper class for step information about next step types and parameter types.
 */
@SuppressWarnings("Duplicates")
public final class StepHelper {

    private static final Set<StepType> MASHING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.Boiling));
    private static final Set<StepType> BOILING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.Fermenting));
    private static final Set<StepType> FERMENTING_STEP_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(StepTypeEnum.Packaging, StepTypeEnum.Aging)));
    private static final Set<StepType> AGING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.Packaging));
    private static final Set<StepType> PACKAGING_STEP_TYPES = Collections.unmodifiableSet(Collections.singleton(StepTypeEnum.Finalized));

    private static final Set<ParameterType> MASHING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.Temperature));
    private static final Set<ParameterType> BOILING_PARAMETER_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ParameterTypeEnum.Temperature, ParameterTypeEnum.AddedHops)));
    private static final Set<ParameterType> FERMENTING_PARAMETER_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ParameterTypeEnum.Temperature, ParameterTypeEnum.ABV)));
    private static final Set<ParameterType> AGING_PARAMETER_TYPES = Collections.unmodifiableSet(Collections.singleton(ParameterTypeEnum.Temperature));

    private StepHelper() { }

    /**
     * Returns a set of {@link StepType} representing all the possible step types that can be reached
     * by a given step type.
     * @param stepType the step type to check.
     * @return a set of {@link StepType} representing all the possible step types that can be reached
     * by a given step type.
     */
    public static Set<StepType> getNextStepTypesOf(final StepType stepType) {
        //need to be changed for proper domain model
        if (stepType.equals(StepTypeEnum.Mashing)) {
            return MASHING_STEP_TYPES;
        }
        if (stepType.equals(StepTypeEnum.Boiling)) {
            return BOILING_STEP_TYPES;
        }
        if (stepType.equals(StepTypeEnum.Fermenting)) {
            return FERMENTING_STEP_TYPES;
        }
        if (stepType.equals(StepTypeEnum.Aging)) {
            return AGING_STEP_TYPES;
        }
        if (stepType.equals(StepTypeEnum.Packaging)) {
            return PACKAGING_STEP_TYPES;
        }
        return Collections.emptySet();
    }

    /**
     * Returns a set of {@link ParameterType} representing all the possible parameter types that can be registered
     * in a given step type.
     * @param stepType the step type to check.
     * @return a set of {@link ParameterType} representing all the possible parameter types that can be registered
     * in a given step type.
     */
    public static Set<ParameterType> getPossibleParameterTypesOf(final StepType stepType) {
        //need to be changed for proper domain model
        if (stepType.equals(StepTypeEnum.Mashing)) {
            return MASHING_PARAMETER_TYPES;
        }
        if (stepType.equals(StepTypeEnum.Boiling)) {
            return BOILING_PARAMETER_TYPES;
        }
        if (stepType.equals(StepTypeEnum.Fermenting)) {
            return FERMENTING_PARAMETER_TYPES;
        }
        if (stepType.equals(StepTypeEnum.Aging)) {
            return AGING_PARAMETER_TYPES;
        }
        return Collections.emptySet();
    }
}
