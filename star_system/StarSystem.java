package spacetrader.star_system;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.GameModel;
import spacetrader.ui.Point;
import spacetrader.ui.SerializableColor;

/**
 * StarSystem contains an array of planets, and resides at some coordinates.
 * @author Team TYN
 */
public class StarSystem implements Serializable {
    /**
     * The name of the star system.
     */
    private final String name;
    /**
     * The coordinates of the star system.
     */
    private final Point coordinates;
    /**
     * The planets of the star system.
     */
    private final Planet[] planets;
    /**
     * The size of the star system.
     */
    private final double size;
    /**
     * The color of the star system.
     */
    private final SerializableColor color;
    /**
     * Whether or not the star system holds the player currently.
     */
    private boolean hasPlayer;
    /**
     * The minimum number of planets per system.
     */
    private static final int MIN_PLANETS = 4;
    /**
     * The maximum number of planets per system.
     */
    private static final int MAX_PLANETS = 10;
    /**
     * The minimum size of a star.
     */
    private static final int MIN_STAR_SIZE = 5;
    /**
     * The maximum number of planets per system.
     */
    private static final int MAX_STAR_SIZE = 15;
    /**
     * The red value of a star.
     */
    private static final int RED = 240;
    /**
     * The green value of a star.
     */
    private static final int GREEN = 255;
    /**
     * The blue value of a star.
     */
    private static final int BLUE = 100;
    /**
     * The initial minimum distance between planets.
     */
    private static final int MIN_DISTANCE = 20;
    /**
     * The initial remaining distance between planets.
     */
    private static final int REM_DISTANCE = 80;
    /**
     * Constructor for a StarSystem.
     * @param aName The name of the star system
     * @param aCoordinates The location of the star system within the universe
     */
    public StarSystem(final String aName, final Point aCoordinates) {
        name = aName;
        coordinates = aCoordinates;
        hasPlayer = false;
        planets = new Planet[GameModel.getRandom().nextInt(
                MAX_PLANETS - MIN_PLANETS) + MIN_PLANETS];
        setOrbits();
        size = GameModel.getRandom().nextInt(
                MAX_STAR_SIZE - MIN_STAR_SIZE) + MIN_STAR_SIZE;
        planets[GameModel.getRandom().nextInt(planets.length - 1)].setTechLevel(
                Planet.TechLevel.HIGHTECH);
        color = new SerializableColor(Color.rgb(RED, GREEN, BLUE));
    }

    /**
     * Getter for the name of the star system.
     * @return The name of the star system
     */
    public final String getName() {
        return name;
    }

    /**
     * Getter for the planets of the star system.
     * @return The planets of the star system
     */
    public final Planet[] getPlanets() {
        return planets;
    }

    /**
     * Getter for the size of the star system.
     * @return The size of the star system
     */
    public final double getSize() {
        return size;
    }

    /**
     * Getter for the color of the star system.
     * @return The color of the star system
     */
    public final Color getColor() {
        return color.getColor();
    }

    /**
     * Getter for the X coordinate of the star system.
     * @return The X coordinate of the star system
     */
    public final double getCoordinateX() {
        return coordinates.getX();
    }

    /**
     * Getter for the Y coordinate of the star system.
     * @return The Y coordinate of the star system
     */
    public final double getCoordinateY() {
        return coordinates.getY();
    }

    /**
     * Method to set the orbit distances for all the planets in the system.
     */
    public final void setOrbits() {
        double minDist = MIN_DISTANCE;
        double remDist = REM_DISTANCE;
        for (int i = 0; i < planets.length; i++) {
            final double planetSize = 2
                    * GameModel.getRandom().nextDouble() + 1;
            final double offset = (planets.length - i != 0)
                    ? remDist / (2 * (planets.length - i)) : remDist / 2;
            double distance = minDist + planetSize + offset + (offset / 3)
                    * GameModel.getRandom().nextGaussian();
            minDist += 2 * offset + planetSize;
            remDist -= 2 * offset + planetSize;
            planets[i] = new Planet(distance, planetSize);
        }
    }

    /**
     * Getter for the system distance of the star system.
     * @param other The other star system to measure the distance to
     * @return The system distance of the star system
     */
    public final double getSystemDistance(final StarSystem other) {
        final double deltaX = other.getCoordinateX() - coordinates.getX();
        final double deltaY = other.getCoordinateY() - coordinates.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Getter for the system distance of the star system.
     * @return the boolean indicating whether or not the system has the player
     */
    public final boolean ishasPlayer() {
        return hasPlayer;
    }

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("System: ").append(name)
                .append("\nStar System Coordinates:\n")
                .append(coordinates).append("\nPlanets: \n");
        for (Planet planet : planets) {
            builder.append(planet).append("\n");
        }
        return builder.toString();
    }
}
