package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 * Shield is a class used to protect ships from taking direct damage.
 *
 * @author Team TYN
 */
public final class Shield implements Serializable {

    /**
     * How much damage the shield can take at present.
     */
    private int strength;
    /**
     * The type of the shield.
     */
    private final ShieldType type;

    /**
     * The enum used to store values constant across all shields of a type.
     */
    public enum ShieldType {

        /**
         * Cheapest shield available.
         */
        Kite(100, 10, 100, new SerializableColor(Color.RED), "Kite"),
        /**
         * Basic shield that favors strength.
         */
        Heater(125, 10, 300, new SerializableColor(Color.GREEN), "Heater"),
        /**
         * Basic shield that favors recharge rate.
         */
        Targe(100, 15, 300, new SerializableColor(Color.PINK), "Targe"),
        /**
         * Basic shield with very low strength and high recharge rate.
         */
        Buckler(25, 25, 300, new SerializableColor(Color.AQUA), "Buckler"),
        /**
         * Basic shield with high strength but doesn't recharge.
         */
        Ishlangu(400, 0, 300, new SerializableColor(Color.WHITE), "Ishlangu"),
        /**
         * Advanced shield that favors recharge rate.
         */
        Hoplon(200, 25, 500, new SerializableColor(Color.DARKKHAKI), "Hoplon"),
        /**
         * Advanced shield that favors strength.
         */
        Riot(250, 20, 500, new SerializableColor(Color.BLACK), "Riot"),
        /**
         * Advanced shield that heavily favors strength.
         */
        BatterSea(400, 5, 500, new SerializableColor(Color.DARKGOLDENROD),
                "BatterSea"),
        /**
         * Epic shield that heavily favors strength.
         */
        Scutum(500, 10, 1000, new SerializableColor(Color.DARKRED), "Scutum"),
        /**
         * The legendary shield of the god Zeus.
         */
        Aegis(500, 25, 2500, new SerializableColor(Color.YELLOW), "Aegis"),
        /**
         * The legendary shield that protects the horses that pull the sun.
         */
        Svalinn(1000, 10, 2500, new SerializableColor(Color.BLUEVIOLET),
                "Svalinn");

        /**
         * How much damage the shield can take.
         */
        private final int shieldStrength;
        /**
         * How fast the shield replenishes its strength.
         */
        private final int rechargeRate;
        /**
         * How much the shield costs.
         */
        private final int cost;
        /**
         * The name of the shield.
         */
        private final String name;
        /**
         * The color of the shield.
         */
        private final SerializableColor color;

        /**
         * Constructor for ShieldType.
         *
         * @param aShieldStrength The strength of this type of shield.
         * @param aRechargeRate The recharge rate of this type of shield.
         * @param aCost The base cost for this type of shield.
         * @param aColor The color used to distinguish this shield type from the
         * others.
         * @param aName The string representation of the shield type.
         */
        ShieldType(final int aShieldStrength, final int aRechargeRate,
                final int aCost, final SerializableColor aColor,
                final String aName) {
            shieldStrength = aShieldStrength;
            rechargeRate = aRechargeRate;
            cost = aCost;
            color = aColor;
            name = aName;
        }
    };

    /**
     * Constructor for Shield.
     *
     * @param aType The shield type of the shield
     */
    public Shield(final ShieldType aType) {
        type = aType;
        strength = type.shieldStrength;
    }

    /**
     * Getter for shield strength.
     *
     * @return the current strength of the shield.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Getter for shield recharge rate.
     *
     * @return the recharge rate of the shield.
     */
    public int getRechargeRate() {
        return type.rechargeRate;
    }

    /**
     * Recharges the shield by a certain amount.
     *
     * @param time the amount of time the shield has to charge.
     * @return the current strength of the shield.
     */
    public int recharge(final int time) {
        strength += type.rechargeRate * time;
        return strength;
    }

    /**
     * Damages the shield by a certain amount, all damage is blocked even if
     * overflow.
     *
     * @param damage the amount of damage being dealt to the shield.
     * @return the current strength of the shield.
     */
    public int doDamage(final int damage) {
        strength -= damage;
        if (strength < 0) {
            strength = 0;
        }
        return strength;
    }

    /**
     * Getter for the name of a shield.
     *
     * @return The name of the shield.
     */
    public String getName() {
        return type.name;
    }

    /**
     * @return the type of the shield
     */
    public ShieldType getType() {
        return type;
    }

    /**
     * @return the color of the shield
     */
    public Color getColor() {
        return type.color.getColor();
    }

    /**
     * @return the cost of the shield
     */
    public int getCost() {
        return type.cost;
    }
}
