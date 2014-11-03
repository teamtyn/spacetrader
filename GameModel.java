package spacetrader;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.stage.Stage;
import spacetrader.player.Player;
import spacetrader.star_system.StarSystem;
import spacetrader.star_system.StarSystemNames;
import spacetrader.ui.Point;

/**
 * Acts as the singleton for the game, notably holding the universal player
 */
public class GameModel implements Serializable {
    public static final int UNIVERSE_WIDTH = 2000;
    public static final int UNIVERSE_HEIGHT = 2000;
    
    private static GameModel state;
    private static final Random random = new Random();
    private static Stage stage;

    private int day;
    private Player player;
    private StarSystem[] systems;

    private GameModel() {
        // Cannot be instantiated outside of this class
    }

    public static Stage getStage() {
        return stage;
    }
    
    public static void initialize(Stage stage) {
        state = new GameModel();
        GameModel.stage = stage;
    }
    
    public static void load(InputStream in) throws IOException {
        try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
            state = GameModel.class.cast(objectIn.readObject());
        } catch (ClassNotFoundException e) {
            throw new IOException("Saved game in incompatible format.", e);
        }
    }
    
    public static void save(OutputStream out) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(out)) {
            objectOut.writeObject(state);
        }
    }

    public static void setPlayer(Player player) {
        state.player = player;
    }

    public static Player getPlayer() {
        return state.player;
    }

    public static StarSystem[] getSystems() {
        return state.systems;
    }
    
    public static Random getRandom() {
        return random;
    }

    public static int getDay() {
        return state.day;
    }

    public static void setDay(int day) {
        state.day = day;
    }

    public static void generateSystems() {
        List<Point> positions = new ArrayList<>();
        for (int x = 0; x <= UNIVERSE_WIDTH; x += 350) {
            for (int y = 0; y <= UNIVERSE_HEIGHT; y += 350) {
                positions.add(new Point(x + random.nextInt(100) - 50, y + random.nextInt(100) - 50));
            }
        }
        Collections.shuffle(positions, random);
        
        state.systems = new StarSystem[10];
        for (int i = 0; i < state.systems.length; i++) {
            state.systems[i] = new StarSystem(StarSystemNames.getName(), positions.remove(0));
        }
        state.player.setSystem(state.systems[0]);
        state.player.setPlanet(state.player.getSystem().getPlanets()[0]);
    }
}