package nwoolcan.utils.function;

/**
 * Represent a function from T to U that may throw an {@link Exception}.
 * @param <T> type of function parameter
 * @param <U> type of function result
 * @param <E> type of exception
 */
@FunctionalInterface
public interface FallibleFunction<T, U, E extends Exception> {
    /**
     * Apply the function to the provided value.
     * @param elem the argument of the function
     * @return the result of applying the function to elem
     * @throws E the exception to be thrown.
     */
    U apply(T elem) throws E;
}
