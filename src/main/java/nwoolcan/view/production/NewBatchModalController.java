package nwoolcan.view.production;

import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.NewBatchViewModel;

/**
 * View controller for new batch modal.
 */
public class NewBatchModalController
    extends AbstractViewController implements InitializableController<NewBatchViewModel> {

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewBatchModalController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final NewBatchViewModel data) {
        //TODO initialization
    }
}
