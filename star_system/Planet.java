package spacetrader.star_system;

import java.io.Serializable;
import spacetrader.GameModel;
import spacetrader.market.MarketPlace;

/**
 * Planet is defined by its government, resource level, circumstance, and tech level
 *   It has the physical characteristics of color, orbit distance, and size
 *   It also knows whether or not the player is currently there
 * @author David Purcell
 */
public class Planet implements Serializable {
    private final String name;
    private final double orbitDistance;
    private final double orbitSpeed;
    private final double axialTilt;
    private final Government government;

    public enum TechLevel {PREAGRICULTURAL, AGRICULTURAL,
                           MEDIEVAL, RENAISSANCE,
                           EARLYINDUSTRIAL, INDUSTRIAL,
                           POSTINDUSTRIAL, HIGHTECH};
    public enum ResourceLevel {NOSPECIALRESOURCES, MINERALRICH, MINERALPOOR,
                               DESERT, LOTSOFWATER, RICHSOIL,
                               POORSOIL, RICHFAUNA, LIFELESS,
                               WEIRDMUSHROOMS, LOTSOFHERBS,
                               ARTISTIC, WARLIKE};
    public enum Environment {EARTH, LAVA, ICE, DESERT, ALIEN, ROCKY};
    private final Circumstance circumstance;
    private ResourceLevel resourceLevel;
    private TechLevel techLevel;
    private Environment environment;
    private final MarketPlace market;
    private final double size;

    /**
     * 
     * @param orbitDistance
     * @param size 
     */
    public Planet(double orbitDistance, double size) {
        resourceLevel = ResourceLevel.values()[
                GameModel.getRandom().nextInt(ResourceLevel.values().length)];
        techLevel = TechLevel.values()[
                GameModel.getRandom().nextInt(TechLevel.values().length)];
        circumstance = new Circumstance();
        this.size = size;
        this.orbitDistance = orbitDistance;
        government = new Government();
        name = PlanetNames.getName(government);
        market = new MarketPlace(this);
        axialTilt = 45 * GameModel.getRandom().nextDouble();
        orbitSpeed = Math.sqrt(1 / (20 * orbitDistance));
    }

    /**
     * 
     * @return 
     */
    public MarketPlace getMarket() {
        return market;
    }

    /**
     * 
     * @return 
     */
    public double getOrbitDistance(){
        return orbitDistance;
    }

    /**
     * 
     * @return 
     */
    public double getOrbitSpeed() {
        return  orbitSpeed;
    }

    /**
     * 
     * @return 
     */
    public double getAxialTilt() {
        return axialTilt;
    }

    /**
     * 
     * @return 
     */
    public double getSize() {
        return size;
    }

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return 
     */
    public Government getGovernment() {
        return government;
    }

    /**
     * 
     * @return 
     */
    public ResourceLevel getResourceLevel() {
        return resourceLevel;
    }

    /**
     * 
     * @return 
     */
    public int getResourceLevelOrdinality() {
        return resourceLevel.ordinal();
    }

    /**
     * 
     * @return 
     */
    public TechLevel getTechLevel() {
        return techLevel;
    }

    /**
     * 
     * @return 
     */
    public int getTechLevelOrdinality() {
        return techLevel.ordinal();
    }

    /**
     * 
     * @return 
     */
    public Circumstance getCircumstance() {
        return circumstance;
    }

    /**
     * 
     * @param resourceLevel 
     */
    public void setResourceLevel(ResourceLevel resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    /**
     * 
     * @param techLevel 
     */
    public void setTechLevel(TechLevel techLevel) {
        this.techLevel = techLevel;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("Planet: ").append(name).append("\nResource Level: ")
            .append(resourceLevel).append("\nTech Level: ").append(techLevel)
            .append("\nUnder rule of ").append(government);
        return str.toString();
    }
}
