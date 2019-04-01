package nwoolcan.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Sets up the whole JavaFX application.
 */
public final class ViewImpl extends Application implements View {

    private final String title = "nWoolcan";
    private final SceneType startScene = SceneType.DASHBOARD;
    private Map<SceneType, Scene> scenes = new HashMap<>();

    @Override
    public void init() throws IOException {
        for (final SceneType t : SceneType.values()) {
            this.scenes.put(t, new Scene(FXMLLoader.load(getClass().getResource(t.getResourceName()))));
        }
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle(this.title);
        primaryStage.setScene(this.scenes.get(this.startScene));
        primaryStage.show();
    }

    @Override
    public void launch() {
        super.launch();
    }
}
