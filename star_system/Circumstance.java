package spacetrader.star_system;

import java.io.Serializable;
import spacetrader.GameModel;

/**
 * Circumstance has one of a couple predefined types It also has a current level or severity as well
 * as a maximum level
 *
 * @author Ryan Burns
 */
public class Circumstance implements Serializable {

    private final Type type;
    private int curLevel;
    private final int maxLevel;
    private boolean ascending;

    public enum Type {

        NONE, DROUGHT, COLD, CROPFAIL, WAR,
        BOREDOM, PLAGUE, LACKOFWORKERS
    };

    /**
     * No argument constructor for a circumstance.
     */
    public Circumstance() {
        type = Type.values()[GameModel.getRandom().nextInt(Type.values().length)];
        curLevel = 0;
        maxLevel = GameModel.getRandom().nextInt(20) + 10;
        ascending = true;
    }

    /**
     * Getter for the type of circumstance.
     * 
     * @return the type of circumstance
     */
    public Type getType() {
        return type;
    }

    /**
     * Getter for the current level of the circumstance.
     * 
     * @return the current level of the circumstance
     */
    public int getCurLevel() {
        return curLevel;
    }

    /**
     * Getter for the max level of this circumstance.
     * 
     * @return the max level
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Getter for the ordinality.
     * 
     * @return the ordinality
     */
    public int getOrdinality() {
        return type.ordinal();
    }

    /**
     * Method to increase or decrease the current level of the circumstance.
     */
    public void tickCurLevel() {
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
