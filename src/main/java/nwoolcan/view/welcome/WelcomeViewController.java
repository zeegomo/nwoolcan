package nwoolcan.view.welcome;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import nwoolcan.controller.Controller;
import nwoolcan.utils.Results;
import nwoolcan.view.utils.PersistencyUtils;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.ViewType;
import nwoolcan.view.subview.SubView;
import nwoolcan.view.subview.SubViewController;

import java.util.logging.Logger;

/**
 * Controller for the first view visible at start.
 */
@SuppressWarnings("NullAway")
public final class WelcomeViewController extends SubViewController {
    private static final String DEMO_FILE = "/nwoolcan/demo.nws";
    private boolean exitOk = false;

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

    private void close() {
        this.exitOk = true;
        ((Stage) this.welcomeSubView.getScene().getWindow()).close();
    }

    /**
     * Returns if the modal was ok or canceled.
     * @return if the modal was ok or canceled.
     */
    public boolean getExitOk() {
        return this.exitOk;
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
                    this.close();
                }
            });
    }

    @FXML
    private void loadFileClicked(final ActionEvent event) {
        final PersistencyUtils utils = new PersistencyUtils(this.getSubView().getScene().getWindow(), this.getController().getFileController());
        utils.showOpenFile().ifPresent(target -> {
            this.getController().loadFrom(target)
                .peek(e -> this.close()).peekError(err -> {
                    Logger.getLogger(this.getClass().getName()).severe(err.toString());
                    this.showErrorAndWait("There was an error!");
                });
        });
    }

    @FXML
    private void loadDemoClicked(final ActionEvent event) {
        final PersistencyUtils utils = new PersistencyUtils(this.getSubView().getScene().getWindow(), this.getController().getFileController());
        Results.ofCloseable(() -> this.getClass().getResourceAsStream(DEMO_FILE), reader -> this.getController().loadFromJAR(reader)
        ).peek(e -> this.close()).peekError(err -> {
                Logger.getLogger(this.getClass().getName()).severe(err.toString());
                utils.showErrorAlert();
            });
    }

    @Override
    protected SubView getSubView() {
        return this.welcomeSubView;
    }
}
