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
    /**
     * The lower bound for a planet with an earth environment.
     */
    private static final float EARTH_SEA_LOW = 0.01f;
    /**
     * The upper bound for a planet with an earth environment.
     */
    private static final float EARTH_SEA_HIGH = 0.2f;
    /**
     * The lower bound for a planet with a lava environment.
     */
    private static final float LAVA_SEA_LOW = 0.005f;
    /**
     * The upper bound for a planet with an lava environment.
     */
    private static final float LAVA_SEA_HIGH = 0.07f;
    /**
     * The lower bound for a planet with an ice environment.
     */
    private static final float ICE_SEA_LOW = 0.01f;
    /**
     * The upper bound for a planet with an ice environment.
     */
    private static final float ICE_SEA_HIGH = 0.2f;
    /**
     * The lower bound for a planet with an desert environment.
     */
    private static final float DESERT_SEA_LOW = 0.001f;
    /**
     * The upper bound for a planet with an desert environment.
     */
    private static final float DESERT_SEA_HIGH = 0.005f;
    /**
     * The lower bound for a planet with an alien environment.
     */
    private static final float ALIEN_SEA_LOW = 0.01f;
    /**
     * The upper bound for a planet with an alien environment.
     */
    private static final float ALIEN_SEA_HIGH = 0.1f;

    /**
     * An enum representing all possible environments for a planet.
     */
    public enum Environment {
        /**
         * An earth environment.
         */
        EARTH,
        /**
         * A lava environment.
         */
        LAVA,
        /**
         * An ice environment.
         */
        ICE,
        /**
         * A desert environment.
         */
        DESERT,
        /**
         * An alien environment.
         */
        ALIEN,
        /**
         * A rocky environment.
         */
        ROCKY
    };

    /**
     * An enum representing all possible resource levels for a planet.
     */
    public enum ResourceLevel {
        NOSPECIALRESOURCES, MINERALRICH, MINERALPOOR,
        DESERT, LOTSOFWATER, RICHSOIL,
        POORSOIL, RICHFAUNA, LIFELESS,
        WEIRDMUSHROOMS, LOTSOFHERBS,
        ARTISTIC, WARLIKE
    };

    /**
     * An enum representing all possible tech levels for a planet.
     */
    public enum TechLevel {
        PREAGRICULTURAL, AGRICULTURAL,
        MEDIEVAL, RENAISSANCE,
        EARLYINDUSTRIAL, INDUSTRIAL,
        POSTINDUSTRIAL, HIGHTECH
    };

    /**
     * The size of the planet.
     */
    private final double size;
    /**
     * The orbit distance of the planet.
     */
    private final double orbitDistance;
    /**
     * The orbit speed of the planet.
     */
    private final double orbitSpeed;
    /**
     * The axial tilt of the planet.
     */
    private final double axialTilt;
    /**
     * The axial speed of the planet.
     */
    private final double axialSpeed;
    /**
     * The seed of the planet.
     */
    private final long seed;
    /**
     * The sea level of the planet.
     */
    private final float seaLevel;
    /**
     * The environment of the planet.
     */
    private final Environment environment;
    /**
     * The resource level of the planet.
     */
    private final ResourceLevel resourceLevel;
    /**
     * The tech level of the planet.
     */
    private TechLevel techLevel;
    /**
     * The circumstance of the planet.
     */
    private final Circumstance circumstance;
    /**
     * The government of the planet.
     */
    private final Government government;
    /**
     * The name of the planet.
     */
    private final String name;
    /**
     * The market of the planet.
     */
    private final MarketPlace market;

    /**
     * No arg constructor that Ryan is using for his JUnit test.
     * DO NOT USE IN ACTUAL GAME!
     */
    public Planet() {
        size = 0;
        orbitDistance = 0;
        orbitSpeed = 0;
        axialTilt = 0;
        axialSpeed = 0;
        seed = 0;
        seaLevel = 0;
        environment = null;
        resourceLevel = ResourceLevel.values()[
                GameModel.getRandom().nextInt(ResourceLevel.values().length)];
        techLevel = TechLevel.values()[
                GameModel.getRandom().nextInt(TechLevel.values().length)];
        circumstance = new Circumstance();
        government = new Government();
        name = PlanetNames.getName(government);
        market = null;
    }

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
                seaLevel = EARTH_SEA_HIGH * GameModel.getRandom().nextFloat()
                        + EARTH_SEA_LOW;
                resourceLevel = choose(
                        ResourceLevel.MINERALRICH,
                        ResourceLevel.LOTSOFWATER,
                        ResourceLevel.RICHSOIL,
                        ResourceLevel.RICHFAUNA,
                        ResourceLevel.LOTSOFHERBS);
                break;
            case LAVA:
                seaLevel = LAVA_SEA_HIGH * GameModel.getRandom().nextFloat()
                        + LAVA_SEA_LOW;
                resourceLevel = choose(
                        ResourceLevel.NOSPECIALRESOURCES,
                        ResourceLevel.MINERALRICH,
                        ResourceLevel.DESERT,
                        ResourceLevel.RICHSOIL,
                        ResourceLevel.LIFELESS);
                break;
            case ICE:
                seaLevel = ICE_SEA_HIGH * GameModel.getRandom().nextFloat()
                        + ICE_SEA_LOW;
                resourceLevel = choose(
                        ResourceLevel.NOSPECIALRESOURCES,
                        ResourceLevel.LOTSOFWATER,
                        ResourceLevel.MINERALPOOR,
                        ResourceLevel.POORSOIL,
                        ResourceLevel.LIFELESS);
                break;
            case DESERT:
                seaLevel = DESERT_SEA_HIGH * GameModel.getRandom().nextFloat()
                        + DESERT_SEA_LOW;
                resourceLevel = choose(
                        ResourceLevel.NOSPECIALRESOURCES,
                        ResourceLevel.DESERT,
                        ResourceLevel.POORSOIL,
                        ResourceLevel.LIFELESS);
                break;
            case ALIEN:
                seaLevel = ALIEN_SEA_HIGH * GameModel.getRandom().nextFloat()
                        + ALIEN_SEA_LOW;
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
