package spacetrader.player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class which is inherited by Player and all Mercenaries.
 * All crew members will have a name and a Map of String keys and Skill values
 * @author Team TYN
 */
public abstract class AbstractCrewMember implements Serializable {
    /**
     * The skills of the crew member.
     */
    protected Map<String, Skill> skills;
    /**
     * The name of the crew member.
     */
    protected String name;

    /**
     * No args constructor for CrewMember, which
     *     creates a CrewMember whose name is redacted.
     * Skills are created as usual
     */
    public AbstractCrewMember() {
        this("REDACTED");
    }

    /**
     * Constructor for CrewMember, takes in a String newName
     *     and creates a CrewMember of that name.
     * It also adds the skills to the CrewMember
     * All skills have a default value of 1
     * @param newName The new name for the crew member
     */
    public AbstractCrewMember(final String newName) {
        name = newName;
        skills = new HashMap();
        skills.put("piloting", new Skill("piloting"));
        skills.put("fighting", new Skill("fighting"));
        skills.put("trading", new Skill("trading"));
        skills.put("charming", new Skill("charming"));
        skills.put("engineering", new Skill("engineering"));
    }

    /**
     * Getter for the skills of the crew member.
     * @return The skills of the crew member
     */
    public final Map<String, Skill> getSkills() {
        return skills;
    }

    /**
     * Getter for an individual skill of the crew member.
     * @param type The skill to be evaluated for this crew member
     * @return The value of the given skill
     */
    public final Skill getSkill(final String type) {
        return skills.get(type);
    }

    /**
     * Getter for the name of the crew member.
     * @return The name of the crew member
     */
    public final String getName() {
        return name;
    }
}
