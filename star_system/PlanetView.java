package spacetrader.star_system;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import spacetrader.Xform;
import spacetrader.GameModel;
import spacetrader.star_system.NoiseGenerator.NoiseMode;

/**
 * A planet view is a visual representation of a planet. Planet views are
 * automatically placed in a hierarchy of transformation Xform that allow for
 * easy manipulation of their relative positions.
 *
 * Each planet view has a noise generator used for generating diffuse and normal
 * textures.
 *
 * @author Team TYN
 */
public class PlanetView extends Sphere {

    /**
     * Low detail texture width.
     */
    public static final int WIDTH_LOW = 100;
    /**
     * Low detail texture height.
     */
    public static final int HEIGHT_LOW = 50;
    /**
     * Medium detail texture width.
     */
    public static final int WIDTH_MED = 1000;
    /**
     * Medium detail texture height.
     */
    public static final int HEIGHT_MED = 500;
    /**
     * High detail texture width.
     */
    public static final int WIDTH_HIGH = 2000;
    /**
     * High detail texture height.
     */
    public static final int HEIGHT_HIGH = 1000;

    /**
     * The base frequency for noise generation.
     */
    private static final double BASE_FREQ = 0.5;
    /**
     * The base amplitude for noise generation.
     */
    private static final double BASE_AMP = 1;
    /**
     * The lacunarity for noise generation.
     */
    private static final double LACUNARITY = 2;
    /**
     * The gain for noise generation.
     */
    private static final double GAIN = 0.5;
    /**
     * The octave cap for noise generation.
     */
    private static final int OCTAVE_CAP = 15;
    /**
     * The normal intensity for normal map calculation.
     */
    private static final int NORMAL_INTENSITY = 7;
    /**
     * Planet represented by this planet view.
     */
    private final Planet planet;
    /**
     * The orbit level Xform.
     */
    private final Xform orbitXform;
    /**
     * The axis level Xform.
     */
    private final Xform axisXform;
    /**
     * The noise generator used for texture generation.
     */
    private final NoiseGenerator noise;
    /**
     * The service that generates noise.
     */
    private final GenerateNoiseService generateNoise;
    /**
     * The service that generates a diffuse map.
     */
    private final GenerateDiffuseService generateDiffuse;
    /**
     * The service that generates a normal map.
     */
    private final GenerateNormalService generateNormal;

    /**
     * Constructs a planet view object given a planet.
     *
     * @param aPlanet The planet to be represented by this planet view.
     */
    public PlanetView(final Planet aPlanet) {
        super(aPlanet.getSize());
        planet = aPlanet;
        orbitXform = new Xform();
        axisXform = new Xform();
        final PhongMaterial material = new PhongMaterial();
        setMaterial(material);
        final ColorGradient colors = ColorGradient.factory(
                planet.getSeaLevel(), planet.getEnvironment());

        noise = new NoiseGenerator(planet.getSeed(), BASE_FREQ, BASE_AMP,
                LACUNARITY, GAIN, OCTAVE_CAP, NoiseMode.SQUARE, colors, null);
        noise.initNoiseBuffer(WIDTH_LOW, HEIGHT_LOW);
        noise.addOctaves();
        material.setDiffuseMap(noise.getDiffuse());
        noise.clearBuffer();

        generateNoise = new GenerateNoiseService();
        generateDiffuse = new GenerateDiffuseService();
        generateNormal = new GenerateNormalService();
        initTextures(material);

        setRotationAxis(Rotate.Y_AXIS);
        axisXform.setRotate(90 + planet.getAxialTilt(),
                0, 360 * GameModel.getRandom().nextDouble());
        orbitXform.setRz(360 * GameModel.getRandom().nextDouble());
        axisXform.getChildren().add(this);
        orbitXform.getChildren().add(axisXform);
    }

    /**
     * Getter for the planet represented by this planet view.
     *
     * @return The planet represented by this planet view.
     */
    public final Planet getPlanet() {
        return planet;
    }

    /**
     * Getter for the orbit level Xform.
     *
     * @return The orbit level Xform.
     */
    public final Xform getOrbitXform() {
        return orbitXform;
    }

    /**
     * Getter for the axis level Xform.
     *
     * @return The axis level Xform.
     */
    public final Xform getAxisXform() {
        return axisXform;
    }

    /**
     * Gets the x offset of this planet view at the axis level.
     *
     * @return The x offset.
     */
    public final double getOffsetX() {
        return axisXform.getTx();
    }

    /**
     * Gets the y offset of this planet view at the axis level.
     *
     * @return The y offset.
     */
    public final double getOffsetY() {
        return axisXform.getTy();
    }

    /**
     * Gets the z offset of this planet view at the axis level.
     *
     * @return The z offset.
     */
    public final double getOffsetZ() {
        return axisXform.getTz();
    }

    /**
     * Gets the scene x of this planet view.
     *
     * @return The scene x.
     */
    public final double getX() {
        return getLocalToSceneTransform().getTx();
    }

    /**
     * Gets the scene y of this planet view.
     *
     * @return The scene y.
     */
    public final double getY() {
        return getLocalToSceneTransform().getTy();
    }

    /**
     * Gets the scene z of this planet view.
     *
     * @return The scene z.
     */
    public final double getZ() {
        return getLocalToSceneTransform().getTz();
    }

    /**
     * Gets the x rotation of this planet view at the orbit level.
     *
     * @return The x rotation.
     */
    public final double getRx() {
        return orbitXform.getRx();
    }

    /**
     * Gets the y rotation of this planet view at the orbit level.
     *
     * @return The y rotation.
     */
    public final double getRy() {
        return orbitXform.getRy();
    }

    /**
     * Gets the z rotation of this planet view at the orbit level.
     *
     * @return The z rotation.
     */
    public final double getRz() {
        return orbitXform.getRz();
    }

    /**
     * Positions this planet view at full orbit distance.
     */
    public final void expand() {
        final Timeline expand = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(axisXform.xProperty(),
                                planet.getOrbitDistance()),
                        new KeyValue(axisXform.sxProperty(), 1),
                        new KeyValue(axisXform.syProperty(), 1),
                        new KeyValue(axisXform.szProperty(), 1)
                )
        );
        expand.play();
    }

    /**
     * Brings this planet view in from orbit distance.
     */
    public final void collapse() {
        final Timeline collapse = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(axisXform.xProperty(), 0),
                        new KeyValue(axisXform.sxProperty(), 0),
                        new KeyValue(axisXform.syProperty(), 0),
                        new KeyValue(axisXform.szProperty(), 0)
                )
        );
        collapse.play();
    }

    /**
     * Initializes all texture generation services. On completion, each service
     * is freed of its executor. Texture services apply their textures to the
     * planet view upon completion. Of the two texture services, whichever
     * finishes last will clear the noise generator's noise buffer.
     *
     * @param material The material to be modified by texture services.
     */
    private void initTextures(final PhongMaterial material) {
        generateNoise.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(final WorkerStateEvent event) {
                generateDiffuse.setExecutor(null);
            }
        });

        generateDiffuse.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(final WorkerStateEvent event) {
                material.setDiffuseMap((Image) event.getSource().getValue());
                generateDiffuse.setExecutor(null);
                if (!generateNormal.isRunning()) {
                    noise.clearBuffer();
                }
            }
        });

        generateNormal.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(final WorkerStateEvent event) {
                material.setBumpMap((Image) event.getSource().getValue());
                generateNormal.setExecutor(null);
                if (!generateDiffuse.isRunning()) {
                    noise.clearBuffer();
                }
            }
        });
    }

    /**
     * Updates this planet view's textures given a desired resolution. An
     * executor may be provided to execute noise and texture generation
     * services. If an executor is not provided, and executor will be created
     * and shutdown once all services have been started. Because noise must be
     * generated before diffuse and normal textures, it is important that a
     * single thread executor is used.
     *
     * @param width The desired width for texture generation.
     * @param height The desired height for texture generation.
     * @param es The executor to use for texture generation. null if an executor
     * should be created.
     */
    public final void updateTextures(final int width, final int height,
            final ExecutorService es) {
        generateNoise.cancel();
        generateDiffuse.cancel();
        generateNormal.cancel();

        ExecutorService execute;
        if (es == null) {
            execute = Executors.newSingleThreadExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(final Runnable runnable) {
                    Thread thread = Executors.defaultThreadFactory()
                            .newThread(runnable);
                    thread.setDaemon(true);
                    return thread;
                }
            });
        } else {
            execute = es;
        }

        generateNoise.setExecutor(execute);
        generateDiffuse.setExecutor(execute);
        generateNormal.setExecutor(execute);

        generateNoise.width = width;
        generateNoise.height = height;
        generateNormal.intensity = NORMAL_INTENSITY;

        generateNoise.restart();
        generateDiffuse.restart();
        generateNormal.restart();

        if (es == null) {
            execute.shutdown();
        }
    }

    /**
     * Increments the orbit of this planet view.
     */
    public final void incrementOrbit() {
        orbitXform.setRz((orbitXform.getRz() + planet.getOrbitSpeed()) % 360);
        setRotate(getRotate() + planet.getAxialSpeed());
    }

    /**
     * A service that initializes and fills the noise generator's noise buffer.
     */
    private class GenerateNoiseService extends Service<Void> {

        /**
         * The desired width of the noise buffer.
         */
        private int width;
        /**
         * The desired height of the noise buffer.
         */
        private int height;

        @Override
        protected Task<Void> createTask() {
            return new GenerateNoiseTask(width, height);
        }

        @Override
        protected void cancelled() {
            Thread.currentThread().interrupt();
        }

        /**
         * A task created by a generate noise service to generate noise.
         */
        private class GenerateNoiseTask extends Task<Void> {

            /**
             * The desired width of the noise buffer.
             */
            private final int width;
            /**
             * The desired height of the noise buffer.
             */
            private final int height;

            /**
             * Constructs a generate noise task with the desired width and
             * height.
             *
             * @param aWidth The desired width.
             * @param aHeight The desired height.
             */
            public GenerateNoiseTask(final int aWidth, final int aHeight) {
                width = aWidth;
                height = aHeight;
            }

            @Override
            protected Void call() throws Exception {
                noise.initNoiseBuffer(width, height);
                noise.addOctaves();
                return null;
            }
        }
    }

    /**
     * A service that generates a diffuse map using the noise generator.
     */
    private class GenerateDiffuseService extends Service<Image> {

        @Override
        protected Task<Image> createTask() {
            return new GenerateDiffuseTask();
        }

        /**
         * A task created by a generate diffuse service to generate a diffuse
         * map.
         */
        private class GenerateDiffuseTask extends Task<Image> {

            @Override
            protected Image call() throws Exception {
                return noise.getDiffuse();
            }
        }

        @Override
        protected void cancelled() {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * A service that generates a normal map using the noise generator.
     */
    private class GenerateNormalService extends Service<Image> {

        /**
         * The intensity of the normal map.
         */
        private double intensity;

        @Override
        protected Task<Image> createTask() {
            return new GenerateNormalTask(intensity);
        }

        @Override
        protected void cancelled() {
            Thread.currentThread().interrupt();
        }

        /**
         * A task created by a generate normal service to generate a normal map.
         */
        private class GenerateNormalTask extends Task<Image> {

            /**
             * The intensity of the normal map.
             */
            private final double intensity;

            /**
             * Constructs a generate normal task with the desired intensity.
             *
             * @param i The desired intensity.
             */
            public GenerateNormalTask(final double i) {
                intensity = i;
            }

            @Override
            protected Image call() throws Exception {
                return noise.getNormal(intensity);
            }
        }
    }
}
