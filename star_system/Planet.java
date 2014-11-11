package spacetrader.star_system;

import java.io.Serializable;
import spacetrader.GameModel;
import spacetrader.market.MarketPlace;

/**
 * Planet is defined by its government, resource level,
 *     circumstance, and tech level.
 * It has the physical characteristics of color, orbit distance, and size
 * It also knows whether or not the player is currently there
 * @author Team TYN
 */
public class Planet implements Serializable {
    public enum Environment {

        EARTH, LAVA, ICE, DESERT, ALIEN, ROCKY
    };

    public enum ResourceLevel {

        NOSPECIALRESOURCES, MINERALRICH, MINERALPOOR,
        DESERT, LOTSOFWATER, RICHSOIL,
        POORSOIL, RICHFAUNA, LIFELESS,
        WEIRDMUSHROOMS, LOTSOFHERBS,
        ARTISTIC, WARLIKE
    };

    public enum TechLevel {

        PREAGRICULTURAL, AGRICULTURAL,
        MEDIEVAL, RENAISSANCE,
        EARLYINDUSTRIAL, INDUSTRIAL,
        POSTINDUSTRIAL, HIGHTECH
    };

    private final double size;
    private final double orbitDistance;
    private final double orbitSpeed;
    private final double axialTilt;
    private final double axialSpeed;

    private final long seed;
    private final float seaLevel;

    private final Environment environment;
    private final ResourceLevel resourceLevel;
    private TechLevel techLevel;
    private Circumstance circumstance;
    private final Government government;
    private final String name;
    private final MarketPlace market;

    /**
     * Constructor for a planet.
     * @param aOrbitDistance The radius of the planet's orbit
     * @param aSize The radius of the planet
     */
    public Planet(final double aOrbitDistance, final double aSize) {
        environment = Environment.values()[
                GameModel.getRandom().nextInt(Environment.values().length)];
        switch (environment) {
            case EARTH:
                seaLevel = 0.1f * GameModel.getRandom().nextFloat() + 0.01f;
                resourceLevel = choose(
                        ResourceLevel.MINERALRICH,
                        ResourceLevel.LOTSOFWATER,
                        ResourceLevel.RICHSOIL,
                        ResourceLevel.RICHFAUNA,
                        ResourceLevel.LOTSOFHERBS);
                break;
            case LAVA:
                seaLevel = 0.07f * GameModel.getRandom().nextFloat() + 0.005f;
                resourceLevel = choose(
                        ResourceLevel.NOSPECIALRESOURCES,
                        ResourceLevel.MINERALRICH,
                        ResourceLevel.DESERT,
                        ResourceLevel.RICHSOIL,
                        ResourceLevel.LIFELESS);
                break;
            case ICE:
                seaLevel = 0.1f * GameModel.getRandom().nextFloat() + 0.01f;
                resourceLevel = choose(
                        ResourceLevel.NOSPECIALRESOURCES,
                        ResourceLevel.LOTSOFWATER,
                        ResourceLevel.MINERALPOOR,
                        ResourceLevel.POORSOIL,
                        ResourceLevel.LIFELESS);
                break;
            case DESERT:
                seaLevel = 0.005f * GameModel.getRandom().nextFloat() + 0.001f;
                resourceLevel = choose(
                        ResourceLevel.NOSPECIALRESOURCES,
                        ResourceLevel.DESERT,
                        ResourceLevel.POORSOIL,
                        ResourceLevel.LIFELESS);
                break;
            case ALIEN:
                seaLevel = 0.1f * GameModel.getRandom().nextFloat() + 0.01f;
                resourceLevel = choose(
                        ResourceLevel.MINERALRICH,
                        ResourceLevel.LOTSOFWATER,
                        ResourceLevel.RICHSOIL,
                        ResourceLevel.RICHFAUNA,
                        ResourceLevel.WEIRDMUSHROOMS,
                        ResourceLevel.LOTSOFHERBS);
                break;
            default:
                seaLevel = 0;
                resourceLevel = choose(
                        ResourceLevel.NOSPECIALRESOURCES,
                        ResourceLevel.MINERALRICH,
                        ResourceLevel.DESERT,
                        ResourceLevel.LIFELESS);
                break;
        }

        techLevel = TechLevel.values()[
                GameModel.getRandom().nextInt(TechLevel.values().length)];
        circumstance = new Circumstance();
        size = aSize;
        orbitDistance = aOrbitDistance;
        government = new Government();
        name = PlanetNames.getName(government);
        market = new MarketPlace(this);
        axialTilt = 45 * GameModel.getRandom().nextDouble();
        axialSpeed = 0.15 * GameModel.getRandom().nextDouble() + 0.05;
        orbitSpeed = Math.sqrt(1 / (20 * orbitDistance));
        seed = GameModel.getRandom().nextLong();
    }

    /**
     * 
     * @param <T>
     * @param values
     * @return 
     */
    private <T> T choose(final T... values) {
        return values[GameModel.getRandom().nextInt(values.length)];
    }

    /**
     * Getter for the size of the planet.
     * @return The size of the planet
     */
    public final double getSize() {
        return size;
    }

    /**
     * Getter for the orbit distance of the planet.
     * @return The orbit distance of the planet
     */
    public final double getOrbitDistance() {
        return orbitDistance;
    }

    /**
     * Getter for the orbit speed of the planet.
     * @return The orbit speed of the planet
     */
    public final double getOrbitSpeed() {
        return orbitSpeed;
    }

    /**
     * Getter for the axial tilt of the planet.
     * @return The axial tilt of the planet
     */
    public final double getAxialTilt() {
        return axialTilt;
    }

    /**
     * Getter for the axial speed of the planet.
     * @return The axial speed of the planet
     */
    public final double getAxialSpeed() {
        return axialSpeed;
    }

    /**
     * Getter for the seed of the planet.
     * @return The seed of the planet
     */
    public final long getSeed() {
        return seed;
    }

    /**
     * Getter for the sea level of the planet.
     * @return The sea level of the planet
     */
    public final float getSeaLevel() {
        return seaLevel;
    }

    /**
     * Getter for the name of the planet.
     * @return The name of the planet
     */
    public final String getName() {
        return name;
    }

    /**
     * Getter for the government of the planet.
     * @return The government of the planet
     */
    public final Government getGovernment() {
        return government;
    }

    /**
     * Getter for the environment of the planet.
     * @return The environment of the planet
     */
    public final Environment getEnvironment() {
        return environment;
    }

    /**
     * Getter for the resource level of the planet.
     * @return The resource level of the planet
     */
    public final ResourceLevel getResourceLevel() {
        return resourceLevel;
    }

    /**
     * Getter for the resource level of the planet.
     * @return The resource level of the planet
     */
    public final int getResourceLevelOrdinality() {
        return resourceLevel.ordinal();
    }

    /**
     * Getter for the tech level of the planet.
     * @return The tech level of the planet
     */
    public final TechLevel getTechLevel() {
        return techLevel;
    }

    /**
     * Getter for the tech level ordinality of the planet.
     * @return The tech level ordinality of the planet
     */
    public final int getTechLevelOrdinality() {
        return techLevel.ordinal();
    }

    /**
     * Getter for the circumstance of the planet.
     * @return The circumstance of the planet
     */
    public final Circumstance getCircumstance() {
        return circumstance;
    }

    /**
     * Getter for the market of the planet.
     * @return The market of the planet
     */
    public final MarketPlace getMarket() {
        return market;
    }

    /**
     * Setter for the tech level of the planet.
     * @param aTechLevel The new tech level for the planet
     */
    public final void setTechLevel(final TechLevel aTechLevel) {
        techLevel = aTechLevel;
    }

    @Override
    public final String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("Planet: ").append(name).append("\nResource Level: ")
                .append(resourceLevel).append("\nTech Level: ")
                .append(techLevel).append("\nUnder rule of ")
                .append(government);
        return str.toString();
    }
}
