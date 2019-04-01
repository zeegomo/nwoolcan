package nwoolcan.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Sets up the whole JavaFX application.
 */
public final class ViewImpl extends Application implements View {

    @Override
    public void start(final Stage primaryStage) throws IOException {
        final Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        final Scene scene = new Scene(root);

        primaryStage.setTitle("nWoolcan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start() {
        launch();
    }
}
