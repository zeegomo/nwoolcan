package nwoolcan.utils;

/**
 * Interface representing an observer object that observes an update of a object
 * of type T.
 * @param <T> the object's type to observe.
 */
@FunctionalInterface
public interface Observer<T> {
    /**
     * Called when an update is observed.
     * @param arg the observed update object.
     */
    void update(T arg);
}
