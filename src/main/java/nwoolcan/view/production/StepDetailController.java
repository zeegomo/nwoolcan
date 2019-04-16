package nwoolcan.view.production;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import nwoolcan.controller.Controller;
import nwoolcan.view.InitializableController;
import nwoolcan.view.ViewManager;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;
import nwoolcan.viewmodel.brewery.production.step.DetailStepViewModel;

public class StepDetailController
    extends SubViewController
    implements InitializableController<DetailStepViewModel> {

    @FXML
    private Text stepTypeName;
    @FXML
    private Button registerParamterButton;
    @FXML
    private SubView stepDetailSubView;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public StepDetailController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @Override
    public void initData(final DetailStepViewModel data) {
        this.registerParamterButton.setDisable(!data.isFinalized());

        this.stepTypeName.setText(data.getTypeName());
    }

    @Override
    protected SubView getSubView() {
        return this.stepDetailSubView;
    }

    public void goBackButtonClicked(final ActionEvent event) {
        this.previousView(); //TODO refactor with reload
    }

    public void registerParameterButtonClicked(final ActionEvent event) {

    }
}
