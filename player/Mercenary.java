/*
 * This is the Mercenary class. The class represents mercenaries that the Player
 * is able to hire. As a crewmember, they have skills just like the player, and
 * these skills are factored into the total skills available to the ship/crew when
 * fighting, piloting, trading, etc.
 */

package spacetrader.player;

/**
 *
 * @author Local Clayton
 */
public class Mercenary extends CrewMember {
    public Mercenary() {
        super();
        this.name = MercenaryNames.getName();
    }
    
    public Mercenary(String suffix) {
        this.name = MercenaryNames.getName() + " of " + suffix;
    }
}
