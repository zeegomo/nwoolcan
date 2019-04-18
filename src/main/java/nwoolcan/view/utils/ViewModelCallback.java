package nwoolcan.view.utils;

/**
 * This class contains both a ViewModel (anything you want) and a callback.
 * The callback can be used by the child controller (usually detail) to say stuff to parent controller (usually master).
 */
public final class ViewModelCallback<T> {
    private final T viewModel;
    private final Runnable callback;

    /**
     * Creates a new ViewModel-Callback pair.
     * @param viewModel the view model to use.
     * @param callback the callback to use.
     */
    public ViewModelCallback(final T viewModel, final Runnable callback) {
        this.viewModel = viewModel;
        this.callback = callback;
    }

    /**
     * The view model.
     * @return the view model.
     */
    public T getViewModel() {
        return viewModel;
    }

    /**
     * The callback.
     * @return the callback.
     */
    public Runnable getCallback() {
        return callback;
    }

}
