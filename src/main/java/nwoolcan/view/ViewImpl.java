package nwoolcan.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Sets up the whole JavaFX application.
 */
public final class ViewImpl extends Application implements View {

    private final String title = "nWoolcan";
    private final ViewType mainViewType = ViewType.MAIN;
    private static final Map<ViewType, Parent> VIEWS = new HashMap<>();

    /**
     * Returns a maps from ViewType to actual views loaded from FXML.
     * @return The loaded views
     */
    public static Map<ViewType, Parent> getViews() {
        return Collections.unmodifiableMap(ViewImpl.VIEWS);
    }

    @Override
    public void init() throws IOException {
        for (final ViewType viewType : ViewType.values()) {
            this.VIEWS.put(viewType, FXMLLoader.load(getClass().getResource(viewType.getResourceName())));
        }
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle(this.title);
        primaryStage.setScene(new Scene(this.VIEWS.get(this.mainViewType)));
        primaryStage.show();
    }

    @Override
    public void launch() {
        super.launch();
    }
}
