package nwoolcan.view;

/**
 * A controller1 to which you can inject some data to fill the view.
 * @param <T> The type of the view model supported by this controller1
 */
public interface InitializableController<T> {
    /**
     * Inject data to initialize the view.
     * @param data Injected view model
     */
    void initData(T data);
}
