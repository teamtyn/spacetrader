package spacetrader.player;


import java.util.ArrayList;
import spacetrader.GameModel;

/**
 * The Mercenary class represents mercenaries that the Player is able to hire.
 * As a crew member, they have skills just like the player, and these
 *     skills are factored into the total skills available to the ship/crew
 *     when fighting, piloting, trading, etc.
 * @author Team TYN
 */
public class Mercenary extends AbstractCrewMember {
    private String specialty;

    /**
     * The no arg constructor for Mercenary.
     * It does everything an AbstractCrewMember does in addition to
     *     assigning itself a random name
     */
    public Mercenary() {
        super();
        this.name = MercenaryNames.getName();
        generateSkills();
    }

    /**
     * The overloaded constructor that takes a String argument.
     * It does the same as above except it adds a suffix to the Mercenary's name
     * @param suffix The suffix to be added onto Mercenary's name
     */
    public Mercenary(final String suffix) {
        super();
        this.name = MercenaryNames.getName() + " of " + suffix;
        generateSkills();
    }
    
    private void generateSkills() {
        Skill maxSkill = new Skill("No specialty");
        maxSkill.setValue(0);
        
        for(Skill skill: skills.values()) {
            skill.setValue(GameModel.getRandom().nextInt(5));
            if(skill.getValue() > maxSkill.getValue()) {
                maxSkill = skill;
            }
        }
        specialty = maxSkill.getType();
    }
    
    public String getSpecialty() {
        return specialty;
    }

}
