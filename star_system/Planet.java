package spacetrader.star_system;

import java.io.Serializable;
import spacetrader.GameModel;
import spacetrader.market.MarketPlace;

/**
 * Planet is defined by its government, resource level, circumstance, and tech level It has the
 * physical characteristics of color, orbit distance, and size It also knows whether or not the
 * player is currently there
 *
 * @author David Purcell
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
     *
     * @param orbitDistance
     * @param size
     */
    public Planet(double orbitDistance, double size) {
        environment = Environment.values()[GameModel.getRandom().nextInt(Environment.values().length)];

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
                seaLevel = 0.5f * GameModel.getRandom().nextFloat() + 0.01f;
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
        this.size = size;
        this.orbitDistance = orbitDistance;
        government = new Government();
        name = PlanetNames.getName(government);
        market = new MarketPlace(this);

        axialTilt = 45 * GameModel.getRandom().nextDouble();
        axialSpeed = 0.15 * GameModel.getRandom().nextDouble() + 0.05;
        orbitSpeed = Math.sqrt(1 / (20 * orbitDistance));

        seed = GameModel.getRandom().nextLong();
    }

    private <T> T choose(T... values) {
        return values[GameModel.getRandom().nextInt(values.length)];
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
    public double getOrbitDistance() {
        return orbitDistance;
    }

    /**
     *
     * @return
     */
    public double getOrbitSpeed() {
        return orbitSpeed;
    }

    /**
     *
     * @return
     */
    public double getAxialTilt() {
        return axialTilt;
    }

    public double getAxialSpeed() {
        return axialSpeed;
    }

    public long getSeed() {
        return seed;
    }

    public float getSeaLevel() {
        return seaLevel;
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

    public Environment getEnvironment() {
        return environment;
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
     * @return
     */
    public MarketPlace getMarket() {
        return market;
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
