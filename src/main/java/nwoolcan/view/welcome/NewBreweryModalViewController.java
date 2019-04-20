package nwoolcan.view.welcome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.utils.ViewManager;

/**
 * Controller for the modal to create a new Brewery.
 */
@SuppressWarnings("NullAway")
public final class NewBreweryModalViewController extends AbstractViewController {
    private boolean exitState = false;
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

    /**
     * Return the exit state of this modal.
     * @return false if canceled
     */
    public boolean getExitState() {
        return this.exitState;
    }

    private void close() {
        ((Stage) this.parent.getScene().getWindow()).close();
    }

    @FXML
    private void createClicked(final ActionEvent event) {
        if (this.txtBreweryName.getText().trim().isEmpty()) {
            this.showErrorAndWait("The brewery name is mandatory!", this.parent.getScene().getWindow());
            return;
        }
        this.getController().initializeNewBrewery();
        this.getController().setBreweryName(this.txtBreweryName.getText().trim());
        if (!this.txtBreweryOwnerName.getText().trim().isEmpty()) {
            this.getController().setOwnerName(this.txtBreweryOwnerName.getText().trim());
        }
        this.exitState = true;
        this.close();
    }

    @FXML
    private void cancelClicked(final ActionEvent event) {
        this.close();
    }
}
