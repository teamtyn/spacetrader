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
     * The maximum duration of a circumstance.
     */
    private static final int MAX_DURATION = 30;
    /**
     * The minimum duration of a circumstance.
     */
    private static final int MIN_DURATION = 10;

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

    /**
     * The set of predefined types of a potential circumstance.
     */
    public enum Type {

        /**
         * Everything is fine.
         */
        NONE,
        /**
         * No water.
         */
        DROUGHT,
        /**
         * It is cold.
         */
        COLD,
        /**
         * No food.
         */
        CROPFAIL,
        /**
         * People killing people.
         */
        WAR,
        /**
         * Nothing to do.
         */
        BOREDOM,
        /**
         * Sick people and death.
         */
        PLAGUE,
        /**
         * No people to do things.
         */
        LACKOFWORKERS
    };

    /**
     * No argument constructor for a circumstance.
     */
    public Circumstance() {
        type = Type.values()[
                GameModel.getRandom().nextInt(Type.values().length)];
        curLevel = 0;
        maxLevel = GameModel.getRandom().nextInt(MAX_DURATION - MIN_DURATION)
                + MIN_DURATION;
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
