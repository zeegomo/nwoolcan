package nwoolcan.view;

import javafx.fxml.FXML;
import nwoolcan.view.subview.SubView;
import nwoolcan.viewmodel.brewery.production.ProductionViewModel;

/**
 * Controller class for production view.
 */
@SuppressWarnings("NullAway")
public final class ProductionController
    extends SubViewController
    implements InitializableController<ProductionViewModel> {

    @FXML
    private SubView productionSubView;

    @Override
    public void initData(final ProductionViewModel data) {
        //TODO initialization.
    }

    @Override
    protected SubView getSubView() {
        return this.productionSubView;
    }
}
