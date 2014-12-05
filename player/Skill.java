package spacetrader.player;

import java.io.Serializable;

/**
 * Represents a skill that the player has which has a value and a type.
 * @author Team TYN
 */
public class Skill implements Serializable {
    /**
     * The type of the skill.
     */
    private String type;
    /**
     * The value of the skill.
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

    /**
     * Returns the value of this skill.
     * @return The value of this skill
     */
    public final int getValue() {
        return this.value;
    }

    /**
     * Returns the type of this skill.
     * @return The type of this skill
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Sets the skill value to a new number.
     * @param newValue The new value for the skill
     */
    public final void setValue(final int newValue) {
        this.value = newValue;
    }

    /**
     * Increases the value of the skill by the specified amount.
     * @param moreValue The value to be added the skill's value
     */
    public final void increaseValue(final int moreValue) {
        this.value += moreValue;
    }
    
    public void combineSkills(Skill otherSkill) {
        this.increaseValue(otherSkill.getValue());
    }
    @Override
    public String toString() {
        return "" + this.getType() + " : " + this.getValue();
    }
}
