package spacetrader.player;

/**
 * The Mercenary class represents mercenaries that the Player
 * is able to hire. As a crewmember, they have skills just like the player, and
 * these skills are factored into the total skills available to the ship/crew when
 * fighting, piloting, trading, etc.
 * 
 * @author Clayton
 */
public class Mercenary extends CrewMember {

    /**
     * The no arg constructor for Mercenary. It does everything a CrewMember does
     * in addition to assigning itself a random name.
     */
    public Mercenary() {
        super();
        this.name = MercenaryNames.getName();
    }
    
    /**
     * This overloaded constructor that takes a String argument does the same as
     * above except adds a suffix to the Mercenary's name.
     * @param suffix the suffix to be added onto Mercenary's name
     */
    public Mercenary(String suffix) {
        super();
        this.name = MercenaryNames.getName() + " of " + suffix;
    }
}
