package spacetrader.star_system;

import java.io.Serializable;
import java.util.HashMap;
import spacetrader.GameModel;

/**
 * Government is defined by its type, leader, and level of anger
 *
 * @author David Purcell
 */
public class Government implements Serializable {

    public enum Type {

        ANARCHY, ARISTOCRACY, CAPITALIST, COMMUNIST,
        CORPORATE, DEMOCRACY, FASCIST, MERITOCRACY,
        MONARCHY, OLIGARCHY, TECHNOCRACY, THEOCRACY
    };
    private HashMap<Type, String> leaders;
    private Type type;
    private String leader;

    /**
     * No argument constructor for a Government
     */
    public Government() {
        type = Type.values()[GameModel.getRandom().nextInt(Type.values().length)];
        leaders = new HashMap<>();
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
     * Getter for the leader of a government
     * 
     * @return the leader's name
     */
    public String getLeader() {
        return leader;
    }

    /**
     * Setter for the new leader of a government
     * 
     * @param newLeader the new leader
     */
    public void setLeader(String newLeader) {
        leader = newLeader;
    }

    /**
     * Getter for the type of government
     * 
     * @return the type of government
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the government type to a new type
     * 
     * @param newType the new government type
     */
    public void setType(Type newType) {
        type = newType;
    }

    @Override
    public String toString() {
        String str = "Government Type: " + type + "\nLeader: " + leader;
        return str;
    }
}
