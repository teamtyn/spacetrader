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
    private ColorGradient(final float sL) {
        colors = new TreeMap<>();
        seaLevel = sL;
    }
    
    /**
     * A factory method for creating color gradients.
     *
     * @param sL The sea level.
     * @param scheme The environment color scheme.
     * @return A new instance of a color gradient.
     */
    public static ColorGradient factory(
            final float sL, final Environment scheme) {
        ColorGradient cg = new ColorGradient(sL);

        switch (scheme) {
            case EARTH:
                cg.addColor(-1f, Color.rgb(0, 47, 100));
                cg.addColor(-0.5f, Color.rgb(13, 87, 140));
                cg.addColor(-0.05f, Color.rgb(153, 204, 190));
                cg.addColor(0f, Color.rgb(235, 221, 162));
                cg.addColor(0.05f, Color.rgb(185, 199, 129));
                cg.addColor(0.1f, Color.rgb(49, 99, 33));
                cg.addColor(0.5f, Color.rgb(50, 56, 48));
                cg.addColor(0.8f, Color.rgb(136, 145, 121));
                cg.addColor(1f, Color.rgb(255, 255, 255));
                break;
            case LAVA:
                cg.addColor(-1f, Color.rgb(255, 255, 100));
                cg.addColor(-0.9f, Color.rgb(255, 200, 0));
                cg.addColor(-0.5f, Color.rgb(255, 100, 0));
                cg.addColor(-0.05f, Color.rgb(159, 57, 24));
                cg.addColor(0f, Color.rgb(45, 45, 45));
                cg.addColor(1f, Color.rgb(160, 160, 160));
                break;
            case ICE:
                cg.addColor(-1f, Color.rgb(36, 44, 64));
                cg.addColor(-0.05f, Color.rgb(91, 151, 212));
                cg.addColor(0f, Color.rgb(151, 182, 201));
                cg.addColor(0.5f, Color.rgb(128, 128, 128));
                cg.addColor(0.7f, Color.rgb(171, 199, 217));
                cg.addColor(1f, Color.rgb(255, 255, 255));
                break;
            case DESERT:
                cg.addColor(-1f, Color.rgb(41, 82, 82));
                cg.addColor(-0.05f, Color.rgb(107, 144, 144));
                cg.addColor(0f, Color.rgb(60, 128, 0));
                cg.addColor(0.01f, Color.rgb(217, 212, 143));
                cg.addColor(0.3f, Color.rgb(135, 120, 70));
                cg.addColor(1f, Color.rgb(105, 101, 90));
                break;
            case ALIEN:
                cg.addColor(-1f, Color.rgb(70, 42, 156));
                cg.addColor(-0.5f, Color.rgb(81, 48, 179));
                cg.addColor(-0.08f, Color.rgb(227, 89, 222));
                cg.addColor(0f, Color.rgb(150, 52, 47));
                cg.addColor(0.1f, Color.rgb(94, 20, 20));
                cg.addColor(0.5f, Color.rgb(28, 12, 12));
                cg.addColor(1f, Color.rgb(253, 245, 255));
                break;
            default:
                cg.addColor(-1f, Color.rgb(41, 22, 10));
                cg.addColor(1f, Color.rgb(145, 145, 145));
                break;
        }
        
        return cg;
    }

    /**
     * Adds a color to the color tree map.
     *
     * @param value A height value.
     * @param color The color to be added.
     */
    private void addColor(final float value, final Color color) {
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
