package nwoolcan.view.warehouse.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import nwoolcan.controller.Controller;
import nwoolcan.view.AbstractViewController;
import nwoolcan.view.ViewManager;

/**
 * Modal of the new stock view used to create a stock.
 */
@SuppressWarnings("NullAway")
public final class NewStockModalViewController extends AbstractViewController {

    @FXML
    private ComboBox comboBoxArticle;
    @FXML
    private CheckBox checkBoxDate;
    @FXML
    private ComboBox comboMinuteSelection;
    @FXML
    private ComboBox comboHourSelection;
    @FXML
    private VBox boxDateTimePicker;
    @FXML
    private Button createStockButton;

    /**
     * Creates itself and inject the controller and the view manager.
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public NewStockModalViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @FXML
    private void specifyDateClick(final ActionEvent actionEvent) {
    }

    @FXML
    private void createStockClick(final ActionEvent actionEvent) {
    }
}
