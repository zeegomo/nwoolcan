package nwoolcan.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewType;

public class Main extends Application {
    private static final String TITLE = "nWoolcan";
    private static final ViewType MAIN_VIEW_TYPE = ViewType.MAIN;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(ViewManager.getView(MAIN_VIEW_TYPE).getValue()));
        primaryStage.show();
    }
}
