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
 *
 * @author Team TYN
 */
public class PlanetView extends Sphere {
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
     * 
     */
    private final GenerateNoiseService generateNoise;
    /**
     * 
     */
    private final GenerateDiffuseService generateDiffuse;
    /**
     * 
     */
    private final GenerateNormalService generateNormal;

    /**
     *
     * @param aPlanet
     */
    public PlanetView(final Planet aPlanet) {
        super(aPlanet.getSize());
        planet = aPlanet;
        orbitXform = new Xform();
        axisXform = new Xform();
        final PhongMaterial material = new PhongMaterial();
        setMaterial(material);
        final ColorGradient colors = new ColorGradient(
                planet.getSeaLevel(), planet.getEnvironment());
        noise = new NoiseGenerator(
                planet.getSeed(), 0.5, 1, 2, 0.5, 15, NoiseMode.SQUARE, colors);
        noise.initNoiseBuffer(100, 50);
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
     * 
     * @return 
     */
    public final Planet getPlanet() {
        return planet;
    }

    /**
     * 
     * @return 
     */
    public final Xform getOrbitXform() {
        return orbitXform;
    }

    /**
     *
     * @return
     */
    public final Xform getAxisXform() {
        return axisXform;
    }

    /**
     *
     * @return
     */
    public final double getOffsetX() {
        return axisXform.getTx();
    }

    /**
     *
     * @return
     */
    public final double getOffsetY() {
        return axisXform.getTy();
    }

    /**
     *
     * @return
     */
    public final double getOffsetZ() {
        return axisXform.getTz();
    }

    /**
     *
     * @return
     */
    public final double getX() {
        return getLocalToSceneTransform().getTx();
    }

    /**
     *
     * @return
     */
    public final double getY() {
        return getLocalToSceneTransform().getTy();
    }

    /**
     *
     * @return
     */
    public final double getZ() {
        return getLocalToSceneTransform().getTz();
    }

    /**
     *
     * @return
     */
    public final double getRx() {
        return orbitXform.getRx();
    }

    /**
     *
     * @return
     */
    public final double getRy() {
        return orbitXform.getRy();
    }

    /**
     *
     * @return
     */
    public final double getRz() {
        return orbitXform.getRz();
    }

    /**
     *
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
     *
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

    private void initTextures(final PhongMaterial material) {
        generateDiffuse.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
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
     *
     * @param width
     * @param height
     * @param es
     */
    public void updateTextures(final int width, final int height,
            final ExecutorService es) {
        generateNoise.cancel();
        generateDiffuse.cancel();
        generateNormal.cancel();

        ExecutorService execute;
        if (es == null) {
            execute = Executors.newSingleThreadExecutor(new ThreadFactory() {

                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = Executors.defaultThreadFactory().newThread(runnable);
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
        generateNormal.intensity = 7;

        generateNoise.restart();
        generateDiffuse.restart();
        generateNormal.restart();

        if (es == null) {
            execute.shutdown();
        }
    }

    /**
     *
     */
    public void incrementOrbit() {
        orbitXform.setRz((orbitXform.getRz() + planet.getOrbitSpeed()) % 360);
        setRotate(getRotate() + planet.getAxialSpeed());
    }

    private class GenerateNoiseService extends Service<Void> {
        private int width;
        private int height;

        @Override
        protected Task<Void> createTask() {
            return new GenerateNoiseTask(width, height);
        }

        @Override
        protected void cancelled() {
            Thread.currentThread().interrupt();
        }

        private class GenerateNoiseTask extends Task<Void> {
            private final int width;
            private final int height;

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

    private class GenerateDiffuseService extends Service<Image> {

        @Override
        protected Task<Image> createTask() {
            return new GenerateDiffuseTask();
        }

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

    private class GenerateNormalService extends Service<Image> {
        private double intensity;

        @Override
        protected Task<Image> createTask() {
            return new GenerateNormalTask(intensity);
        }

        @Override
        protected void cancelled() {
            Thread.currentThread().interrupt();
        }

        private class GenerateNormalTask extends Task<Image> {
            private final double intensity;

            public GenerateNormalTask(double i) {
                intensity = i;
            }

            @Override
            protected Image call() throws Exception {
                return noise.getNormal(intensity);
            }
        }
    }
}
