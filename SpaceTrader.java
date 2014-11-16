package spacetrader;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class that sets up the view components.
 * @author Team TYN
 */
public class SpaceTrader extends Application {

    /**
     * The width of the screen.
     */
    public static final int SCREEN_WIDTH = 960;
    /**
     * The height of the screen.
     */
    public static final int SCREEN_HEIGHT = 720;

    @Override
    public final void start(final Stage stage) {
        final ScreensController mainContainer = new ScreensController();
        GameModel.initialize(stage);
        mainContainer.loadScreen("Menu", "Menu.fxml");
        mainContainer.loadScreen("SkillSetup", "SkillSetup.fxml");
        mainContainer.loadScreen("UniverseMap", "UniverseMap.fxml");
        mainContainer.loadScreen("Market", "Market.fxml");
        mainContainer.loadScreen("SpaceStation", "SpaceStation.fxml");
        mainContainer.loadScreen("Encounter", "Encounter.fxml");
        mainContainer.loadScreen("MercenaryShop", "MercenaryShop.fxml");
        mainContainer.setScreen("Menu");

        final Group root = new Group();
        root.getChildren().addAll(mainContainer);

        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method of the application.
     * @param args The array of String arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
