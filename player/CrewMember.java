/*
 * This is the CrewMember, the abstract class which is inherited by Player and
 * all Mercenaries. All crew members will have a 
 */

package spacetrader.player;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Local Clayton
 */
public abstract class CrewMember implements Serializable {
    Map<String, Skill> skills = new HashMap();
    String name;
    
    public CrewMember() {
        this("REDACTED");
    }
    
    public CrewMember(String newName) {
        name = newName;
        //These are the skills posessed by every single CrewMember, Player included.
        //Default value is 1.
        skills.put("piloting", new Skill("piloting"));
        skills.put("fighting", new Skill("fighting"));
        skills.put("trading", new Skill("trading"));
        skills.put("charming", new Skill("charming"));
        skills.put("engineering", new Skill("engineering"));
    }
    public Map<String, Skill> getSkills() {
        return skills;
    }
    
        // Getter for an individual skill
    public Skill getSkill(String type) {
        return skills.get(type);
    }
    
    
    public String getName() {
        return name;
    }
    
}
