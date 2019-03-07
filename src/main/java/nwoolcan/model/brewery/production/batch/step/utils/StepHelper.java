package nwoolcan.model.brewery.production.batch.step.utils;

import nwoolcan.model.brewery.production.batch.step.ParameterType;
import nwoolcan.model.brewery.production.batch.step.ParameterTypeEnum;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

/**
 * Helper class for step infos about next step types and parameter types.
 */
public final class StepHelper {

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
            return Collections.singleton(StepTypeEnum.Boiling);
        }
        if (stepType.equals(StepTypeEnum.Boiling)) {
            return Collections.singleton(StepTypeEnum.Fermenting);
        }
        if (stepType.equals(StepTypeEnum.Fermenting)) {
            return new HashSet<>(Arrays.asList(StepTypeEnum.Packaging, StepTypeEnum.Aging));
        }
        if (stepType.equals(StepTypeEnum.Aging)) {
            return Collections.singleton(StepTypeEnum.Packaging);
        }
        if (stepType.equals(StepTypeEnum.Packaging)) {
            return Collections.singleton(StepTypeEnum.Finalized);
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
            return Collections.singleton(ParameterTypeEnum.Temperature);
        }
        if (stepType.equals(StepTypeEnum.Boiling)) {
            return new HashSet<>(Arrays.asList(ParameterTypeEnum.Temperature, ParameterTypeEnum.AddedHops));
        }
        if (stepType.equals(StepTypeEnum.Fermenting)) {
            return new HashSet<>(Arrays.asList(ParameterTypeEnum.Temperature, ParameterTypeEnum.ABV));
        }
        if (stepType.equals(StepTypeEnum.Aging)) {
            return Collections.singleton(ParameterTypeEnum.Temperature);
        }
        return Collections.emptySet();
    }
}
