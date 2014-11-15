package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.player.AbstractCrewMember;
import spacetrader.player.Mercenary;
import spacetrader.ui.SerializableColor;

/**
 * Ship class, what the player travels around and transports cargo in
 *
 * @author David Purcell
 */
public class Ship implements Serializable {

    public ShipType type;
    private final Shield[] shields;
    private final Weapon[] weapons;
    private final Engine[] engines;
    private final AbstractCrewMember[] crew;
    private final CargoBay cargoBay;
    private int crewNumber;
    private int hull;
    private int shield;
    private Color color;
    private double fuel;

    /**
     * ShipType contains TYPE(hullStrength, shieldSlots, weaponSlots, cargoBaySlots, fuelCapacity)
     */
    public enum ShipType {

        //Name      hull  fuelC S  W  C   E     Cr   cost  color

        Flea(10, 30, 0, 0, 10, 1, 1, 100, Color.BLUE),
        Gnat(100, 100, 0, 1, 15, 1, 2, 200, Color.RED),
        Firefly(100, 200, 0, 1, 20, 2, 5, 500, Color.GREEN),
        Mosquito(300, 100, 1, 2, 15, 2, 7, 750, Color.ORANGE),
        Bumblebee(100, 200, 1, 2, 20, 2, 7, 750, Color.YELLOW),
        Beetle(100, 1000, 1, 0, 50, 3, 10, 1000, Color.PURPLE),
        Hornet(400, 100, 2, 3, 20, 3, 10,  1000, Color.BROWN),
        Grasshopper(100, 200, 2, 2, 30, 3, 10, 1000, Color.GREY),
        Termite(500, 1000, 3, 1, 60, 4, 50, 5000, Color.WHITE),
        Wasp(500, 300, 2, 4, 35, 4, 50, 5000, Color.ALICEBLUE);

        private int hullStrength;
        private final double fuelCapacity;
        private int weaponSlots;
        private int shieldSlots;
        private int engineSlots;
        private int cargoBaySlots;
        private int crewSlots;
        private final int cost;
        private final SerializableColor color;

        /**
         * Constructor for ShipType.
         *
         * @param hullStrength the hull strength for this type of ship
         * @param fuelCapacity the fuel capacity for this type of ship
         * @param shieldSlots the shield slots for this type of ship
         * @param weaponSlots the weapon slots for this type of ship
         * @param cargoBaySlots the cargo bay slots for this type of ship
         * @param engineSlots the engine slots for this type of ship
         * @param crewSlots the crew slots for this type of ship
         * @param cost the cost for this type of ship
         * @param color the color used to fill in the square for this ship
         */
        ShipType(int hullStrength, double fuelCapacity, int shieldSlots, int weaponSlots, int cargoBaySlots, int engineSlots, int crewSlots, int cost, Color color) {
            this.hullStrength = hullStrength;
            this.fuelCapacity = fuelCapacity;
            this.shieldSlots = shieldSlots;
            this.weaponSlots = weaponSlots;
            this.cargoBaySlots = cargoBaySlots;
            this.engineSlots = engineSlots;
            this.crewSlots = crewSlots;
            this.cost = cost;
            this.color = new SerializableColor(color);
        }

        /**
         * Getter for the color of a ship.
         *
         * @return the color used for this ships rectangle
         */
        public Color getColor() {
            return color.getColor();
        }

        /**
         * Getter for the cost of a ship.
         *
         * @return the base cost for this type of ship
         */
        public int getCost() {
            return cost;
        }
    };

    /**
     * Constructor for a Ship.
     *
     * @param type the ShipType that this ship is based on
     */
    public Ship(ShipType type) {
        this.type = type;
        shields = new Shield[type.shieldSlots];
        weapons = new Weapon[type.weaponSlots];
        engines = new Engine[type.engineSlots];
        crew = new AbstractCrewMember[type.crewSlots];
        cargoBay = new CargoBay(type.cargoBaySlots);
        crewNumber = 0;
        hull = type.hullStrength;
        fuel = 0;
        this.shield = 0;
    }

    /**
     * Adder for a new shield
     *
     * @param newShield the new shield to be added
     * @return whether or not the add succeeded
     */
    public boolean addShield(Shield newShield) {
        boolean success = false;
        for (int i = 0; i < shields.length; i++) {
            if (shields[i] == null && !success) {
                shields[i] = newShield;
                success = true;
            }
        }
        return success;
    }

    /**
     * Adder for a new weapon
     *
     * @param newWeapon the new weapon to be added
     * @return whether or not the add succeeded
     */
    public boolean addWeapon(Weapon newWeapon) {
        boolean success = false;
        for (int i = 0; i < weapons.length; i++) {
            if (weapons[i] == null && !success) {
                weapons[i] = newWeapon;
                success = true;
            }
        }
        return success;
    }

    /**
     * Adder for a new engine.
     *
     * @param newEngine the new engine to be added
     * @return whether or not the add succeeded
     */
    public boolean addEngine(Engine newEngine) {
        boolean success = false;
        for (int i = 0; i < engines.length; i++) {
            if (engines[i] == null && !success) {
                engines[i] = newEngine;
                success = true;
            }
        }
        return success;
    }

    /**
     * Adder for fuel.
     *
     * @param newFuel the amount of fuel to be added
     * @return the new amount of fuel
     */
    public double addFuel(double newFuel) {
        fuel += newFuel;
        if (fuel > type.fuelCapacity) {
            fuel = type.fuelCapacity;
        }
        return fuel;
    }
    
    public boolean addCrewMember(AbstractCrewMember cm) {
        boolean success = false;
        if(crewNumber < getCrewSlots()) {
            crew[crewNumber] = cm;
            crewNumber++;
            success = true;
        }
        return success;
    }

    /**
     * Remover for a shield.
     *
     * @param position the index to be removed from
     * @return the object that was removed
     */
    public Shield removeShield(int position) {
        Shield removed = null;
        if (position < shields.length) {
            removed = shields[position];
            shields[position] = null;
        }
        return removed;
    }

    /**
     * Remover for a weapon.
     *
     * @param position the index to be removed from
     * @return the object that was removed
     */
    public Weapon removeWeapon(int position) {
        Weapon removed = null;
        if (position < weapons.length) {
            removed = weapons[position];
            weapons[position] = null;
        }
        return removed;
    }

    /**
     * Remover for an engine.
     *
     * @param position the index to be removed from
     * @return the object that was removed
     */
    public Engine removeEngine(int position) {
        Engine removed = null;
        if (position < engines.length) {
            removed = engines[position];
            engines[position] = null;
        }
        return removed;
    }

    /**
     * Getter for shields.
     *
     * @return the shields for this ship
     */
    public Shield[] getShields() {
        return shields;
    }

    /**
     * Getter for shield slots.
     *
     * @return the shield slots for this ship
     */
    public int getShieldSlots() {
        return shields.length;
    }

    /**
     * Getter for weapons.
     *
     * @return the weapons for this ship
     */
    public Weapon[] getWeapons() {
        return weapons;
    }

    public int getWeaponSlots() {
        return weapons.length;
    }

    /**
     * Getter for engines.
     *
     * @return the engines for this ship
     */
    public Engine[] getEngines() {
        return engines;
    }
    
    /**
     * Getter for the number of engine slots.
     *
     * @return the number of engine slots on this ship
     */
    public int getEngineSlots() {
        return engines.length;
    }
    /**
     * Getter for the crew
     *
     * @return the crew for this ship
     */
    public AbstractCrewMember[] getCrew() {
        return crew;
    }
    /**
     * Getter for number of crew slots.
     *
     * @return the number of crew slots on this ship
     */
    public int getCrewSlots() {
        return crew.length;
    }

    /**
     * Getter for Cargo bay.
     *
     * @return the Cargo bay for this ship
     */
    public CargoBay getCargoBay() {
        return cargoBay;
    }

    public int getCargoBaySlots() {
        return cargoBay.getCapacity();
    }

    /**
     * Getter for hull.
     *
     * @return the hull for this ship
     */
    public int getHull() {
        return hull;
    }

    /**
     * Getter for fuel.
     *
     * @return the fuel for this ship
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * Getter for fuel capacity.
     *
     * @return the fuel capacity for this ship
     */
    public double getFuelCapacity() {
        return type.fuelCapacity;
    }

    /**
     * Getter for fuel efficiency.
     *
     * @return the fuel efficiency for this ship
     */
    public double getFuelEfficiency() {
        double fuelEfficiency = 0;
        for (Engine engine : engines) {
            if (engine != null) {
                fuelEfficiency += engine.getFuelEfficiency();
            }
        }
        return fuelEfficiency;
    }

    /**
     * Getter for missing.
     *
     * @return the missing fuel for this ship
     */
    public double getMissingFuel() {
        return type.fuelCapacity - fuel;
    }

    /**
     * Getter for range.
     *
     * @return the range for this ship
     */
    public int getRange() {
        return (int) (fuel * getFuelEfficiency());
    }

    /**
     * Do a specified amount of damage to this ship.
     *
     * @param damage the amount of damage to be done.
     * @return the remaining hull strength
     */
    public int takeDamage(int damage) {
        // Shields???
        if (hull - damage >= 0) {
            hull -= damage;
        } else {
            hull = 0;
        }
        if (hull == 0) {
            System.out.println("YOU DEAD");
            // Kertsplode
        }
        return hull;
    }

    public int repairHull(int repairs) {
        hull += repairs;
        if (hull >= type.hullStrength) {
            hull = type.hullStrength;
        }
        return hull;
    }

    public int storeTradeGood(String goodName, int quantity) {
        return cargoBay.addTradeGood(goodName, quantity);
    }

    public int removeTradeGood(String goodName, int quantity) {
        return cargoBay.removeTradeGood(goodName, quantity);
    }

    // Return the unused space of the cargo bay
    public int getExtraSpace() {
        return cargoBay.getCapacity() - cargoBay.getCurrentSize();
    }

    public void emptyFuel() {
        fuel = 0;
    }
}
