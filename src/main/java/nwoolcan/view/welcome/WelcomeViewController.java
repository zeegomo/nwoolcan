package nwoolcan.view.welcome;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;

import java.io.File;
import java.util.logging.Logger;

/**
 * Controller for the first view visible at start.
 */
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

        this.getViewManager().<NewBreweryModalViewController>getViewAndController(ViewType.NEW_BREWERY_MODAL)
            .peek(p -> {
                final Scene scene = new Scene(p.getLeft());

                modal.setScene(scene);
                modal.setResizable(false);
                modal.setOnCloseRequest(Event::consume); // To prevent closing from the outside
                modal.showAndWait();
                if (p.getRight().getExitState()) {
                    this.substituteView(ViewType.DASHBOARD);
                }
            });
    }

    @FXML
    private void loadFileClicked(final ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        final File target = fileChooser.showOpenDialog(this.welcomeSubView.getScene().getWindow());
        if (target != null) {
            this.getController().loadFrom(target)
                .peek(e -> this.substituteView(ViewType.DASHBOARD)).peekError(err -> {
                    Logger.getLogger(this.getClass().getName()).severe(err.toString());
                    this.showErrorAndWait("There was an error!");
                });
        }
    }

    @Override
    protected SubView getSubView() {
        return this.welcomeSubView;
    }
}
