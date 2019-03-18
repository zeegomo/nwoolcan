package nwoolcan.utils.function;

/**
 * Represent a function with no parameter and no result that may throw an error.
 * @param <E> type of exception
 */
@FunctionalInterface
public interface FallibleRunnable<E extends Exception> {
    /**
     * Run the function.
     * @throws E the exception to be thrown.
     */
    void run() throws E;
}
