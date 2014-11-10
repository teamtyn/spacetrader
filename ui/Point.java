package spacetrader.ui;

import java.io.Serializable;

/**
 * Point is a class used to store x,y,z cooridinates of objects within 
 * the universe
 * 
 * @author Team TYN
 */
public class Point implements Serializable {

    private final double x;
    private final double y;
    private final double z;

    /**
     * 2D Constructor for point
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this(x, y, 0);
    }

    /**
     * 3D Constructor for point
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Getter for the x coordinate
     * 
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for the y coordinate
     * 
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Getter for the z coordinate
     * 
     * @return the z coordinate
     */
    public double getZ() {
        return z;
    }
}
