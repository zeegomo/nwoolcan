package nwoolcan.model.brewery;

/**
 * Interface representing a generic incremental id generator.
 */
public interface IdGenerator {
    /**
     * Returns the next id generated.
     * @return next id.
     */
    int getNextId();
}
