package spacetrader.ui;

import java.io.Serializable;

/**
 * Stores x, y, and z coordinates of objects within the universe.
 *
 * @author Team TYN
 */
public final class Point implements Serializable {

    /**
     * The x coordinate.
     */
    private final double x;
    /**
     * The y coordinate.
     */
    private final double y;
    /**
     * The z coordinate.
     */
    private final double z;

    /**
     * Constructor for a two-dimensional point.
     *
     * @param newX the x coordinate
     * @param newY the y coordinate
     */
    public Point(final double newX, final double newY) {
        this(newX, newY, 0);
    }

    /**
     * Constructor for a three-dimensional point.
     *
     * @param newX the x coordinate
     * @param newY the y coordinate
     * @param newZ the z coordinate
     */
    public Point(final double newX, final double newY, final double newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    /**
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * @return the z coordinate
     */
    public double getZ() {
        return z;
    }
}
