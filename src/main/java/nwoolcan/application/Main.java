package nwoolcan.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;

/**
 * Main class run when the program starts.
 */
public final class Main extends Application {
    private static final String TITLE = "nWoolcan";
    private static final ViewType MAIN_VIEW_TYPE = ViewType.MAIN;

    /**
     * The start point of the program.
     * @param args The arguments from the command line.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(ViewManager.getView(MAIN_VIEW_TYPE).getValue()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
