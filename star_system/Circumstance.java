package spacetrader.star_system;

import java.io.Serializable;
import spacetrader.GameModel;

/**
 * Circumstance has one of a couple predefined types.
 * It also has a current level of severity, as well as a maximum level
 * @author Team TYN
 */
public class Circumstance implements Serializable {
    /**
     * The type of the circumstance.
     */
    private final Type type;
    /**
     * The current level of the circumstance.
     */
    private int curLevel;
    /**
     * The max level of the circumstance.
     */
    private final int maxLevel;
    /**
     * Whether the level of the circumstance is currently ascending.
     */
    private boolean ascending;

    public enum Type {
        NONE, DROUGHT, COLD, CROPFAIL, WAR,
        BOREDOM, PLAGUE, LACKOFWORKERS
    };

    /**
     * No argument constructor for a circumstance.
     */
    public Circumstance() {
        type = Type.values()[
                GameModel.getRandom().nextInt(Type.values().length)];
        curLevel = 0;
        maxLevel = GameModel.getRandom().nextInt(20) + 10;
        ascending = true;
    }

    /**
     * Getter for the type of the circumstance.
     * @return The type of the circumstance
     */
    public final Type getType() {
        return type;
    }

    /**
     * Getter for the current level of the circumstance.
     * @return The current level of the circumstance
     */
    public final int getCurLevel() {
        return curLevel;
    }

    /**
     * Getter for the max level of the circumstance.
     * @return The max level of the circumstance
     */
    public final int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Getter for the ordinality of the circumstance.
     * @return The ordinality of the circumstance
     */
    public final int getOrdinality() {
        return type.ordinal();
    }

    /**
     * Method to increase or decrease the current level of the circumstance.
     */
    public final void tickCurLevel() {
        if (ascending) {
            curLevel++;
        } else if (!ascending && curLevel != 0) {
            curLevel--;
        }
        if (curLevel == maxLevel) {
            ascending = false;
        }
    }
}
