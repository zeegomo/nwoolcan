package nwoolcan.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Class to handle GUI events.
 */
public final class ViewController {

    /**
     * Handles the click event of a test button.
     * @param event The occurred event
     */
    @FXML
    public void btnPrintClicked(final ActionEvent event) {
        System.out.println("printed");
    }
}
