package spacetrader.player;

/**
 * The Mercenary class represents mercenaries that the Player is able to hire.
 * As a crew member, they have skills just like the player, and these
 *     skills are factored into the total skills available to the ship/crew
 *     when fighting, piloting, trading, etc.
 * @author Team TYN
 */
public class Mercenary extends AbstractCrewMember {

    /**
     * The no arg constructor for Mercenary.
     * It does everything an AbstractCrewMember does in addition to
     *     assigning itself a random name
     */
    public Mercenary() {
        super();
        this.name = MercenaryNames.getName();
    }

    /**
     * The overloaded constructor that takes a String argument.
     * It does the same as above except it adds a suffix to the Mercenary's name
     * @param suffix The suffix to be added onto Mercenary's name
     */
    public Mercenary(final String suffix) {
        super();
        this.name = MercenaryNames.getName() + " of " + suffix;
    }
}
