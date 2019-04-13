package nwoolcan.model.brewery.batch.step;

/**
 * Interface for representing a production step type.
 */
public interface StepType {

    /**
     * Returns the name of the step type.
     * @return the name of the step type.
     */
    String getName();

    /**
     * Returns true if it is the step type representing a end state.
     * @return true if it is the step type representing a end state.
     */
    boolean isEndType();
}
