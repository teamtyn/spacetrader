package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 *
 * @author Purcell7
 */
public class Engine implements Serializable {

    private final EngineType type;
    private final int acceleration;
    private final int fuelEfficiency;
    private final String name;

    public enum EngineType {

        //Name      acc   fuel  cost  color                              //Name

        Hackney(10, 10, 100, new SerializableColor(Color.GREEN), "Hackney"),
        Lipizzaner(30, 10, 300, new SerializableColor(Color.BLUE), "Lipizzaner"),
        Marwari(10, 30, 300, new SerializableColor(Color.BLUE), "Marwari"),
        Malopolski(15, 20, 300, new SerializableColor(Color.BLUE), "Malopolski"),
        Galiceño(20, 15, 300, new SerializableColor(Color.BLUE), "Galiceño"),
        Kampilan(25, 20, 500, new SerializableColor(Color.RED), "Kampilan"),
        Shetland(20, 25, 500, new SerializableColor(Color.RED), "Shetland"),
        Tawleed(50, 10, 500, new SerializableColor(Color.RED), "Tawleed"),
        Yakut(10, 250, 2500, new SerializableColor(Color.GOLD), "Yakut"),
        Unicorn(250, 10, 2500, new SerializableColor(Color.GOLD), "Unicorn");

        public final int acceleration;
        public final int fuelEfficiency;
        public final int cost;
        public final String name;
        public final SerializableColor color;

        /**
         * Constructor for EngineType
         *
         * @param acceleration The rate of change in velocity due to the engine
         * @param fuelEfficiency The rate at which fuel is burnt to travel distances
         * @param cost The base cost for this type of engine
         * @param color The color used to distinguish this engine type from the others
         * @param name The string representation of the name type
         */
        EngineType(int acceleration, int fuelEfficiency, int cost, SerializableColor color, String name) {
            this.acceleration = acceleration;
            this.fuelEfficiency = fuelEfficiency;
            this.cost = cost;
            this.color = color;
            this.name = name;
        }
    };

    /**
     * No argument constructor for Shield, creates a Hackney engine (lowest level)
     */
    public Engine() {
        this.type = EngineType.Hackney;
        this.acceleration = type.acceleration;
        this.fuelEfficiency = type.fuelEfficiency;
        this.name = type.name;
    }

    /**
     * Constructor for Engine
     *
     * @param type The engine type of the engine
     */
    public Engine(EngineType type) {
        this.type = type;
        this.acceleration = type.acceleration;
        this.fuelEfficiency = type.fuelEfficiency;
        this.name = type.name;
    }

    /**
     * Getter for acceleration
     *
     * @return The engine's acceleration
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Getter for fuel efficiency
     *
     * @return The engine's fuel efficiency
     */
    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    /**
     * Getter for engine type
     *
     * @return The engine's engine type
     */
    public EngineType getType() {
        return type;
    }

    /**
     * Getter for engine name
     *
     * @return The engine's engine name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
