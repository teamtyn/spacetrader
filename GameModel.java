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
 *
 * @author Team TYN
 */
public final class GameModel implements Serializable {

    /**
     * The width of the universe, in arbitrary units.
     */
    public static final int UNIVERSE_WIDTH = 2000;
    /**
     * The height of the universe, in arbitrary units.
     */
    public static final int UNIVERSE_HEIGHT = 2000;
    /**
     * The average space, in arbitrary units, between adjacent systems.
     */
    public static final int SYSTEM_SPACING = 350;
    /**
     * The maximum amount by which the distance between adjacent systems is
     * allowed to vary.
     */
    public static final int SYSTEM_SPACING_FUZZINESS = 100;
    /**
     * The number of systems in the universe.
     */
    public static final int SYSTEM_COUNT = 10;

    /**
     * The singleton that holds the state for this game.
     */
    private static GameModel state;
    /**
     * The one true source of all that is random.
     */
    private static final Random RANDOM = new Random();
    /**
     * Collection of Observers for changes in global state.
     */
    private static final ObserverRegistry OBSERVERS = new ObserverRegistry();
    /**
     * The highest-level UI element for the application.
     */
    private static Stage stage;

    /**
     * The current day within the game.
     */
    private int day;
    /**
     * The player of the game. Unfortunately, there isn't multi-player support.
     */
    private Player player;
    /**
     * All the systems that make up the universe.
     */
    private StarSystem[] systems;

    /**
     * Prevents instantiation outside of the class.
     */
    private GameModel() {
    }

    /**
     * Getter for the ObserverRegistry variable.
     *
     * @return The OBSERVERS
     */
    public static ObserverRegistry getObserverRegistry() {
        return OBSERVERS;
    }

    /**
     * Getter for the stage variable.
     *
     * @return The stage, which is used to display our views
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Create a new GameModel and its stage variable.
     *
     * @param aStage The stage used by the GameModel for displaying views
     */
    public static void initialize(final Stage aStage) {
        state = new GameModel();
        GameModel.stage = aStage;
    }

    /**
     * Load a previous save of the game.
     *
     * @param in the stream that passes info from the save file to be
     * deserialized
     * @throws IOException if there was an error reading from the stream
     */
    public static void load(final InputStream in) throws IOException {
        try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
            state = GameModel.class.cast(objectIn.readObject());
        } catch (ClassNotFoundException e) {
            throw new IOException("Saved game in incompatible format.", e);
        }
    }

    /**
     * Save a current session of the game.
     *
     * @param out The stream that passes info from the game to be serialized
     * @throws IOException if there was an error writing to the stream
     */
    public static void save(final OutputStream out) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(out)) {
            objectOut.writeObject(state);
        }
    }

    /**
     * Setter for the player variable.
     *
     * @param player The player of the game
     */
    public static void setPlayer(final Player player) {
        state.player = player;
    }

    /**
     * Getter for the player variable.
     *
     * @return The player of the game
     */
    public static Player getPlayer() {
        return state.player;
    }

    /**
     * Getter for the systems variable.
     *
     * @return The array of star system in the Universe
     */
    public static StarSystem[] getSystems() {
        return state.systems;
    }

    /**
     * Getter for the random variable of the game.
     *
     * @return The random variable of the game
     */
    public static Random getRandom() {
        return RANDOM;
    }

    /**
     * Getter for the day variable.
     *
     * @return The current day in the game
     */
    public static int getDay() {
        return state.day;
    }

    /**
     * Setter for the day variable.
     *
     * @param aDay The current day in the game
     */
    public static void setDay(final int aDay) {
        state.day = aDay;
    }

    /**
     * Generate the systems of the Universe. The positions of each star system
     * is decided by each one choosing a random point on an x, y plane where
     * each point is a certain distance away from each other and the border of
     * the Universe
     */
    public static void generateSystems() {
        List<Point> positions = new ArrayList<>();
        for (int x = 0; x <= UNIVERSE_WIDTH; x += SYSTEM_SPACING) {
            for (int y = 0; y <= UNIVERSE_HEIGHT; y += SYSTEM_SPACING) {
                positions.add(new Point(
                        x + calculateFuzziness(), y + calculateFuzziness()));
            }
        }
        Collections.shuffle(positions, RANDOM);

        state.systems = new StarSystem[SYSTEM_COUNT];
        for (int i = 0; i < state.systems.length; i++) {
            state.systems[i] = new StarSystem(
                    StarSystemNames.getName(), positions.remove(0));
        }
        state.player.setSystem(state.systems[0]);
        state.player.setPlanet(state.player.getSystem().getPlanets()[0]);
    }

    /**
     * @return A random fuzzy number by which to vary spacing.
     */
    private static int calculateFuzziness() {
        return RANDOM.nextInt(SYSTEM_SPACING_FUZZINESS)
                - SYSTEM_SPACING_FUZZINESS / 2;
    }
}
