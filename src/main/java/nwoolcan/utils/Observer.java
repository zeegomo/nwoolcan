package nwoolcan.utils;

@FunctionalInterface
public interface Observer<T> {
    void update(T arg);
}
