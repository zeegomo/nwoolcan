package nwoolcan.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Result.
 * @param <T> Result type.
 */
public final class Result<T> {

    private final Optional<T> elem;
    private final Optional<Exception> exception;

    private Result(final Optional<T> elem, final Optional<Exception> e) {
        this.elem = elem;
        this.exception = e;
    }
    /**
     * @param <T> the type of elem
     * @param elem the result to be encapsulated.
     * @return a new {@link Result} holding elem
     */
    public static <T> Result<T> of(final T elem) {
        return new Result<>(Optional.of(elem), Optional.empty());
    }
    /**
     * @param <T> the type of the value to be returned, if any
     * @param e the exception to be encapsulated.
     * @return a new {@link Result} holding e
     */
    public static <T> Result<T> error(final Exception e) {
        return new Result<>(Optional.empty(), Optional.of(e));
    }
    /**
     *
     * @return a new {@link Result} holding an {@link Empty} value
     */
    public static Result<Empty> ofEmpty() {
        return new Result<>(Optional.of(new Empty() { }), Optional.empty());
    }
    /**
     * Return true if the {@link Result} holds a value.
     * @return whether the {@link Result} holds a value
     */
    public boolean isPresent() {
        return this.elem.isPresent();
    }
    /**
     * Return true if the {@link Result} holds an exception.
     * @return whether the {@link Result} holds an exception
     */
    public boolean isError() {
        return this.exception.isPresent();
    }
    /**
     * Return the value held by the {@link Result} if any, otherwise throws a {@link Exception}.
     * @return the value held by the {@link Result}
     */
    public T getValue() {
        return this.elem.get();
    }
    /**
     * Return the exception held by the {@link Result} if any, otherwise throws a {@link Exception}.
     * @return the exception held by the {@link Result}
     */
    public Exception getError() {
        return this.exception.get();
    }
    /**
     * If a value is present, apply the provided function to it returning a {@link Result} describing it. Otherwise return a {@link Result} holding the original exception.
     * @param mapper a mapping function to apply to the value, if present
     * @param <U> the type of the result of the mapping function
     * @return a {@link Result} describing the result of applying a mapping function to the value of this {@link Result}, if a value is present, otherwise a {@link Result} holding the original exception.
     */
    @SuppressWarnings("unchecked")
    public <U> Result<U> map(final Function<? super T, ? extends U> mapper) {
        return this.isPresent() ? Result.of(mapper.apply(this.elem.get())) : (Result<U>) this;
    }
    /**
     * If a value is present, apply the provided {@link Result}-bearing function to it returning that {@link Result}. Otherwise return a {@link Result} holding the original exception.
     * @param mapper a mapping function to apply to the value, if present. The method is similar to map(Function) but the provided mapper is one whose result is already a {@link Result}, and if invoked, flatMap does not wrap it with an additional {@link Result}.
     * @param <U> the type of the result of the mapping function
     * @return a {@link Result} describing the result of the supplier, if a value is present, otherwise a {@link Result} holding the original exception.
     */
    @SuppressWarnings("unchecked")
    public <U> Result<U> flatMap(final Function<? super T, Result<U>> mapper) {
        return this.isPresent() ? mapper.apply(this.elem.get()) : (Result<U>) this;
    }
    /**
     * If a value is present, apply the provided {@link Result}-bearing function to it returning that {@link Result}. Otherwise return a {@link Result} holding the original exception.
     * @param supplier a supplier of {@link Result}
     * @param <U> the type of the result of the supplier
     * @return a {@link Result} describing the result of the supplier, if a value is present, otherwise a {@link Result} holding the original exception.
     */
    @SuppressWarnings("unchecked")
    public <U> Result<U> flatMap(final Supplier<Result<U>> supplier) {
        return this.isPresent() ? supplier.get() : (Result<U>) this;
    }
    /**
     * If a value is present, execute the provided {@link Consumer}.
     * @param action a non-interfering action to perform on the element
     * @return this
     */
    public Result<T> peek(final Consumer<? super T> action) {
        return this.map(elem -> {
            action.accept(elem);
            return elem;
        });
    }
    /**
     * Return the value if present, otherwise return other.
     * @param other the value to be returned if there is no value present
     * @return the value, if present, otherwise other
     */
    public T orElse(final T other) {
        return this.isPresent() ? this.elem.get() : other;
    }
    /**
     * Return the value if present, otherwise invoke other and return the result of that invocation.
     * @param other a {@link Supplier} whose result is returned if no value is present
     * @return the value if present otherwise the result of other.get()
     */
    public T orElse(final Supplier<? extends T> other) {
        return this.isPresent() ? this.elem.get() : other.get();
    }
    /**
     * If the value is not present or the value is present and matches the given predicate, return this.
     * Otherwise return a {@link Result} holding an {@link IllegalArgumentException}.
     * @param predicate a predicate to apply to the value, if present
     * @return a {@link Result} describing the value of this {@link Result} if a value is present and the value matches the given predicate
     */
    public Result<T> require(final Predicate<? super T> predicate) {
        return this.require(predicate, new IllegalArgumentException());
    }
    /**
     * If the value is not present or the value is present and matches the given predicate, return this.
     *      * Otherwise return a {@link Result} holding the specified exception.
     * @param predicate a predicate to apply to the value, if present
     * @param exception the exception to be hold in the resulting {@link Result}
     *                  if the value does not match the predicate
     * @return a {@link Result} describing the value of this {@link Result} if a value is present
     * and the value matches the given predicate
     */
    public Result<T> require(final Predicate<? super T> predicate, final Exception exception) {
        if (this.isPresent()) {
            return predicate.test(this.elem.get()) ? this : Result.error(exception);
        } else {
            return this;
        }
    }
    /**
     * If the value is not present or the value is present and the predicate is true, return this.
     * Otherwise return a {@link Result} holding an {@link IllegalArgumentException}.
     * @param supplier a supplier of a boolean condition
     * @return a {@link Result} describing the value of this {@link Result} if a value is present and the condition is true
     */
    public Result<T> require(final Supplier<Boolean> supplier) {
        return this.require(supplier, new IllegalArgumentException());
    }
    /**
     * Apply the provided function to the exception, if any and the exception type matches
     * the provided one.
     * @param exception the type of exception.
     * @param function the function to apply to the exception
     * @param <E> the type of the exception
     * @return this
     */
    @SuppressWarnings("unchecked")
    public <E extends Exception> Result<T> peekError(final Class<E> exception, final Consumer<E> function) {
        if (this.isError() && exception.equals(this.exception.get().getClass())) {
           function.accept((E) this.exception.get());
        }
        return this;
    }
    /**
     * Apply the provided function to the exception, if any.
     * @param function the function to apply to the exception
     * @return this
     */
    public Result<T> peekError(final Consumer<Exception> function) {
        if (this.isError()) {
            function.accept(this.exception.get());
        }
        return this;
    }
    /**
     * If the value is not present or the value is present and the predicate is true, return this.
     * Otherwise return a {@link Result} holding the specified exception.
     * @param supplier a supplier of a boolean condition
     * @param exception the exception to be hold in the resulting {@link Result} if the value does not match the predicate
     * @return a {@link Result} describing the value of this {@link Result} if a value is present and the condition is true
     */
    public Result<T> require(final Supplier<Boolean> supplier, final Exception exception) {
        if (this.isPresent()) {
            return supplier.get() ? this : Result.error(exception);
        } else {
            return this;
        }
    }
    /**
     * Return a {@link Optional}  describing the value if present. Otherwise returns an empty {@link Optional}
     * @return a {@link Optional} describing the value if present, or an empty {@link Optional} if this {@link Result} hold an exception
     */
    public Optional<T> toOptional() {
        return this.isPresent() ? Optional.of(this.elem.get()) : Optional.empty();
    }
    /**
     * Map this {@link Result} to a {@link Result} of {@link Empty}.
     * @return a {@link Result} of {@link Empty}.
     */
    public Result<Empty> toEmpty() {
        return this.flatMap(Result::ofEmpty);
    }
    /**
     * Returns a {@link Stream} with this {@link Result} as its source.
     * @return a {@link Stream}.
     */
    public Stream<Result<T>> stream() {
        return Stream.of(this);
    }
    /**
     * Indicates whether some other object is "equal to" this {@link Result}. The other object is considered equal if:
     *  - it is also a {@link Result} and:
     *  - the present values are "equal to" each other via equals().
     * @param obj an object to be tested for equality
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Result)) {
            return false;
        }
        Result<?> other = (Result<?>) obj;
        if (this.isPresent()) {
            return this.elem.equals(other.elem);
        } else {
            return false;
        }
    }
    /**
     * Return a non-empty string representation of this {@link Result} suitable for debugging. The exact presentation format is unspecified and may vary between implementations and versions.
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return this.isPresent() ? this.elem.get().toString() : this.exception.get().toString();
    }
    /**
     * Return the hash code value of the present value, if any, or 0 if no value is present.
     * @return hash code value of the present value or 0
     */
    @Override
    public int hashCode() {
        return this.isPresent() ? this.elem.get().hashCode() : 0;
    }
}
