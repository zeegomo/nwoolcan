package nwoolcan.application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nwoolcan.controller.BreweryController;
import nwoolcan.controller.Controller;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.utils.ViewManagerImpl;
import nwoolcan.view.ViewType;
import nwoolcan.view.welcome.WelcomeViewController;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

/**
 * Main class run when the program starts.
 */
public final class Main extends Application {
    private static final String TITLE = "nWoolcan";
    private static final ViewType MAIN_VIEW_TYPE = ViewType.MAIN;
    private static final ViewType SELECT_START_VIEW_TYPE = ViewType.WELCOME;
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 400;

    /**
     * The start point of the program.
     * @param args The arguments from the command line.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        //Now we have the brewery to inject into the controller
        final Controller controller = new BreweryController();
        //Creates the view manager
        final ViewManager vm = new ViewManagerImpl(controller);

        //Stage for selection of data to load or to create a new data set
        final Stage selectStartData = new Stage();
        selectStartData.setTitle(TITLE);
        final Pair<Parent, WelcomeViewController> viewAndController = vm.<WelcomeViewController>getViewAndController(SELECT_START_VIEW_TYPE).getValue();
        selectStartData.setScene(new Scene(viewAndController.getLeft()));
        selectStartData.initModality(Modality.WINDOW_MODAL);
        selectStartData.initStyle(StageStyle.UTILITY);
        selectStartData.setMinHeight(MIN_HEIGHT);
        selectStartData.setMinWidth(MIN_WIDTH);
        selectStartData.showAndWait();

        if (viewAndController.getRight().getExitOk()) {
            primaryStage.setTitle(TITLE);
            primaryStage.setMinHeight(MIN_HEIGHT);
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setScene(new Scene(vm.getView(MAIN_VIEW_TYPE).getValue()));
            primaryStage.setMaximized(true);
            primaryStage.setOnHiding(event -> {
                final Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
                saveAlert.initOwner(primaryStage);
                saveAlert.setContentText("Do you want to save?");
                final boolean saveConfirmed = saveAlert.showAndWait().map(buttonType -> buttonType.equals(ButtonType.OK)).orElse(false);

                if (saveConfirmed) {
                    final FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
                    final File target = fileChooser.showOpenDialog(primaryStage);
                    if (target != null) {
                        controller.saveTo(target);
                        final Alert confirmAlert = new Alert(Alert.AlertType.INFORMATION);
                        confirmAlert.setHeaderText("Saved successfully");
                        confirmAlert.initOwner(primaryStage);
                        confirmAlert.showAndWait();
                    }
                }
            });
            primaryStage.show();
        }
    }
}
