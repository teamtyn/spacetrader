package spacetrader.ui;

import java.io.Serializable;

public class Point implements Serializable {

    private final double x;
    private final double y;
    private final double z;

    /**
     *
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this(x, y, 0);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public double getZ() {
        return z;
    }
}
