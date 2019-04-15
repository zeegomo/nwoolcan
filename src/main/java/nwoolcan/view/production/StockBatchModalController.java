package nwoolcan.view.production;

import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.StockBatchViewModel;

public final class StockBatchModalController
    extends AbstractViewController
    implements InitializableController<StockBatchViewModel> {

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public StockBatchModalController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final StockBatchViewModel data) {

    }
}
