package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 * Weapon is a class used to attack other ships and deal damage.
 * 
 * @author David Purcell
 */
public class Weapon implements Serializable {

    private WeaponType type;
    private int damage;
    private int rateOfFire;
    private String name;

    /**
     * The enum used to store values constant across all weapons of a type.
     */
    public enum WeaponType {

        /**
         *  Cheapest weapon available.
         */
        Gladius(10, 10, 100, new SerializableColor(Color.GREEN), "Gladius"),
        /**
         *  Basic weapon that favors damage.
         */
        Spatha(30, 10, 300, new SerializableColor(Color.BLUE), "Spatha"),
        /**
         *  Basic weapon that favors rate of fire.
         */
        Katana(10, 30, 301, new SerializableColor(Color.BLUE), "Katana"),
        /**
         *  Basic weapon that heavily favors rate of fire.
         */
        Rapier(5, 60, 300, new SerializableColor(Color.BLUE), "Rapier"),
        /**
         *  Advanced weapon that is roughly balanced.
         */
        Kampilan(25, 20, 500, new SerializableColor(Color.RED), "Kampilan"),
        /**
         *  Advanced weapon that very heavily favors rate of fire.
         */
        Scimitar(1, 500, 500, new SerializableColor(Color.RED), "Scimitar"),
        /**
         *  Advanced weapon that very heavily favors damage.
         */
        Claymore(500, 1, 500, new SerializableColor(Color.RED), "Claymore"),
        /**
         *  The legendary sword wielded by Charlemagne.
         */
        Joyeuse(100, 25, 2500, new SerializableColor(Color.GOLD), "Joyeuse"),
        /**
         *  The legendary sword used to crown polish kings for centuries.
         */
        Szczerbiec(250, 10, 2500, new SerializableColor(Color.GOLD), "Szczerbiec");

        public final int damage;
        public final int rateOfFire;
        public final int cost;
        public final String name;
        public final SerializableColor color;

        /**
         * Constructor for WeaponType.
         *
         * @param damage The damage of this type of weapon.
         * @param rateOfFire The rate of fire of this type of weapon.
         * @param cost The base cost for this type of weapon.
         * @param color The color used to distinguish this weapon type from the others.
         * @param name The string representation of the weapon type.
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
     * Constructor for weapons.
     *
     * @param type The weapon type of the weapon.
     */
    public Weapon(WeaponType type) {
        this.type = type;
        this.damage = type.damage;
        this.rateOfFire = type.rateOfFire;
        this.name = type.name;
    }

    /**
     * Getter for the damage of a weapon.
     *
     * @return The damage of the weapon.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter for the rate of fire of a weapon.
     *
     * @return The rate of fire of the weapon.
     */
    public int getRateOfFire() {
        return rateOfFire;
    }

    /**
     * Getter for the type of a weapon.
     *
     * @return The type of the weapon.
     */
    public WeaponType getType() {
        return type;
    }

    /**
     * Getter for the name of a weapon.
     *
     * @return The name of the weapon.
     */
    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
