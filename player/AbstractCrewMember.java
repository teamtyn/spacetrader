package spacetrader.player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the AbstractCrewMember, the abstract class which is inherited by Player and all Mercenaries. All
 * crew members will have a name and a Map of String keys and Skill values.
 *
 * @author Clayton
 */
public abstract class AbstractCrewMember implements Serializable {

    Map<String, Skill> skills = new HashMap();
    String name;

    /**
     * The no args constructor for CrewMember, creates a CrewMember whose name is redacted. Skills
     * are created as usual.
     */
    public AbstractCrewMember() {
        this("REDACTED");
    }

    /**
     * Constructor for CrewMember, takes in a String newName and creates a CrewMember of that name.
     * It also adds the skills to the CrewMember.
     *
     * @param newName the name for the crew member
     */
    public AbstractCrewMember(String newName) {
        name = newName;
        //These are the skills posessed by every single AbstractCrewMember, Player included.
        //Default value is 1.
        skills.put("piloting", new Skill("piloting"));
        skills.put("fighting", new Skill("fighting"));
        skills.put("trading", new Skill("trading"));
        skills.put("charming", new Skill("charming"));
        skills.put("engineering", new Skill("engineering"));
    }

    /**
     * Getter for skills
     *
     * @return
     */
    public Map<String, Skill> getSkills() {
        return skills;
    }

        // Getter for an individual skill
    /**
     * Getter for individual Skill
     *
     * @param type
     * @return
     */
    public Skill getSkill(String type) {
        return skills.get(type);
    }

    /**
     * Getter for name
     *
     * @return
     */
    public String getName() {
        return name;
    }

}
