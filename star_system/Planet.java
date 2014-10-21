package spacetrader.star_system;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.GameModel;
import spacetrader.market.MarketPlace;
import spacetrader.ui.SerializableColor;

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
    private final Circumstance circumstance;
    private ResourceLevel resourceLevel;
    private TechLevel techLevel;
    private final MarketPlace market;
    private final SerializableColor color;
    private final double size;
    public boolean hasPlayer;

    public Planet(double orbitDistance, double size) {
        resourceLevel = ResourceLevel.values()[GameModel.getRandom().nextInt(ResourceLevel.values().length)];
        techLevel = TechLevel.values()[GameModel.getRandom().nextInt(TechLevel.values().length)];
        circumstance = new Circumstance();
        this.size = size;
        color = new SerializableColor(Color.rgb(GameModel.getRandom().nextInt(256),
                GameModel.getRandom().nextInt(256),
                GameModel.getRandom().nextInt(256)));
        this.orbitDistance = orbitDistance;
        government = new Government();
        name = PlanetNames.getName(government);
        hasPlayer = false;
        market = new MarketPlace(this);
        axialTilt = 45 * GameModel.getRandom().nextDouble();
        orbitSpeed = Math.sqrt(1 / (20 * orbitDistance));
    }

    public MarketPlace getMarket() {
        return market;
    }
    
    public Color getColor() {
        return color.getColor();
    }

    public double getOrbitDistance(){
        return orbitDistance;
    }

    public double getOrbitSpeed() {
        return  orbitSpeed;
    }

    public double getAxialTilt() {
        return axialTilt;
    }

    public double getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public Government getGovernment() {
        return government;
    }

    public ResourceLevel getResourceLevel() {
        return resourceLevel;
    }

    public int getResourceLevelOrdinality() {
        return resourceLevel.ordinal();
    }

    public TechLevel getTechLevel() {
        return techLevel;
    }

    public int getTechLevelOrdinality() {
        return techLevel.ordinal();
    }

    public Circumstance getCircumstance() {
        return circumstance;
    }

    public void setResourceLevel(ResourceLevel resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public void setTechLevel(TechLevel techLevel) {
        this.techLevel = techLevel;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Planet: ").append(name).append("\nResource Level: ")
                .append(resourceLevel).append("\nTech Level: ").append(techLevel)
                        .append("\nUnder rule of ").append(government);
        return str.toString();
    }
}