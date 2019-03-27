package nwoolcan.utils;

/**
 * Observer pattern.
 * @param <T> the type of objects
 */
@FunctionalInterface
public interface Observer<T> {
    /**
     * This method is called whenever the observed object is changed.
     * @param arg an argument passed.
     */
    void update(T arg);
}
