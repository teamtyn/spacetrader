package spacetrader.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.scene.paint.Color;
import spacetrader.items.Engine.EngineType;
import spacetrader.items.Shield.ShieldType;
import spacetrader.items.Weapon.WeaponType;
import spacetrader.player.AbstractCrewMember;
import spacetrader.player.Mercenary;
import spacetrader.player.Skill;
import spacetrader.ui.SerializableColor;

/**
 * Ship class, what the player travels around and transports cargo in.
 *
 * @author Team TYN
 */
public final class Ship implements Serializable {

    /**
     * The type of the ship.
     */
    private final ShipType type;
    /**
     * The shields installed in the ship.
     */
    private final Shield[] shields;
    /**
     * The weapons installed in the ship.
     */
    private final Weapon[] weapons;
    /**
     * The engines installed in the ship.
     */
    private final Engine[] engines;
    /**
     * The ship's crew members.
     */
    private final AbstractCrewMember[] crew;
    /**
     * The ship's cargo bay.
     */
    private final CargoBay cargoBay;
    /**
     * The number of crew members.
     */
    private int crewNumber;
    /**
     * The current hull strength.
     */
    private int hull;
    /**
     * The current fuel level.
     */
    private double fuel;
    

    /**
     * The enum used to store values constant across all ships of a type.
     */
    public enum ShipType {

        /**
         * A very cheap ship with almost no capabilities.
         */
        Flea(10, 30, 0, 0, 10, 1, 1, 100, Color.BLUE),
        /**
         * The most basic of ships, starting player ship.
         */
        Gnat(100, 100, 0, 1, 15, 1, 2, 200, Color.RED),
        /**
         * A slight improvement on the Gnat ship.
         */
        Firefly(100, 200, 0, 1, 20, 2, 5, 500, Color.GREEN),
        /**
         * A good ship for low level pirating.
         */
        Mosquito(300, 100, 1, 2, 15, 2, 7, 750, Color.ORANGE),
        /**
         * A good early ship for hauling lots of cargo.
         */
        Bumblebee(100, 200, 1, 2, 20, 2, 7, 750, Color.YELLOW),
        /**
         * A ship specialized to take lots of cargo long distances, then get
         * robbed.
         */
        Beetle(100, 1000, 1, 0, 50, 3, 10, 1000, Color.PURPLE),
        /**
         * The standard ship for interstellar pirating.
         */
        Hornet(400, 100, 2, 3, 20, 3, 10, 1000, Color.BROWN),
        /**
         * A very expensive but well rounded ship.
         */
        Grasshopper(100, 200, 2, 2, 30, 3, 10, 1000, Color.GREY),
        /**
         * The ultimate in long distance hauling from safety.
         */
        Termite(500, 1000, 3, 1, 60, 4, 20, 5000, Color.WHITE),
        /**
         * The ultimate in killing things and taking their stuff.
         */
        Wasp(500, 300, 2, 4, 35, 4, 20, 5000, Color.ALICEBLUE);

        /**
         * The maximum hull strength.
         */
        private int hullStrength;
        /**
         * The maximum fuel capacity.
         */
        private final double fuelCapacity;
        /**
         * The maximum number of weapons installed.
         */
        private int weaponSlots;
        /**
         * The maximum number of shields installed.
         */
        private int shieldSlots;
        /**
         * The maximum number of engines installed.
         */
        private int engineSlots;
        /**
         * The maximum number of cargo bays installed.
         */
        private int cargoBaySlots;
        /**
         * The maximum number of crew members.
         */
        private int crewSlots;
        /**
         * The cost of the ship.
         */
        private final int cost;
        /**
         * The color associated with the ship.
         */
        private final SerializableColor color;

        /**
         * Constructor for ShipType.
         *
         * @param aHullStrength the hull strength for this type of ship.
         * @param aFuelCapacity the fuel capacity for this type of ship.
         * @param aShieldSlots the shield slots for this type of ship.
         * @param aWeaponSlots the weapon slots for this type of ship.
         * @param aCargoBaySlots the cargo bay slots for this type of ship.
         * @param aEngineSlots the engine slots for this type of ship.
         * @param aCrewSlots the crew slots for this type of ship.
         * @param aCost the cost for this type of ship.
         * @param aColor the color used to fill in the square for this ship.
         */
        ShipType(final int aHullStrength, final double aFuelCapacity,
                final int aShieldSlots, final int aWeaponSlots,
                final int aCargoBaySlots, final int aEngineSlots,
                final int aCrewSlots, final int aCost, final Color aColor) {
            hullStrength = aHullStrength;
            fuelCapacity = aFuelCapacity;
            shieldSlots = aShieldSlots;
            weaponSlots = aWeaponSlots;
            cargoBaySlots = aCargoBaySlots;
            engineSlots = aEngineSlots;
            crewSlots = aCrewSlots;
            cost = aCost;
            color = new SerializableColor(aColor);
        }

        /**
         * Getter for the color of a ship.
         *
         * @return The color used for this ships rectangle.
         */
        public Color getColor() {
            return color.getColor();
        }

        /**
         * Getter for the cost of a ship.
         *
         * @return The base cost for this type of ship.
         */
        public int getCost() {
            return cost;
        }
    };

    /**
     * Constructor for a Ship.
     *
     * @param aType The ShipType that this ship is based on.
     */
    public Ship(final ShipType aType) {
        type = aType;
        shields = new Shield[type.shieldSlots];
        for (int i = 0; i < shields.length; i++) {
            if (shields[i] == null) {
                shields[i] = new Shield(ShieldType.EmptySlot);
            }
        }
        weapons = new Weapon[type.weaponSlots];
        for (int i = 0; i < weapons.length; i++) {
            if (weapons[i] == null) {
                weapons[i] = new Weapon(WeaponType.EmptySlot);
            }
        }
        engines = new Engine[type.engineSlots];
        for (int i = 0; i < engines.length; i++) {
            if (engines[i] == null) {
                engines[i] = new Engine(EngineType.EmptySlot);
            }
        }
        crew = new AbstractCrewMember[type.crewSlots];
        cargoBay = new CargoBay(type.cargoBaySlots);
        crewNumber = 0;
        hull = type.hullStrength;
        fuel = 0;
    }

    /**
     * MAKE A RANDOM SHIP.
     * @param difficulty
     * @return the random ship.
     */
    public static Ship randomShip(int difficulty){
        Random random = new Random();
        Ship newShip = new Ship(ShipType.values()[random.nextInt(
                                                  ShipType.values().length)]);

        for(int i=0; i<newShip.getWeaponSlots() ; i++){
            newShip.addWeapon(new Weapon(WeaponType.values()[random.nextInt(
                                                  WeaponType.values().length)]));
        }
        for(int i=0; i<newShip.getShieldSlots() ; i++){
            newShip.addShield(new Shield(ShieldType.values()[random.nextInt(
                                                  ShieldType.values().length)]));
        }
        for(int i=0; i<newShip.getEngineSlots() ; i++){
            newShip.addEngine(new Engine(EngineType.values()[random.nextInt(
                                                  EngineType.values().length)]));
        }
        
        for(int i=0; i<newShip.getCrewSlots(); i++){
            newShip.addCrewMember(new Mercenary());
        }
        
        System.out.println(newShip.toString());
        return newShip;
    }

    /**
     * @return the type
     */
    public ShipType getType() {
        return type;
    }

    /**
     * Adder for a new shield.
     *
     * @param newShield The new shield to be added.
     * @return Whether or not the add succeeded.
     */
    public boolean addShield(final Shield newShield) {
        boolean success = false;
        for (int i = 0; i < shields.length; i++) {
            if (shields[i] == null 
                    || shields[i].getType() == ShieldType.EmptySlot 
                    && !success) {
                shields[i] = newShield;
                success = true;
            }
        }
        return success;
    }

    /**
     * Adder for a new weapon.
     *
     * @param newWeapon the new weapon to be added.
     * @return whether or not the add succeeded.
     */
    public boolean addWeapon(final Weapon newWeapon) {
        boolean success = false;
        for (int i = 0; i < weapons.length; i++) {
            if (weapons[i] == null || 
                    weapons[i].getType() == WeaponType.EmptySlot 
                    && !success) {
                weapons[i] = newWeapon;
                success = true;
            }
        }
        return success;
    }

    /**
     * Adder for a new engine.
     *
     * @param newEngine the new engine to be added.
     * @return whether or not the add succeeded.
     */
    public boolean addEngine(final Engine newEngine) {
        boolean success = false;
        for (int i = 0; i < engines.length; i++) {
            if (engines[i] == null
                    || engines[i].getType() == EngineType.EmptySlot
                    && !success) {
                engines[i] = newEngine;
                success = true;
            }
        }
        return success;
    }

    /**
     * Adder for fuel.
     *
     * @param newFuel the amount of fuel to be added.
     * @return the new amount of fuel.
     */
    public double addFuel(final double newFuel) {
        fuel += newFuel;
        if (fuel > type.fuelCapacity) {
            fuel = type.fuelCapacity;
        }
        return fuel;
    }

    /**
     * Adder for crew members.
     *
     * @param cm the crew member to add.
     * @return whether or not the add succeeded.
     */
    public boolean addCrewMember(final AbstractCrewMember cm) {
        boolean success = false;
        if (crewNumber < getCrewSlots()) {
            crew[crewNumber] = cm;
            crewNumber++;
            success = true;
        }
        return success;
    }

    /**
     * Remover for a shield.
     *
     * @param position the index to be removed from.
     * @return the object that was removed.
     */
    public Shield removeShield(final int position) {
        Shield removed = null;
        if (position < shields.length) {
            removed = shields[position];
            shields[position] = new Shield(ShieldType.EmptySlot);
        }
        return removed;
    }

    /**
     * Remover for a weapon.
     *
     * @param position the index to be removed from.
     * @return the object that was removed.
     */
    public Weapon removeWeapon(final int position) {
        Weapon removed = null;
        if (position < weapons.length) {
            removed = weapons[position];
            weapons[position] = new Weapon(WeaponType.EmptySlot);
        }
        return removed;
    }

    /**
     * Remover for an engine.
     *
     * @param position the index to be removed from.
     * @return the object that was removed.
     */
    public Engine removeEngine(final int position) {
        Engine removed = null;
        if (position < engines.length) {
            removed = engines[position];
            engines[position] = new Engine(EngineType.EmptySlot);
        }
        return removed;
    }

    /**
     * Getter for shields.
     *
     * @return the shields for this ship.
     */
    public Shield[] getShields() {
        return shields;
    }

    /**
     * Getter for shield slots.
     *
     * @return the shield slots for this ship.
     */
    public int getShieldSlots() {
        return shields.length;
    }

    /**
     * Getter for weapons.
     *
     * @return the weapons for this ship.
     */
    public Weapon[] getWeapons() {
        return weapons;
    }

    /**
     * Getter for the number of weapon slots.
     *
     * @return the number of weapon slots.
     */
    public int getWeaponSlots() {
        return weapons.length;
    }

    /**
     * Getter for engines.
     *
     * @return the engines for this ship.
     */
    public Engine[] getEngines() {
        return engines;
    }

    /**
     * Getter for the number of engine slots.
     *
     * @return the number of engine slots on this ship.
     */
    public int getEngineSlots() {
        return engines.length;
    }

    /**
     * Getter for the crew.
     *
     * @return the crew for this ship.
     */
    public AbstractCrewMember[] getCrew() {
        return crew;
    }

    /**
     * Getter for number of crew slots.
     *
     * @return the number of crew slots on this ship.
     */
    public int getCrewSlots() {
        return crew.length;
    }

    /**
     * Getter for Cargo bay.
     *
     * @return the Cargo bay for this ship.
     */
    public CargoBay getCargoBay() {
        return cargoBay;
    }

    /**
     * @return the maximum number of installed cargo bays
     */
    public int getCargoBaySlots() {
        return cargoBay.getCapacity();
    }

    /**
     * Getter for hull.
     *
     * @return the hull for this ship.
     */
    public int getHull() {
        return hull;
    }

    /**
     * Getter for fuel.
     *
     * @return the fuel for this ship.
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * Getter for fuel capacity.
     *
     * @return the fuel capacity for this ship.
     */
    public double getFuelCapacity() {
        return type.fuelCapacity;
    }

    /**
     * Getter for fuel efficiency.
     *
     * @return the fuel efficiency for this ship.
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
     * @return the missing fuel for this ship.
     */
    public double getMissingFuel() {
        return type.fuelCapacity - fuel;
    }

    /**
     * Getter for range.
     *
     * @return the range for this ship.
     */
    public int getRange() {
        return (int) (fuel * getFuelEfficiency());
    }

    /**
     * Do a specified amount of damage to this ship.
     *
     * @param damage the amount of damage to be done.
     * @return the remaining hull strength.
     */
    public int takeDamage(final int damage) {
        int damageRemaining = damage;
        for (Shield s : shields) {
            if (s != null) {
                if (s.getStrength() >= damageRemaining) {
                    s.doDamage(damageRemaining);
                    damageRemaining = 0;
                }
            }
        }
        hull = Math.max(hull - damageRemaining, 0);
        if (hull == 0) {
            System.out.println("YOU DEAD");
            // Kertsplode
        }
        return hull;
    }

    /**
     * Repair a specified amount of damage to this ship.
     *
     * @param repairs the amount of damage to be undone.
     * @return the current hull strength.
     */
    public int repairHull(final int repairs) {
        hull += repairs;
        if (hull >= type.hullStrength) {
            hull = type.hullStrength;
        }
        return hull;
    }

    /**
     * Add a specified amount of a specified trade good to the ship.
     *
     * @param goodName the type of trade good to be added.
     * @param quantity the amount of trade goods to be added.
     * @return the number of goods successfully added.
     */
    public int storeTradeGood(final String goodName, final int quantity) {
        return cargoBay.addTradeGood(goodName, quantity);
    }

    /**
     * Remove a specified amount of a specified trade good to the ship.
     *
     * @param goodName the type of trade good to be removed.
     * @param quantity the amount of trade goods to be removed.
     * @return the number of goods successfully removed.
     */
    public int removeTradeGood(final String goodName, final int quantity) {
        return cargoBay.removeTradeGood(goodName, quantity);
    }

    /**
     * Return the remaining available space in the cargo bay.
     *
     * @return the remaining available space in the cargo bay.
     */
    public int getExtraSpace() {
        return cargoBay.getCapacity() - cargoBay.getCurrentSize();
    }

    /**
     * Set the fuel level of the ship to 0.
     */
    public void emptyFuel() {
        fuel = 0;
    }
    
    public Map<String, Skill> generateSkills() {
        Map<String, Skill> skills = new HashMap<String, Skill>();
        skills.put("piloting", new Skill("piloting"));
        skills.put("fighting", new Skill("fighting"));
        skills.put("trading", new Skill("trading"));
        skills.put("charming", new Skill("charming"));
        skills.put("engineering", new Skill("engineering"));
        for(AbstractCrewMember acme: crew) {
            if(acme != null) {
                System.out.println(skills.toString());
                skills = combineSkillMap(skills, acme.getSkills());
            }
        }
        return skills;
    }
    
    public String listShipSkillValues() {
        String string = "";
        for (Skill skill: generateSkills().values()) {
            string = string + skill.getType() + " : " + skill.getValue() + "\n";
        }
        return string;
    }
    
    public int calculateDamage() {
        float originalDamage = this.getWeapons()[0].getDamage();
        float damageMultiplier = calculateDamageMultiplier();
        return (int)(originalDamage * damageMultiplier);
    }
    
    private float calculateDamageMultiplier() {
       return 1.0f + (this.getShipSkillValue("fighting") / 60.0f);
    }
    
    public int getShipSkillValue(String skill) {
        return generateSkills().get(skill).getValue();
    }
    
    public Map<String, Skill> combineSkillMap(Map<String, Skill> map1, Map<String, Skill> map2) {
        Map<String, Skill> newMap = map1;
        for(Object object : map1.values()) {
             Skill skill = (Skill) object;
             skill.combineSkills((Skill)map2.get((skill.getType())));
            newMap.get(skill.getType()).setValue(skill.getValue());
        }
        return newMap;
        
    }
    
    public String toString(){
        String toString = this.getType().name();
        toString += "\nWeapons: ";
        for(Weapon weapon : weapons){
            toString += "\n" + weapon.toString();
        }
        toString += "\nShileds: ";
        for(Shield shield : shields){
            toString += "\n" + shield.toString();
        }
        toString += "\nEngines: ";
        for(Engine engine : engines){
            toString += "\n" + engine.toString();
        }
        toString += "\nCrew: ";
        for(AbstractCrewMember crew : crew){
            toString += "\n" + crew.getName();
        }
        return toString;
    }
}
