package nwoolcan.view;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.view.utils.ViewManager;

/**
 * Abstract class representing a view controller that has been injected with a {@link Controller}
 * and a {@link ViewManager}.
 */
public abstract class AbstractViewController {

    private final Controller controller;
    private final ViewManager viewManager;

    /**
     * Creates itself and inject the controller and the view manager.
     * @param controller injected controller.
     * @param viewManager injected view manager.
     */
    public AbstractViewController(final Controller controller, final ViewManager viewManager) {
        this.controller = controller;
        this.viewManager = viewManager;
    }

    /**
     * Returns the injected controller.
     * @return the injected controller.
     */
    protected Controller getController() {
        return this.controller;
    }

    /**
     * Returns the injected view manager.
     * @return the injected view manager.
     */
    protected ViewManager getViewManager() {
        return this.viewManager;
    }

    private void showAlertAndWait(final Alert.AlertType type, final String title, final String message, final Window owner) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Shows an error alert.
     * @param message the message to display
     * @param owner the window that requested this alert
     */
    protected final void showErrorAndWait(final String message, final Window owner) {
        this.showAlertAndWait(Alert.AlertType.ERROR, "Error", message, owner);
    }

    /**
     * Shows a warning alert.
     * @param message the message to display
     * @param owner the window that requested this alert
     */
    protected final void showWarningAndWait(final String message, final Window owner) {
        this.showAlertAndWait(Alert.AlertType.WARNING, "Warning", message, owner);
    }

    /**
     * Shows an information alert.
     * @param message the message to display
     * @param owner the window that requested this alert
     */
    protected final void showInfoAndWait(final String message, final Window owner) {
        this.showAlertAndWait(Alert.AlertType.INFORMATION, "Info", message, owner);
    }
}
