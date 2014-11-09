package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
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
    private final CargoBay cargoBay;
    private EscapePod escapePod;
    private Insurance insurance;
    private int hull;
    private int shield;
    private Color color;
    private double fuel;

    /**
     * ShipType contains TYPE(hullStrength, shieldSlots, weaponSlots, cargoBaySlots, fuelCapacity)
     */
    public enum ShipType {

        //Name      hull  fuelC S  W  C   E     cost  color

        Flea(10, 30, 0, 0, 10, 1, 100, Color.BLUE),
        Gnat(100, 100, 0, 1, 15, 1, 200, Color.RED),
        Firefly(100, 200, 0, 1, 20, 2, 500, Color.GREEN),
        Mosquito(300, 100, 1, 2, 15, 2, 750, Color.ORANGE),
        Bumblebee(100, 200, 1, 2, 20, 2, 750, Color.YELLOW),
        Beetle(100, 1000, 1, 0, 50, 3, 1000, Color.PURPLE),
        Hornet(400, 100, 2, 3, 20, 3, 1000, Color.BROWN),
        Grasshopper(100, 200, 2, 2, 30, 3, 1000, Color.GREY),
        Termite(500, 1000, 3, 1, 60, 4, 5000, Color.WHITE),
        Wasp(500, 300, 2, 4, 35, 4, 5000, Color.ALICEBLUE);

        private int hullStrength;
        private final double fuelCapacity;
        private int weaponSlots;
        private int shieldSlots;
        private int engineSlots;
        private int cargoBaySlots;
        private final int cost;
        private final SerializableColor color;

        ShipType(int hullStrength, double fuelCapacity, int shieldSlots, int weaponSlots, int cargoBaySlots, int engineSlots, int cost, Color color) {
            this.hullStrength = hullStrength;
            this.fuelCapacity = fuelCapacity;
            this.shieldSlots = shieldSlots;
            this.weaponSlots = weaponSlots;
            this.cargoBaySlots = cargoBaySlots;
            this.engineSlots = engineSlots;
            this.cost = cost;
            this.color = new SerializableColor(color);
        }

        public Color getColor() {
            return color.getColor();
        }

        public int getCost() {
            return cost;
        }
    };

    public Ship(ShipType type, EscapePod escapePod, Insurance insurance) {
        this.type = type;
        shields = new Shield[type.shieldSlots];
        weapons = new Weapon[type.weaponSlots];
        engines = new Engine[type.engineSlots];
        cargoBay = new CargoBay(type.cargoBaySlots);
        hull = type.hullStrength;
        fuel = 0;
        this.escapePod = escapePod;
        this.insurance = insurance;
        this.shield = 0;
    }

    //Add things to ship
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

    public void addEscapePod(EscapePod escapePod) {
        this.escapePod = escapePod;
    }

    public void addInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public double addFuel(double newFuel) {
        fuel += newFuel;
        if (fuel > type.fuelCapacity) {
            fuel = type.fuelCapacity;
        }
        return fuel;
    }

    //Remove things from ship
    public Shield removeShield(int position) {
        Shield removed = null;
        if (position < shields.length) {
            removed = shields[position];
            shields[position] = null;
        }
        return removed;
    }

    public Weapon removeWeapon(int position) {
        Weapon removed = null;
        if (position < weapons.length) {
            removed = weapons[position];
            weapons[position] = null;
        }
        return removed;
    }

    public Engine removeEngine(int position) {
        Engine removed = null;
        if (position < engines.length) {
            removed = engines[position];
            engines[position] = null;
        }
        return removed;
    }

    public EscapePod removeEscapePod() {
        EscapePod removed = escapePod;
        escapePod = null;
        return removed;
    }

    public Insurance removeInsurance() {
        Insurance removed = insurance;
        insurance = null;
        return removed;
    }

    // Getters
    public Shield[] getShields() {
        return shields;
    }

    public int getShieldSlots() {
        return shields.length;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public int getWeaponSlots() {
        return weapons.length;
    }

    public Engine[] getEngines() {
        return engines;
    }

    public int getEngineSlots() {
        return engines.length;
    }

    public CargoBay getCargoBay() {
        return cargoBay;
    }

    public int getCargoBaySlots() {
        return cargoBay.getCapacity();
    }

    public EscapePod getEscapePod() {
        return escapePod;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public int getHull() {
        return hull;
    }

    public double getFuel() {
        return fuel;
    }

    public double getFuelCapacity() {
        return type.fuelCapacity;
    }

    public double getFuelEfficiency() {
        double fuelEfficiency = 0;
        for (Engine engine : engines) {
            if (engine != null) {
                fuelEfficiency += engine.getFuelEfficiency();
            }
        }
        return fuelEfficiency;
    }

    public double getMissingFuel() {
        return type.fuelCapacity - fuel;
    }

    public int getRange() {
        return (int) (fuel * getFuelEfficiency());
    }

    // Other functionality
    public boolean travelDistance(int distance) {
        boolean success = false;
        if (distance <= getRange()) {
            fuel = fuel - distance / getFuelEfficiency();
            success = true;
        }
        return success;
    }

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
