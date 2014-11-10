package spacetrader.star_system;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import spacetrader.star_system.NoiseGenerator.NoiseMode;

/**
 *
 * @author Administrator
 */
public class PlanetView extends Sphere {

    private final Planet planet;
    private final Xform orbitXform;
    private final Xform axisXform;
    private final NoiseGenerator noise;
    private final GenerateNoiseService generateNoise;
    private final GenerateDiffuseService generateDiffuse;
    private final GenerateNormalService generateNormal;

    public PlanetView(Planet planet) {
        super(planet.getSize());

        Random r = new Random();

        this.planet = planet;
        orbitXform = new Xform();
        axisXform = new Xform();

        PhongMaterial material = new PhongMaterial();
        setMaterial(material);

        ColorGradient colors = new ColorGradient(planet.getSeaLevel(), planet.getEnvironment());
        noise = new NoiseGenerator(planet.getSeed(), 0.5, 1, 2, 0.5, 15, NoiseMode.SQUARE, colors);
        noise.initNoiseBuffer(100, 50);
        noise.addOctaves();
        material.setDiffuseMap(noise.getDiffuse());
        noise.clearBuffer();

        generateNoise = new GenerateNoiseService();
        generateDiffuse = new GenerateDiffuseService();
        generateNormal = new GenerateNormalService();
        initTextures(material);

        setRotationAxis(Rotate.Y_AXIS);
        axisXform.setRotate(90 + planet.getAxialTilt(), 0, 360 * r.nextDouble());
        orbitXform.setRz(360 * r.nextDouble());

        axisXform.getChildren().add(this);
        orbitXform.getChildren().add(axisXform);
    }

    public Planet getPlanet() {
        return planet;
    }

    public Xform getOrbitXform() {
        return orbitXform;
    }

    public Xform getAxisXform() {
        return axisXform;
    }

    public double getOffsetX() {
        return axisXform.getTx();
    }

    public double getOffsetY() {
        return axisXform.getTy();
    }

    public double getOffsetZ() {
        return axisXform.getTz();
    }

    public double getX() {
        return getLocalToSceneTransform().getTx();
    }

    public double getY() {
        return getLocalToSceneTransform().getTy();
    }

    public double getZ() {
        return getLocalToSceneTransform().getTz();
    }

    public double getRx() {
        return orbitXform.getRx();
    }

    public double getRy() {
        return orbitXform.getRy();
    }

    public double getRz() {
        return orbitXform.getRz();
    }

    public void expand() {
        Timeline expand = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(axisXform.xProperty(), planet.getOrbitDistance()),
                        new KeyValue(axisXform.sxProperty(), 1),
                        new KeyValue(axisXform.syProperty(), 1),
                        new KeyValue(axisXform.szProperty(), 1)
                )
        );
        expand.play();
    }

    public void collapse() {
        Timeline collapse = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(axisXform.xProperty(), 0),
                        new KeyValue(axisXform.sxProperty(), 0),
                        new KeyValue(axisXform.syProperty(), 0),
                        new KeyValue(axisXform.szProperty(), 0)
                )
        );
        collapse.play();
    }

    private void initTextures(PhongMaterial material) {
        generateDiffuse.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                material.setDiffuseMap((Image) t.getSource().getValue());
                generateDiffuse.setExecutor(null);
                if (!generateNormal.isRunning()) {
                    noise.clearBuffer();
                }
            }
        });

        generateNormal.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                material.setBumpMap((Image) t.getSource().getValue());
                generateNormal.setExecutor(null);
                if (!generateDiffuse.isRunning()) {
                    noise.clearBuffer();
                }
            }
        });
    }

    public void updateTextures(int width, int height, ExecutorService es) {
        generateNoise.cancel();
        generateDiffuse.cancel();
        generateNormal.cancel();

        ExecutorService execute;
        if (es == null) {
            execute = Executors.newSingleThreadExecutor((Runnable runnable) -> {
                Thread thread = Executors.defaultThreadFactory().newThread(runnable);
                thread.setDaemon(true);
                return thread;
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

    public void incrementOrbit() {
        orbitXform.setRz((orbitXform.getRz() + planet.getOrbitSpeed()) % 360);
        setRotate(getRotate() + planet.getAxialSpeed());
    }

    private class GenerateNoiseService extends Service<Void> {

        int width;
        int height;

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

            public GenerateNoiseTask(int w, int h) {
                width = w;
                height = h;
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

        double intensity;

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
