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
import spacetrader.observer.ObserverRegistry;
import spacetrader.player.Player;
import spacetrader.star_system.StarSystem;
import spacetrader.star_system.StarSystemNames;
import spacetrader.ui.Point;

/**
 * Acts as the singleton for the game, holding all the state.
 * @author Team TYN
 */
public class GameModel implements Serializable {
    public static final int UNIVERSE_WIDTH = 2000;
    public static final int UNIVERSE_HEIGHT = 2000;

    private static GameModel state;
    private static final Random RANDOM = new Random();
    private static final ObserverRegistry OBSERVERS = new ObserverRegistry();
    private static Stage stage;

    private int day;
    private Player player;
    private StarSystem[] systems;

    private GameModel() {
        // Cannot be instantiated outside of this class
    }

    /**
     * Getter for the ObserverRegistry variable.
     * @return The OBSERVERS
     */
    public static ObserverRegistry getObserverRegistry() {
        return OBSERVERS;
    }

    /**
     * Getter for the stage variable.
     * @return The stage, which is used to display our views
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Create a new GameModel and its stage variable.
     * @param aStage The stage used by the GameModel for displaying views
     */
    public static void initialize(Stage aStage) {
        state = new GameModel();
        GameModel.stage = aStage;
    }

    /**
     * Load a previous save of the game.
     * @param in the stream that passes info from
     *     the save file to be deserialized
     * @throws IOException if there was an error reading from the stream
     */
    public static void load(InputStream in) throws IOException {
        try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
            state = GameModel.class.cast(objectIn.readObject());
        } catch (ClassNotFoundException e) {
            throw new IOException("Saved game in incompatible format.", e);
        }
    }

    /**
     * Save a current session of the game.
     * @param out The stream that passes info from the game to be serialized
     * @throws IOException if there was an error writing to the stream
     */
    public static void save(OutputStream out) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(out)) {
            objectOut.writeObject(state);
        }
    }

    /**
     * Setter for the player variable.
     * @param player The player of the game
     */
    public static void setPlayer(Player player) {
        state.player = player;
    }

    /**
     * Getter for the player variable.
     * @return The player of the game
     */
    public static Player getPlayer() {
        return state.player;
    }

    /**
     * Getter for the systems variable.
     * @return The array of star system in the Universe
     */
    public static StarSystem[] getSystems() {
        return state.systems;
    }

    /**
     * Getter for the random variable of the game.
     * @return The random variable of the game
     */
    public static Random getRandom() {
        return RANDOM;
    }

    /**
     * Getter for the day variable.
     * @return The current day in the game
     */
    public static int getDay() {
        return state.day;
    }

    /**
     * Setter for the day variable.
     * @param aDay The current day in the game
     */
    public static void setDay(int aDay) {
        state.day = aDay;
    }

    /**
     * Generate the systems of the Universe.
     * The positions of each star system is decided by each one choosing a
     *     random point on an x, y plane where each point is a certain distance
     *     away from each other and the border of the Universe
     */
    public static void generateSystems() {
        List<Point> positions = new ArrayList<>();
        for (int x = 0; x <= UNIVERSE_WIDTH; x += 350) {
            for (int y = 0; y <= UNIVERSE_HEIGHT; y += 350) {
                positions.add(new Point(x + RANDOM.nextInt(100) - 50, y + RANDOM.nextInt(100) - 50));
            }
        }
        Collections.shuffle(positions, RANDOM);

        state.systems = new StarSystem[10];
        for (int i = 0; i < state.systems.length; i++) {
            state.systems[i] = new StarSystem(StarSystemNames.getName(), positions.remove(0));
        }
        state.player.setSystem(state.systems[0]);
        state.player.setPlanet(state.player.getSystem().getPlanets()[0]);
    }
}
