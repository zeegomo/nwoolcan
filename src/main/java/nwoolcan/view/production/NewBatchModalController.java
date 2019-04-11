package nwoolcan.view.production;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.viewmodel.brewery.production.batch.NewBatchViewModel;

/**
 * View controller for new batch modal.
 */
@SuppressWarnings("NullAway")
public class NewBatchModalController
    extends AbstractViewController implements InitializableController<NewBatchViewModel> {

    @FXML
    private TableView elementsTableView;
    @FXML
    private ComboBox elementsComboBox;

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
