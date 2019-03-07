package nwoolcan.utils;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utilites for Result.
 */
public final class Results {
    private static final String EMPTY_MESSAGE = "";
    private Results() { }

    /**
     * Returns a {@link Result} holding elem if elem is not null, otherwise holds a {@link NullPointerException}.
     * @param elem the elem to be tested
     * @param <T> elem type
     * @return a {@link Result} holding elem if elem is not null, otherwise holds a {@link NullPointerException}
     */
    public static <T> Result<T> requireNonNull(final T elem) {
        return requireNonNull(elem, EMPTY_MESSAGE);
    }
    /**
     * Returns a {@link Result} holding elem if elem is not null, otherwise holds a {@link NullPointerException}
     * with specified message.
     * @param elem the elem to be tested
     * @param message the message to be put in the {@link NullPointerException}
     * @param <T> elem type
     * @return a {@link Result} holding elem if elem is not null, otherwise holds a {@link NullPointerException}
     */
    public static <T> Result<T> requireNonNull(final T elem, final String message) {
        if (elem == null) {
            return Result.error(new NullPointerException(message));
        } else {
            return Result.of(elem);
        }
    }
    /**
     * Return a {@link Result} holding the value produced by callable, if everything goes well.
     * Otherwise return a {@link Result} holding the exception thrown by callable.
     * Use this to avoid try-catch blocks.
     * @param callable the function to call
     * @param <T> the type of the {@link Result}
     * @return a {@link Result}
     */
    public static <T> Result<T> ofChecked(final Callable<T> callable) {
        try {
            return Result.of(callable.call());
        } catch (Exception e) {
            return Result.error(e);
        }
    }
    /**
     * Return a {@link Result} holding the value produced by function, in everything goes well.
     * Otherwise return a {@link Result} holding the exception thrown by closing resource.
     * Use this to avoid try with resource.
     * @param resource the resource being used
     * @param function the function to call.
     * @param <T> the type of the Result.
     * @param <U> the type of the resource.
     * @return a {@link Result}
     */
    public static <T, U extends AutoCloseable> Result<T> ofCloseable(final Supplier<U> resource, final Function<U, T> function) {
        try (U elem = resource.get()) {
            return Result.of(function.apply(elem));
        } catch (Exception e) {
            return Result.error(e);
        }
    }
}
