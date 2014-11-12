package spacetrader.star_system;

import java.util.TreeMap;
import javafx.scene.paint.Color;
import spacetrader.star_system.Planet.Environment;

/**
 * A color gradient stores a set of colors in a tree map, keying with
 * corresponding heights. The height value of each color is with respect to a
 * sea level, where -1 is the lowest point, 0 is at sea level, and 1 is the
 * highest point.
 *
 * @author Team TYN
 */
public final class ColorGradient {

    /**
     * The tree map used to sort the colors.
     */
    private TreeMap<Float, Color> colors;
    /**
     * The sea level.
     */
    private float seaLevel;

    /**
     * Constructs a color gradient with an empty tree map.
     *
     * @param sL The sea level.
     */
    public ColorGradient(final float sL) {
        colors = new TreeMap<>();
        seaLevel = sL;
    }

    /**
     * Constructs a default color gradient based on an environment.
     *
     * @param sL The seal level.
     * @param scheme The environment used for defining the color scheme.
     */
    public ColorGradient(final float sL, final Environment scheme) {
        colors = new TreeMap<>();
        seaLevel = sL;

        switch (scheme) {
            case EARTH:
                colors.put(-1f, Color.rgb(0, 47, 100));
                colors.put(-0.5f, Color.rgb(13, 87, 140));
                colors.put(-0.05f, Color.rgb(153, 204, 190));
                colors.put(0f, Color.rgb(235, 221, 162));
                colors.put(0.05f, Color.rgb(185, 199, 129));
                colors.put(0.1f, Color.rgb(49, 99, 33));
                colors.put(0.5f, Color.rgb(50, 56, 48));
                colors.put(0.8f, Color.rgb(136, 145, 121));
                colors.put(1f, Color.rgb(255, 255, 255));
                break;
            case LAVA:
                colors.put(-1f, Color.rgb(255, 255, 100));
                colors.put(-0.9f, Color.rgb(255, 200, 0));
                colors.put(-0.5f, Color.rgb(255, 100, 0));
                colors.put(-0.05f, Color.rgb(159, 57, 24));
                colors.put(0f, Color.rgb(45, 45, 45));
                colors.put(1f, Color.rgb(160, 160, 160));
                break;
            case ICE:
                colors.put(-1f, Color.rgb(36, 44, 64));
                colors.put(-0.05f, Color.rgb(91, 151, 212));
                colors.put(0f, Color.rgb(151, 182, 201));
                colors.put(0.5f, Color.rgb(128, 128, 128));
                colors.put(0.7f, Color.rgb(171, 199, 217));
                colors.put(1f, Color.rgb(255, 255, 255));
                break;
            case DESERT:
                colors.put(-1f, Color.rgb(41, 82, 82));
                colors.put(-0.05f, Color.rgb(107, 144, 144));
                colors.put(0f, Color.rgb(60, 128, 0));
                colors.put(0.01f, Color.rgb(217, 212, 143));
                colors.put(0.3f, Color.rgb(135, 120, 70));
                colors.put(1f, Color.rgb(105, 101, 90));
                break;
            case ALIEN:
                colors.put(-1f, Color.rgb(70, 42, 156));
                colors.put(-0.5f, Color.rgb(81, 48, 179));
                colors.put(-0.08f, Color.rgb(227, 89, 222));
                colors.put(0f, Color.rgb(150, 52, 47));
                colors.put(0.1f, Color.rgb(94, 20, 20));
                colors.put(0.5f, Color.rgb(28, 12, 12));
                colors.put(1f, Color.rgb(253, 245, 255));
                break;
            default:
                colors.put(-1f, Color.rgb(41, 22, 10));
                colors.put(1f, Color.rgb(145, 145, 145));
                break;
        }
    }

    /**
     * Adds a color to the color tree map.
     *
     * @param value A height value.
     * @param color The color to be added.
     */
    public void addColor(final float value, final Color color) {
        colors.put(value, color);
    }

    /**
     * Calculates an interpolated color given a value corresponding to an
     * absolute height (not with respect to sea level). This value should range
     * from 0 to 1;
     *
     * @param value The arbitrary value.
     * @return The interpolated color.
     */
    public Color getColor(final float value) {
        float height = value;
        if (seaLevel > 0 && seaLevel < 1) {
            if (height >= seaLevel) {
                height = (height - seaLevel) / (1 - seaLevel);
            } else {
                height = (height - seaLevel) / seaLevel;
            }
        }
        if (colors.containsKey(height)) {
            return colors.get(height);
        } else {
            float low = colors.floorKey(height);
            float high = colors.ceilingKey(height);
            double scalar = (height - low) / (high - low);
            return colors.get(low).interpolate(colors.get(high), scalar);
        }
    }
}
