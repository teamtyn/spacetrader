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
        Unicorn(250, 10, 2500, new SerializableColor(Color.GOLD), "Unicorn"),;

        public final int acceleration;
        public final int fuelEfficiency;
        public final int cost;
        public final String name;
        public final SerializableColor color;

        EngineType(int acceleration, int fuelEfficiency, int cost, SerializableColor color, String name) {
            this.acceleration = acceleration;
            this.fuelEfficiency = fuelEfficiency;
            this.cost = cost;
            this.color = color;
            this.name = name;
        }
    };

    public Engine() {
        this.type = EngineType.Hackney;
        this.acceleration = type.acceleration;
        this.fuelEfficiency = type.fuelEfficiency;
        this.name = type.name;
    }

    public Engine(EngineType type) {
        this.type = type;
        this.acceleration = type.acceleration;
        this.fuelEfficiency = type.fuelEfficiency;
        this.name = type.name;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    public EngineType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
