package nwoolcan.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nwoolcan.controller.BreweryController;
import nwoolcan.controller.Controller;
import nwoolcan.view.utils.ViewManager;
import nwoolcan.view.utils.ViewManagerImpl;
import nwoolcan.view.ViewType;

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
        selectStartData.setScene(new Scene(vm.getView(SELECT_START_VIEW_TYPE).getValue()));
        selectStartData.initModality(Modality.WINDOW_MODAL);
        selectStartData.initStyle(StageStyle.UTILITY);
        selectStartData.setMinHeight(MIN_HEIGHT);
        selectStartData.setMinWidth(MIN_WIDTH);
        selectStartData.showAndWait();

        primaryStage.setTitle(TITLE);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setScene(new Scene(vm.getView(MAIN_VIEW_TYPE).getValue()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
