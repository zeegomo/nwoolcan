package nwoolcan.utils.function;

/**
 * Represent a function from T to U that may throw an {@link Exception}.
 * @param <T> type of function parameter
 * @param <U> type of function result
 */
@FunctionalInterface
public interface FallibleFunction<T, U> {
    /**
     * Apply the function to the provided value.
     * @param elem the argument of the function
     * @return the result of applying the function to elem
     * @throws Exception the exception to be thrown.
     */
    U apply(T elem) throws Exception;
}
