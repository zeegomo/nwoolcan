package nwoolcan.view.utils;

/**
 * A controller to which you can inject some data to fill the view.
 * @param <T> The type of the view model supported by this controller
 */
public interface InitializableController<T> {
    /**
     * Inject data to initialize the view.
     * @param data Injected view model
     */
    void initData(T data);
}
