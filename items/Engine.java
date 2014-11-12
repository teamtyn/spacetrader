package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 * Engine is a class used to propel ships around the universe.
 *
 * @author Team TYN
 */
public class Engine implements Serializable {
    /**
     * 
     */
    private final EngineType type;
    /**
     * 
     */
    private final int acceleration;
    /**
     * 
     */
    private final int fuelEfficiency;
    /**
     * 
     */
    private final String name;

    /**
     * The enum used to store values constant across all engines of a type.
     */
    public enum EngineType {

        /**
         * Cheapest engine type.
         */
        Hackney(10, 10, 100, new SerializableColor(Color.GREEN), "Hackney"),
        /**
         * Basic engine that greatly favors acceleration.
         */
        Lipizzaner(30, 10, 300, new SerializableColor(Color.BLUE),
                "Lipizzaner"),
        /**
         * Basic engine that greatly favors efficiency.
         */
        Marwari(10, 30, 300, new SerializableColor(Color.BLUE), "Marwari"),
        /**
         * Better engine that slightly favors efficiency.
         */
        Malopolski(15, 20, 300, new SerializableColor(Color.BLUE),
                "Malopolski"),
        /**
         * Better engine that slightly favors acceleration.
         */
        Galiceno(20, 15, 300, new SerializableColor(Color.BLUE), "Galiceño"),
        /**
         * Advanced engine that slightly favors acceleration.
         */
        Kampilan(25, 20, 500, new SerializableColor(Color.RED), "Kampilan"),
        /**
         * Advanced engine that slightly favors efficiency.
         */
        Shetland(20, 25, 500, new SerializableColor(Color.RED), "Shetland"),
        /**
         *  Advanced engine that heavily favors acceleration.
         */
        Tawleed(50, 10, 500, new SerializableColor(Color.RED), "Tawleed"),
        /**
         *  Epic engine that heavily favors efficiency.
         */
        Yakut(10, 250, 2500, new SerializableColor(Color.GOLD), "Yakut"),
        /**
         *  Epic engine that heavily favors acceleration.
         */
        Unicorn(250, 10, 2500, new SerializableColor(Color.GOLD), "Unicorn");

        /**
         * 
         */
        public final int acceleration;
        /**
         * 
         */
        public final int fuelEfficiency;
        /**
         * 
         */
        public final int cost;
        /**
         * 
         */
        public final String name;
        /**
         * 
         */
        public final SerializableColor color;

        /**
         * Constructor for EngineType.
         *
         * @param aAcceleration The rate of change in velocity
         *     due to the engine.
         * @param aFuelEfficiency The rate at which fuel is burnt to
         *     travel distances.
         * @param aCost The base cost for this type of engine.
         * @param aColor The color used to distinguish this engine
         *     type from the others.
         * @param aName The string representation of the name type.
         */
        EngineType(final int aAcceleration, final int aFuelEfficiency,
                final int aCost, final SerializableColor aColor,
                final String aName) {
            acceleration = aAcceleration;
            fuelEfficiency = aFuelEfficiency;
            cost = aCost;
            color = aColor;
            name = aName;
        }
    };

    /**
     * No argument constructor for Shield,
     *     creates a Hackney engine (lowest level).
     */
    public Engine() {
        this.type = EngineType.Hackney;
        this.acceleration = type.acceleration;
        this.fuelEfficiency = type.fuelEfficiency;
        this.name = type.name;
    }

    /**
     * Constructor for Engine.
     *
     * @param aType The engine type of the engine.
     */
    public Engine(final EngineType aType) {
        type = aType;
        acceleration = aType.acceleration;
        fuelEfficiency = aType.fuelEfficiency;
        name = aType.name;
    }

    /**
     * Getter for acceleration.
     *
     * @return The engine's acceleration.
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * Getter for fuel efficiency.
     *
     * @return The engine's fuel efficiency.
     */
    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    /**
     * Getter for engine type.
     *
     * @return The engine's engine type.
     */
    public EngineType getType() {
        return type;
    }

    /**
     * Getter for engine name.
     *
     * @return The engine's engine name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
