package nwoolcan.utils;

import nwoolcan.utils.function.FallibleFunction;
import nwoolcan.utils.function.FallibleRunnable;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Utilities for Result.
 */
public final class Results {
    private static final String EMPTY_MESSAGE = "";
    private Results() { }

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
     * Return a {@link Result} holding the value produced by runnable, if everything goes well.
     * Otherwise return a {@link Result} holding the exception thrown by runnable.
     * Use this to avoid try-catch blocks.
     * @param runnable the function to call
     * @return a {@link Result}
     */
    public static Result<Empty> ofChecked(final FallibleRunnable runnable) {
        try {
            runnable.run();
            return Result.ofEmpty();
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
    public static <T, U extends AutoCloseable> Result<T> ofCloseable(final Supplier<U> resource, final FallibleFunction<U, T> function) {
        try (U elem = resource.get()) {
            return Result.of(function.apply(elem));
        } catch (Exception e) {
            return Result.error(e);
        }
    }
}
