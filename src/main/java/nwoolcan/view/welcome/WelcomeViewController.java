package nwoolcan.view.welcome;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;

@SuppressWarnings("NullAway")
public final class WelcomeViewController extends SubViewController {

    @FXML
    private SubView welcomeSubView;

    /**
     * Creates itself and gets injected.
     *
     * @param controller  injected controller.
     * @param viewManager injected view manager.
     */
    public WelcomeViewController(final Controller controller, final ViewManager viewManager) {
        super(controller, viewManager);
    }

    @FXML
    private void createNewBreweryClicked(final ActionEvent event) {
        final Stage modal =  new Stage();
        final Window window = this.getSubView().getScene().getWindow();

        modal.initOwner(window);
        modal.initModality(Modality.WINDOW_MODAL);

        final Scene scene = new Scene(this.getViewManager().getView(ViewType.NEW_BREWERY_MODAL).orElse(new AnchorPane()));

        modal.setScene(scene);
        modal.setResizable(false);
        modal.setOnCloseRequest(Event::consume); // To prevent closing from the outside
        modal.showAndWait();
    }

    @Override
    protected SubView getSubView() {
        return this.welcomeSubView;
    }
}
