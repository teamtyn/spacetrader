package spacetrader.star_system;

import java.util.Arrays;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * A noise generator is used to generate textures for a planet view.
 * A noise buffer can be specified and then filled by sampling a
 *     three dimensional simplex noise function
 * Once the noise buffer is filled, a diffuse map can be generated using a
 *     color gradient and a normal map can be generated using a sobel filter
 * @author Team TYN
 */
public class NoiseGenerator {

    public enum NoiseMode {
        /**
         * Indicates no post processing should be applied.
         */
        NONE,
        /**
         * Indicates values should follow a quadratic curve.
         */
        SQUARE,
        /**
         * Indicates values should follow a cubic curve.
         */
        CUBE,
        /**
         * Indicates values should follow an absolute value curve.
         */
        ABS
    };

    /**
     * Skew factor for simplex.
     */
    private static final double F3 = 1.0 / 3.0;

    /**
     * Unskew factor for simplex.
     */
    private static final double G3 = 1.0 / 6.0;
    
    /**
     * Cached array of possible gradient vectors.
     */
    private static final Grad[] GTAB = {new Grad(1, 1, 0), new Grad(-1, 1, 0), new Grad(1, -1, 0), new Grad(-1, -1, 0),
        new Grad(1, 0, 1), new Grad(-1, 0, 1), new Grad(1, 0, -1), new Grad(-1, 0, -1),
        new Grad(0, 1, 1), new Grad(0, -1, 1), new Grad(0, 1, -1), new Grad(0, -1, -1)};
    
    /**
     * List of numbers to be shuffled for the permutation table.
     */
    private static final short[] SUPPLY = new short[256];
    static {
        for (int i = 0; i < 256; i++) {
            SUPPLY[i] = (short) i;
        }
    }

    /**
     * The permutation table used for the noise function.
     */
    private final short[] perm;
    
    /**
     * The permutation table mod 12.
     */
    private final short[] permMod12 = new short[512];

    /**
     * The base frequency used for sampling. 
     */
    private final double baseFreq;
    
    /**
     * The base amplitude used for sampling.
     */
    private final double baseAmp;
    
    /**
     * The amount the frequency varies with each octave.
     */
    private final double lacunarity;
    
    /**
     * The amount the amplitude varies with each octave.
     */
    private final double gain;
    
    /**
     * The maximum number of octaves.
     */
    private final int octaveCap;
    
    /**
     * The type of post processing to use.
     */
    private final NoiseMode mode;
    
    /**
     * The color gradient used for diffuse map generation.
     */
    private final ColorGradient colors;

    /**
     * The noise buffer for this noise generator.
     */
    private float[][] noiseBuffer;
    
    /**
     * The width of the noise buffer.
     */
    private int width;
    
    /**
     * The height of the noise buffer.
     */
    private int height;

    /**
     * Constructs a noise generator based on specified parameters.
     * 
     * @param seed The seed for building a permutation table.
     * @param bF The base frequency used for the first octave.
     * @param bA The base amplitude used for the first octave.
     * @param l The lacunarity. Defines how frequency changes for each octave.
     * @param g The gain. Defines how amplitude changes for each octave.
     * @param oC The maximum number of octaves to run.
     * @param m The type of post processing to use.
     * @param c The color gradient to use for diffuse map calculation.
     */
    public NoiseGenerator(long seed, double bF, double bA, double l, double g, int oC, NoiseMode m, ColorGradient c) {
        Random r = new Random(seed);
        perm = Arrays.copyOf(SUPPLY, 512);
        for (int i = 0; i < 256; i++) {
            int j = r.nextInt(256);
            short temp = perm[i];
            perm[i] = perm[j];
            perm[j] = temp;
        }
        for (int i = 0; i < 512; i++) {
            perm[i] = perm[i & 255];
            permMod12[i] = (short) (perm[i] % 12);
        }
        baseFreq = bF;
        baseAmp = bA;
        lacunarity = l;
        gain = g;
        octaveCap = oC;
        mode = m;
        colors = c;
    }

    private static int fastfloor(double x) {
        int floorX = (int) x;
        return x < floorX ? floorX - 1 : floorX;
    }

    private static double dot(Grad g, double x, double y, double z) {
        return g.x * x + g.y * y + g.z * z;
    }

    /**
     * A three dimensional noise function based on the paper found here:
     * http://webstaff.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf
     * 
     * @param xin The x coordinate of the sample point.
     * @param yin The y coordinate of the sample point.
     * @param zin The z coordinate of the sample point.
     * @return The noise value at the point (x, y, z).
     */
    private double noise(double xin, double yin, double zin) {
        double n0, n1, n2, n3;
        double s = (xin + yin + zin) * F3;
        int i = fastfloor(xin + s);
        int j = fastfloor(yin + s);
        int k = fastfloor(zin + s);
        double t = (i + j + k) * G3;
        double x0 = xin - (i - t);
        double y0 = yin - (j - t);
        double z0 = zin - (k - t);
        int i1, j1, k1;
        int i2, j2, k2;
        if (x0 >= y0) {
            if (y0 >= z0) {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            } else if (x0 >= z0) {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            } else {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            }
        } else {
            if (y0 < z0) {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } else if (x0 < z0) {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } else {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            }
        }
        double x1 = x0 - i1 + G3;
        double y1 = y0 - j1 + G3;
        double z1 = z0 - k1 + G3;
        double x2 = x0 - i2 + 2.0 * G3;
        double y2 = y0 - j2 + 2.0 * G3;
        double z2 = z0 - k2 + 2.0 * G3;
        double x3 = x0 - 1.0 + 3.0 * G3;
        double y3 = y0 - 1.0 + 3.0 * G3;
        double z3 = z0 - 1.0 + 3.0 * G3;
        int ii = i & 255;
        int jj = j & 255;
        int kk = k & 255;
        int gi0 = permMod12[ii + perm[jj + perm[kk]]];
        int gi1 = permMod12[ii + i1 + perm[jj + j1 + perm[kk + k1]]];
        int gi2 = permMod12[ii + i2 + perm[jj + j2 + perm[kk + k2]]];
        int gi3 = permMod12[ii + 1 + perm[jj + 1 + perm[kk + 1]]];
        double t0 = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;
        if (t0 < 0) {
            n0 = 0.0;
        } else {
            t0 *= t0;
            n0 = t0 * t0 * dot(GTAB[gi0], x0, y0, z0);
        }
        double t1 = 0.6 - x1 * x1 - y1 * y1 - z1 * z1;
        if (t1 < 0) {
            n1 = 0.0;
        } else {
            t1 *= t1;
            n1 = t1 * t1 * dot(GTAB[gi1], x1, y1, z1);
        }
        double t2 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
        if (t2 < 0) {
            n2 = 0.0;
        } else {
            t2 *= t2;
            n2 = t2 * t2 * dot(GTAB[gi2], x2, y2, z2);
        }
        double t3 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
        if (t3 < 0) {
            n3 = 0.0;
        } else {
            t3 *= t3;
            n3 = t3 * t3 * dot(GTAB[gi3], x3, y3, z3);
        }
        return 32.0 * (n0 + n1 + n2 + n3);
    }

    /**
     * Initializes the noise buffer given a width and height.
     * @param w The width of the noise buffer.
     * @param h The height of the noise buffer.
     */
    public void initNoiseBuffer(int w, int h) {
        width = w;
        height = h;
        noiseBuffer = new float[h][w];
    }

    /**
     * Clears the noise buffer. This should be called after all desired textures
     * have been generated to save memory.
     */
    public void clearBuffer() {
        width = 0;
        height = 0;
        noiseBuffer = null;
    }

    /**
     * Fills the noise buffer with values corresponding to heights. The noise
     * function is sampled along the surface of a sphere with a radius equal to
     * the frequency at each octave. This result is projected onto the two
     * dimensional buffer using a cylindrical projection. Octaves will continue
     * to be calculated until the octave cap is reached or until max detail for
     * the current resolution is reached.
     * 
     * A second pass on the noise buffer applies any post processing defined by
     * noise mode and normalizes the final result.
     * 
     * This method is computationally expensive and is intended to be run
     * concurrently. If it needs to be stopped suddenly, the thread should be
     * interrupted.
     */
    public void addOctaves() {
        boolean cancelled = false;
        float max = 0;
        int octaves = Math.min((int) (Math.log(1 / (baseFreq * 2.0 * Math.PI / width)) / Math.log(lacunarity)), octaveCap);
        noiseLoop:
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (Thread.interrupted()) {
                    cancelled = true;
                    break noiseLoop;
                }
                double normU = i / (double) width;
                double normV = 1 - (2.0 * j / height);

                double x = Math.cos(2.0 * Math.PI * normU) * Math.sqrt(1.0 - normV * normV);
                double y = Math.sin(2.0 * Math.PI * normU) * Math.sqrt(1.0 - normV * normV);
                double z = normV;

                x *= baseFreq;
                y *= baseFreq;
                z *= baseFreq;

                float noiseSum = 0;
                double amplitude = baseAmp;
                for (int k = 1; k < octaves; k++) {
                    noiseSum += noise(x, y, z) * amplitude;
                    x *= lacunarity;
                    y *= lacunarity;
                    z *= lacunarity;
                    amplitude *= gain;
                }
                noiseBuffer[j][i] += noiseSum;
                if (Math.abs(noiseBuffer[j][i]) > max) {
                    max = Math.abs(noiseBuffer[j][i]);
                }
            }
        }

        if (!cancelled) {
            normalizeLoop:
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    if (Thread.interrupted()) {
                        break normalizeLoop;
                    }
                    noiseBuffer[j][i] /= max;
                    switch (mode) {
                        case NONE:
                            noiseBuffer[j][i] = 0.5f * (1 + noiseBuffer[j][i]);
                            break;
                        case SQUARE:
                            noiseBuffer[j][i] = (float) Math.pow(noiseBuffer[j][i], 2);
                            break;
                        case CUBE:
                            noiseBuffer[j][i] = 0.5f * (1 + (float) Math.pow(noiseBuffer[j][i], 3));
                            break;
                        case ABS:
                            noiseBuffer[j][i] = (float) Math.abs(noiseBuffer[j][i]);
                    }
                }
            }
        }
    }

    /**
     * Generates a diffuse map, coloring pixels based on the specified color
     * gradient. Diffuse maps are interpolated in JavaFx so the map will be
     * scaled if the noise buffer is greater than a resolution of 500000.
     * @return The diffuse map.
     */
    public Image getDiffuse() {
        int scaleFactor;
        if (width * height >= 500000) {
            scaleFactor = 2;
        } else {
            scaleFactor = 1;
        }

        int widthScaled = (int) Math.ceil((double) width / scaleFactor);
        int heightScaled = (int) Math.ceil((double) height / scaleFactor);
        WritableImage img = new WritableImage(widthScaled, heightScaled);
        PixelWriter writer = img.getPixelWriter();
        diffuseLoop:
        for (int j = 0; j < heightScaled; j++) {
            for (int i = 0; i < widthScaled; i++) {
                if (Thread.interrupted()) {
                    break diffuseLoop;
                }
                writer.setColor(i, j, colors.getColor(noiseBuffer[j * scaleFactor][i * scaleFactor]));
            }
        }
        return img;
    }

    /**
     * Generates `a normal map by applying a sobel filter to the noise buffer.
     * @param intensity The intensity of the the sobel filter.
     * @return The normal map.
     */
    public Image getNormal(double intensity) {
        if (width * height < 500000) {
            return null;
        }
        WritableImage img = new WritableImage(width, height);
        PixelWriter writer = img.getPixelWriter();
        normalLoop:
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (Thread.interrupted()) {
                    break normalLoop;
                }
                double tl = noiseBuffer[(height + j - 1) % height][(width + i - 1) % width];
                double t = noiseBuffer[(height + j - 1) % height][i];
                double tr = noiseBuffer[(height + j - 1) % height][(i + 1) % width];
                double r = noiseBuffer[j][(i + 1) % width];
                double br = noiseBuffer[(j + 1) % height][(i + 1) % width];
                double b = noiseBuffer[(j + 1) % height][i];
                double bl = noiseBuffer[(j + 1) % height][(width + i - 1) % width];
                double l = noiseBuffer[j][(width + i - 1) % width];
                double dx = (tl + 2.0 * l + bl) - (tr + 2.0 * r + br);
                double dy = (tl + 2.0 * t + tr) - (bl + 2.0 * b + br);
                double dz = 1.0 / intensity;
                double mag = Math.sqrt(dx * dx + dy * dy + dz * dz);
                dx = 0.5 * (1 + dx / mag);
                dy = 0.5 * (1 + dy / mag);
                dz = 0.5 * (1 + dz / mag);
                writer.setColor(i, j, Color.color(dx, dy, dz));
            }
        }
        return img;
    }

    private static class Grad {
        double x, y, z;
        Grad(double aX, double aY, double aZ) {
            x = aX;
            y = aY;
            z = aZ;
        }
    }
}
