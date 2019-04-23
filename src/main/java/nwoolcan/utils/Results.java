package nwoolcan.utils;

import nwoolcan.utils.function.FallibleFunction;
import nwoolcan.utils.function.FallibleRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Utilities for Result.
 */
public final class Results {
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
     * @param <E> the type of the exception.
     * @return a {@link Result}
     */
    public static <E extends Exception> Result<Empty> ofChecked(final FallibleRunnable<E> runnable) {
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
     * @param <E> the type of the exception.
     * @return a {@link Result}
     */
    public static <T, U extends AutoCloseable, E extends Exception> Result<T> ofCloseable(final Supplier<U> resource, final FallibleFunction<U, T, E> function) {
        try (U elem = resource.get()) {
            return Result.of(function.apply(elem));
        } catch (Exception e) {
            return Result.error(e);
        }
    }
    /**
     * Returns a {@link Result} of a collection reduced propagating the errors of
     * every result in the passed collection.
     * @param collection the collection to reduce.
     * @param <T> the type of elements inside the collection of results.
     * @return a reduced collection with errors propagated.
     */
    public static <T> Result<Collection<T>> reduce(final Collection<Result<T>> collection) {
        return collection.stream().reduce(
            Result.of(new ArrayList<T>()),
            (acc, r) -> acc.require(r::isPresent, r::getError)
                           .peek(col -> col.add(r.getValue())),
            (r1, r2) -> r1.require(r2::isPresent, r2::getError)
                          .peek(col -> col.addAll(r2.getValue())));
    }
}
