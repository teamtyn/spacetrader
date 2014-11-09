package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 *
 * @author Purcell7
 */
public class Weapon implements Serializable {

    private WeaponType type;
    private int damage;
    private int rateOfFire;
    private String name;

    /**
     *
     */
    public enum WeaponType {

        //Name      dmg   rof   cost  color

        Gladius(10, 10, 100, new SerializableColor(Color.GREEN), "Gladius"),
        Spatha(30, 10, 300, new SerializableColor(Color.BLUE), "Spatha"),
        Katana(10, 30, 301, new SerializableColor(Color.BLUE), "Katana"),
        Rapier(5, 60, 300, new SerializableColor(Color.BLUE), "Rapier"),
        Kampilan(25, 20, 500, new SerializableColor(Color.RED), "Kampilan"),
        Scimitar(1, 500, 500, new SerializableColor(Color.RED), "Scimitar"),
        Claymore(500, 1, 500, new SerializableColor(Color.RED), "Claymore"),
        Joyeuse(100, 25, 2500, new SerializableColor(Color.GOLD), "Joyeuse"),
        Szczerbiec(250, 10, 2500, new SerializableColor(Color.GOLD), "Szczerbiec"),;

        public final int damage;
        public final int rateOfFire;
        public final int cost;
        public final String name;
        public final SerializableColor color;

        /**
         * Constructor for WeaponType
         *
         * @param damage The damage of this type of weapon
         * @param rateOfFire The rate of fire of this type of weapon
         * @param cost The base cost for this type of weapon
         * @param color The color used to distinguish this weapon type from the others
         * @param name The string representation of the weapon type
         */
        WeaponType(int damage, int rateOfFire, int cost, SerializableColor color, String name) {
            this.damage = damage;
            this.rateOfFire = rateOfFire;
            this.cost = cost;
            this.color = color;
            this.name = name;
        }
    };

    /**
     * Constructor for weapons
     *
     * @param type The weapon type of the weapon
     */
    public Weapon(WeaponType type) {
        this.type = type;
        this.damage = type.damage;
        this.rateOfFire = type.rateOfFire;
        this.name = type.name;
    }

    /**
     * Getter for the damage of a weapon
     *
     * @return The damage of the weapon
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter for the rate of fire of a weapon
     *
     * @return The rate of fire of the weapon
     */
    public int getRateOfFire() {
        return rateOfFire;
    }

    /**
     * Getter for the type of a weapon
     *
     * @return The type of the weapon
     */
    public WeaponType getType() {
        return type;
    }

    /**
     * Getter for the name of a weapon
     *
     * @return The name of the weapon
     */
    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
