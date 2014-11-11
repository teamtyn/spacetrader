package spacetrader.player;

import java.io.Serializable;

/**
 * 
 * @author Team TYN
 */
public class Skill implements Serializable {
    /**
     * 
     */
    private String type;
    /**
     * 
     */
    private int value;

    /**
     * Single arg constructor that passes 0 for the value.
     * @param aType The type of skill to be created
     */
    public Skill(final String aType) {
        this(aType, 0);
    }

    /**
     * Two arg constructor that sets an initial value for the skill.
     * @param aType The type of skill to be created
     * @param aValue The initial value for the skill
     */
    public Skill(final String aType, final int aValue) {
        type = aType;
        value = aValue;
    }

    // Getter for value

    /**
     *
     * @return
     */
    public final int getValue() {
        return this.value;
    }

    // Getter for type

    /**
     *
     * @return
     */
    public final String getType() {
        return this.type;
    }

    // Setter for value

    /**
     *
     * @param newValue
     */
    public final void setValue(int newValue) {
        this.value = newValue;
    }

    // Increase the value of skill by specified amount

    /**
     *
     * @param moreValue The value to be added the skill's value
     */
    public final void increaseValue(int moreValue) {
        this.value += moreValue;
    }
}
