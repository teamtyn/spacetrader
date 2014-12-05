/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.util.TreeMap;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import spacetrader.star_system.Planet.Environment;

/**
 *
 * @author Administrator
 */
public class TextureGradient {
    /**
     * The tree map used to sort the textures.
     */
    private TreeMap<Float, Image> textures;
    
    /**
     * Constructs a texture gradient with an empty tree map.
     */
    private TextureGradient() {
        textures = new TreeMap<>();
    }
    
    public static TextureGradient factory(Environment scheme) {
        TextureGradient tg = new TextureGradient();
        
        switch(scheme) {
            case EARTH:
                tg.addTexture(5f, "grass.jpg");
                tg.addTexture(25f, "dirt.jpg");
                tg.addTexture(125f, "stone.png");
                tg.addTexture(175f, "snow.jpg");
                break;
            case LAVA:
                tg.addTexture(2f, "lavaRock.jpg");
                tg.addTexture(10f, "blackRock.jpg");
                tg.addTexture(25f, "darkGrayRock.jpg");
                tg.addTexture(125f, "lightGrayRock.png");
                break;
            case ICE:
                tg.addTexture(5f, "snow.jpg");
                tg.addTexture(25f, "stone.png");
                tg.addTexture(125f, "ice.jpg");
                tg.addTexture(175, "snow.jpg");
                break;
            case DESERT:
                tg.addTexture(5f, "sand.jpg");
                tg.addTexture(25f, "sandstone.jpg");
                tg.addTexture(175f, "sand.jpg");
                break;
            case ALIEN:
                tg.addTexture(5f, "alien.jpg");
                tg.addTexture(120f, "stone.png");
                tg.addTexture(200f, "snow.jpg");
                break;
        }
        
        return tg;
    }
    
    private void addTexture(float value, String name) {
        textures.put(value,
                new Image(getClass().getResource(name).toExternalForm()));
    }

    
    /**
     *
     * @param x
     * @param y
     * @param value
     * @return 
     */
    public Color getPixel(final int x, final int y, final float value) {
        if (value >= textures.lastKey()) {
            Image tex = textures.get(textures.lastKey());
            return tex.getPixelReader().getColor(x % (int) tex.getWidth(), y % (int) tex.getHeight());
        } else if (value <= textures.firstKey()) {
            Image tex = textures.get(textures.firstKey());
            return tex.getPixelReader().getColor(x % (int) tex.getWidth(), y % (int) tex.getHeight());
        } else if (textures.containsKey(value)) {
            Image tex = textures.get(value);
            return tex.getPixelReader().getColor(x % (int) tex.getWidth(), y % (int) tex.getHeight());
        } else {
            float low = textures.floorKey(value);
            float high = textures.ceilingKey(value);
            double scalar = (value - low) / (high - low);
            
            Image texLow = textures.get(low);
            Image texHigh = textures.get(high);
            Color c = texLow.getPixelReader().getColor(
                    x % (int) texLow.getWidth(),
                    y % (int) texLow.getHeight());
            return c.interpolate(
                    texHigh.getPixelReader().getColor(
                            x % (int) texHigh.getWidth(),
                            y % (int) texHigh.getHeight()), scalar);
        }
    }
}
