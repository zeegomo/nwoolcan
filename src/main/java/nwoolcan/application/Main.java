package nwoolcan.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nwoolcan.controller.Controller;
import nwoolcan.controller.ControllerImpl;
import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.BreweryImpl;
import nwoolcan.view.ViewManager;
import nwoolcan.view.ViewManagerImpl;
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
        //Stage for selection of data to load or to create a new data set
        //final Stage selectStartData = new Stage();
        //TODO
        //selectStartData.showAndWait();

        //Supposing a new brewery is created, creates the brewery
        final Brewery brewery = new BreweryImpl();
        brewery.setBreweryName("Test brewery");
        brewery.setOwnerName("Giasamuglio");
        //Or maybe load it from the selected file
        //TODO

        //Now we have the brewery to inject into the controller
        final Controller controller = new ControllerImpl(brewery);
        //Creates the view manager
        final ViewManager vm = new ViewManagerImpl(controller);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(vm.getView(MAIN_VIEW_TYPE).getValue()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
