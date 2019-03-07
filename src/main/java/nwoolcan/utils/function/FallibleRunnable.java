package nwoolcan.utils.function;

/**
 * Represent a function with no parameter and no result that may throw an error.
 */
@FunctionalInterface
public interface FallibleRunnable {
    /**
     * Run the function.
     * @throws Exception the exception to be thrown
     */
    void run() throws Exception;
}
