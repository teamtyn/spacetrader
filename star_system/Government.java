package spacetrader.star_system;

import java.io.Serializable;
import java.util.HashMap;
import spacetrader.GameModel;

/**
 * Government is defined by its type, leader, and level of anger.
 *
 * @author David Purcell
 */
public final class Government implements Serializable {

    /**
     * All the possible kinds of government.
     */
    public enum Type {

        /**
         * No government.
         */
        ANARCHY,
        /**
         * Government run by the upper class families.
         */
        ARISTOCRACY,
        /**
         * Government run by money.
         */
        CAPITALIST,
        /**
         * Government run "for the people", ostensibly.
         */
        COMMUNIST,
        /**
         * Government run by big companies.
         */
        CORPORATE,
        /**
         * Government run by big companies.
         */
        DEMOCRACY,
        /**
         * Government run by misled patriotism.
         */
        FASCIST,
        /**
         * Government run by those who think they can judge skill objectively.
         */
        MERITOCRACY,
        /**
         * Government run by those who have had a farcical aquatic ceremony.
         */
        MONARCHY,
        /**
         * Government run by the upper class.
         */
        OLIGARCHY,
        /**
         * Government run by those who wish to recreate the Matrix.
         */
        TECHNOCRACY,
        /**
         * Government run by Madoka.
         */
        THEOCRACY
    };
    /**
     * The leaders of each government.
     */
    private final HashMap<Type, String> leaders;
    /**
     * The type of this particular government.
     */
    private Type type;
    /**
     * The leader of this particular government.
     */
    private String leader;

    /**
     * No argument constructor for a Government.
     */
    public Government() {
        type = Type
                .values()[GameModel.getRandom().nextInt(Type.values().length)];
        leaders = new HashMap<Type, String>();
        //setUpLeaderMap();
        //leader = leaders.get(type);
        leader = "THE PEOPLE";
    }

    /**
     * Method that creates a map of government type to leader names.
     */
    public void setUpLeaderMap() {
        leaders.put(Type.ANARCHY, "No One");
        leaders.put(Type.ARISTOCRACY, "The Snobs");
        leaders.put(Type.CAPITALIST, "The Money");
        leaders.put(Type.COMMUNIST, "The State");
        leaders.put(Type.CORPORATE, "The Businesses");
        leaders.put(Type.DEMOCRACY, "The People");
        leaders.put(Type.FASCIST, "One Mean Guy");
        leaders.put(Type.MERITOCRACY, "The Qualified");
        leaders.put(Type.MONARCHY, "One Nice Guy");
        leaders.put(Type.OLIGARCHY, "The Few");
        leaders.put(Type.TECHNOCRACY, "The Experts");
        leaders.put(Type.THEOCRACY, "God");
    }

    /**
     * Getter for the leader of the government.
     *
     * @return The leader of the government
     */
    public String getLeader() {
        return leader;
    }

    /**
     * Setter for the new leader of the government.
     *
     * @param newLeader The new leader
     */
    public void setLeader(final String newLeader) {
        leader = newLeader;
    }

    /**
     * Getter for the type of the government.
     *
     * @return The type of the government
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the government type to a new type.
     *
     * @param newType The new government type
     */
    public void setType(final Type newType) {
        type = newType;
    }

    @Override
    public String toString() {
        String str = type + "\nLeader: " + leader;
        return str;
    }
}
