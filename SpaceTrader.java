package spacetrader;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author TYN
 * @version 1.0
 */
public class SpaceTrader extends Application {

    /**
     *
     */
    public static final int SCREEN_WIDTH = 960;
    /**
     *
     */
    public static final int SCREEN_HEIGHT = 720;

    @Override
    public void start(Stage stage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        GameModel.initialize(stage);
        mainContainer.loadScreen("Menu", "Menu.fxml");
        mainContainer.loadScreen("SkillSetup", "SkillSetup.fxml");
        mainContainer.loadScreen("UniverseMap", "UniverseMap.fxml");
        mainContainer.loadScreen("Market", "Market.fxml");
        mainContainer.loadScreen("SpaceStation", "SpaceStation.fxml");
        mainContainer.loadScreen("Encounter", "Encounter.fxml");
        mainContainer.setScreen("Menu");

        final Group root = new Group();
        root.getChildren().addAll(mainContainer);

        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method of the application.
     * @param args the array of String arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
