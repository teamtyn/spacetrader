package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 * Weapon is a class used to attack other ships and deal damage.
 *
 * @author Team TYN
 */
public final class Weapon implements Serializable {

    /**
     * The type of this instance of weapon.
     */
    private final WeaponType type;

    /**
     * The enum used to store values constant across all weapons of a type.
     */
    public enum WeaponType {

        /**
         * Null object.
         */
        EmptySlot(0, 10, 0, new SerializableColor(Color.WHITE), "Empty Slot"),
        /**
         * Cheapest weapon available.
         */
        Gladius(10, 10, 100, new SerializableColor(Color.GREEN), "Gladius"),
        /**
         * Basic weapon that favors damage.
         */
        Spatha(30, 10, 300, new SerializableColor(Color.BLUE), "Spatha"),
        /**
         * Basic weapon that favors rate of fire.
         */
        Katana(10, 30, 301, new SerializableColor(Color.BLUE), "Katana"),
        /**
         * Basic weapon that heavily favors rate of fire.
         */
        Rapier(5, 60, 300, new SerializableColor(Color.BLUE), "Rapier"),
        /**
         * Advanced weapon that is roughly balanced.
         */
        Kampilan(25, 20, 500, new SerializableColor(Color.RED), "Kampilan"),
        /**
         * Advanced weapon that very heavily favors rate of fire.
         */
        Scimitar(1, 500, 500, new SerializableColor(Color.RED), "Scimitar"),
        /**
         * Advanced weapon that very heavily favors damage.
         */
        Claymore(500, 1, 500, new SerializableColor(Color.RED), "Claymore"),
        /**
         * The legendary sword wielded by Charlemagne.
         */
        Joyeuse(100, 25, 2500, new SerializableColor(Color.GOLD), "Joyeuse"),
        /**
         * The legendary sword used to crown polish kings for centuries.
         */
        Szczerbiec(
                250, 10, 2500, new SerializableColor(Color.GOLD), "Szczerbiec");

        /**
         * How much damage this weapon does per shot.
         */
        private final int damage;
        /**
         * How many shots this weapon fires per tick.
         */
        private final int rateOfFire;
        /**
         * The price of the weapon.
         */
        private final int cost;
        /**
         * Some cool name that David picked that has some historical
         * significance and also serves to make the weapon sound cool.
         */
        private final String name;
        /**
         * The only way we visually distinguish the weapons at this point.
         */
        private final SerializableColor color;

        /**
         * Constructor for WeaponType.
         *
         * @param newDamage The damage of this type of weapon.
         * @param newRateOfFire The rate of fire of this type of weapon.
         * @param newCost The base cost for this type of weapon.
         * @param newColor The color used to distinguish this weapon type from
         * the others.
         * @param newName The string representation of the weapon type.
         */
        WeaponType(final int newDamage, final int newRateOfFire,
                final int newCost, final SerializableColor newColor,
                final String newName) {
            this.damage = newDamage;
            this.rateOfFire = newRateOfFire;
            this.cost = newCost;
            this.color = newColor;
            this.name = newName;
        }
    };

    /**
     * Constructor for weapons.
     *
     * @param newType The weapon type of the weapon.
     */
    public Weapon(final WeaponType newType) {
        this.type = newType;
    }

    /**
     * Getter for the damage of a weapon.
     *
     * @return The damage of the weapon.
     */
    public int getDamage() {
        return type.damage;
    }

    /**
     * Getter for the rate of fire of a weapon.
     *
     * @return The rate of fire of the weapon.
     */
    public int getRateOfFire() {
        return type.rateOfFire;
    }

    /**
     * @return the price of the weapon
     */
    public int getCost() {
        return type.cost;
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
        return type.name;
    }

    /**
     * @return the weapon's color
     */
    public Color getColor() {
        return type.color.getColor();
    }

    @Override
    public String toString() {
        return getName();
    }
}
