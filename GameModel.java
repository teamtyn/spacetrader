package spacetrader;

import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import spacetrader.player.Player;
import spacetrader.star_system.StarSystem;
import spacetrader.star_system.StarSystemNames;
import spacetrader.ui.Point;

/**
 * Acts as the singleton for the game, notably holding the universal player
 */
public class GameModel implements Serializable {
    private static GameModel state;
    private static final Random random = new Random();

    private int day;
    private Player player;
    private StarSystem[] systems;

    private GameModel() {
        // Cannot be instantiated outside of this class
    }
    
    public static void initialize() {
        state = new GameModel();
    }
    
    public static void load(InputStream in) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
            state = GameModel.class.cast(objectIn.readObject());
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
        for (int x = 0; x <= 2000; x += 350) {
            for (int y = 0; y <= 2000; y += 350) {
                positions.add(new Point(x + random.nextInt(100) - 50, y + random.nextInt(100) - 50));
            }
        }
        Collections.shuffle(positions, random);
        
        state.systems = new StarSystem[10];
        for (int i = 0; i < state.systems.length; i++) {
            state.systems[i] = new StarSystem(StarSystemNames.getName(), positions.remove(0));
        }
        state.player.setSystem(state.systems[0]);
        //System.out.println(state.player.getSystem());
        state.player.setPlanet(state.player.getSystem().getPlanets()[0]);
        //System.out.println(state.player.getPlanet());
        state.player.addKnownPlanet(state.player.getPlanet());
    }
}