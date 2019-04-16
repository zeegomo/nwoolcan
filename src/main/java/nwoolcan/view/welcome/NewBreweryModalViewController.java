package nwoolcan.view.welcome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.ViewManager;

@SuppressWarnings("NullAway")
public final class NewBreweryModalViewController extends AbstractViewController {
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField txtBreweryOwnerName;
    @FXML
    private TextField txtBreweryName;

    /**
     * Creates itself and inject the controller and the view manager.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewBreweryModalViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @FXML
    private void createClicked(final ActionEvent event) {
        this.getController().initializeNewBrewery();
        if (!this.txtBreweryName.getText().isEmpty()) {
            this.getController().setBreweryName(this.txtBreweryName.getText());
        }
        if (!this.txtBreweryOwnerName.getText().isEmpty()) {
            this.getController().setOwnerName(this.txtBreweryOwnerName.getText());
        }
        ((Stage) this.parent.getScene().getWindow()).close();

    }
}
