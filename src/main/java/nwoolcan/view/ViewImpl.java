package nwoolcan.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Sets up the whole JavaFX application.
 */
public final class ViewImpl extends Application implements View {

    @Nullable private Parent rootWindow;

    @Override
    public void init() throws IOException {
        this.rootWindow = FXMLLoader.load(getClass().getResource("main.fxml"));
    }

    @Override
    public void start(final Stage primaryStage) {
        final Scene scene = new Scene(this.rootWindow);

        primaryStage.setTitle("nWoolcan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void launch() {
        super.launch();
    }
}
