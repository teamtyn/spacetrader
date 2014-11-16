package spacetrader.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import spacetrader.items.Engine;
import spacetrader.items.Ship;
import spacetrader.star_system.Planet;
import spacetrader.star_system.StarSystem;
import spacetrader.ui.Point;

/**
 * The class representing the Player of the game.
 * @author Team TYN
 */
public final class Player extends AbstractCrewMember {
    /**
     * The list of planets that the player knows.
     */
    private final List<Planet> knownPlanets;
    /**
     * The point used to represent the player's location in the universe
     * as a whole.
     */
    private Point coord;
    /**
     * The star system that the player is in.
     */
    private StarSystem system;
    /**
     * The planet that the player is visiting currently.
     */
    private Planet planet;
    /**
     * The ship that the player currently owns.
     */
    private Ship ship;
    /**
     * The money that the player has.
     */
    private int money;
    /**
     * The money that the player starts with.
     */
    private static final int STARTING_MONEY = 1000;

    /**
     * The constructor for the Player class.
     */
    public Player() {
        super();
        knownPlanets = new ArrayList<>();
        coord = new Point(0, 0);
        ship = new Ship(Ship.ShipType.Gnat);
        ship.addCrewMember(this);
        ship.addEngine(new Engine());
        money = STARTING_MONEY;
    }

    /**
     * Setter for the money variable.
     * @param newMoney the player's money
     */
    public void setMoney(final int newMoney) {
        money = newMoney;
    }

    /**
     * Subtracts an integer amount from money.
     * @param withdrawal the amount of money to subtract
     */
    public void subtractMoney(final int withdrawal) {
        money -= withdrawal;
    }

    /**
     * Subtracts money from the player and, if the player doesn't have enough
     * money, subtracts all of the player's money.
     * @param withdrawal the amount of money attempting to remove
     * @return the amount of money ultimately removed
     */
        public int attemptToSubtractMoney(final int withdrawal) {
        int removed = withdrawal;
        if (withdrawal > this.money) {
            removed = this.money;
            this.money = 0;
        } else {
            this.money -= withdrawal;
        }
        return removed;
    }

    /**
     * Add specified amount of money.
     * @param cashflow the amount of money to add
     */
        public void addMoney(final int cashflow) {
        money += cashflow;
    }

    /**
     * Setter for the name variable.
     * @param newName the player's new name
     */
        public void setName(final String newName) {
        name = newName;
    }

    // Setter for the list of skills

    /**
     * Setter for the skills variable.
     * @param newSkills the new Map of Skills for the player
     */
     public void setSkillList(final Map<String, Skill> newSkills) {
        skills = newSkills;
    }

    /**
     * Setter for the value of a specific skill.
     * @param type the type of skill to set
     * @param value the new value of this skill
     */
        public void setSkill(final String type, final int value) {
        Skill found = skills.get(type);
        found.setValue(value);
        skills.put(type, found);
    }

    /**
     * Returns whether or not the player knows the specified planet.
     * @param aPlanet the planet whose known status is being requested
     * @return whether the planet is known
     */
    public boolean knowsPlanet(final Planet aPlanet) {
        return knownPlanets.contains(aPlanet);
    }

    /**
     * Setter for the ship variable.
     * @param newShip the player's ship
     */
    public void setShip(final Ship newShip) {
        ship = newShip;
    }

    /**
     * Setter for coordinates.
     * @param newLoc the new location of the player
     */
        public void setCoordinates(final Point newLoc) {
        coord = newLoc;
    }

    /**
     * Setter for the system.
     * @param newSystem the new system that the player is in
     */
    public void setSystem(final StarSystem newSystem) {
        system = newSystem;
    }

    /**
     * Setter for the planet.
     * @param newPlanet the planet that the player is currently visiting
     */
    public void setPlanet(final Planet newPlanet) {
        planet = newPlanet;
        knownPlanets.add(newPlanet);
    }

    /**
     * Increases the level of a skill by the specified value.
     * @param type the type of skill to be increased
     * @param value the new value of the skill
     */
    public void increaseSkill(final String type, final int value) {
        Skill found = skills.get(type);
        found.increaseValue(value);
        skills.put(type, found);
    }

    /**
     * Getter for money.
     * @return the player's money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Getter for the ship variable.
     * @return the player's ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Getter for coordinates.
     * @return the coordinates of the player
     */
    public Point getCoordinates() {
        return coord;
    }

    /**
     * Getter for the X coordinate of the player.
     * @return the x coordinate of the player
     */
    public double getX() {
        return coord.getX();
    }

    /**
     * Getter for the Y coordinate of the player.
     * @return the y coordinate of the player.
     */
    public double getY() {
        return coord.getY();
    }

    /**
     * Getter for the star system of the player.
     * @return the star system of the player
     */
    public StarSystem getSystem() {
        return system;
    }


    /**
     * Getter for the planet variable.
     * @return the planet of the player
     */
    public Planet getPlanet() {
        return planet;
    }

}
