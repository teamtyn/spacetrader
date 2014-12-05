/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import spacetrader.star_system.NoiseGenerator;
import spacetrader.star_system.Planet;
import spacetrader.star_system.Planet.Environment;

/**
 *
 * @author Administrator
 */
public class TerrainView extends Xform {
    /**
     * The base frequency for noise generation.
     */
    private static final double BASE_FREQ = 0.0005;
    /**
     * The base amplitude for noise generation.
     */
    private static final double BASE_AMP = 12;
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
     * The size of each terrain chunk.
     */
    private static final int CHUNK_SIZE = 256;
    /**
     * The number of subdivisions for a chunk.
     */
    private static final int CHUNK_SUBDIVISIONS = 32;

    private NoiseGenerator noise;
    private HashMap<Point2D, Service> loadMap;
    private Point2D focus;
    private ExecutorService execute;
    
    public TerrainView(Planet planet) {
        final TextureGradient textures = TextureGradient.factory(Planet.Environment.EARTH);
        noise = new NoiseGenerator(planet.getSeed(), BASE_FREQ, BASE_AMP,
                LACUNARITY, GAIN, OCTAVE_CAP, NoiseGenerator.NoiseMode.SQUARE, null, textures);
        loadMap = new HashMap<>();
        focus = new Point2D(0, 0);
        execute = Executors.newFixedThreadPool(5, new ThreadFactory() {
                @Override
                public Thread newThread(final Runnable runnable) {
                    Thread thread = Executors.defaultThreadFactory()
                            .newThread(runnable);
                    thread.setDaemon(true);
                    return thread;
                }
        });
        checkLoaded();
    }

    public void setFocus(Point2D f) {
        if (!focus.equals(f)) {
            focus = f;
            checkLoaded();
        }
    }

    private void checkLoaded() {
        for (int j = 4; j >= -4; j--) {
            for (int i = -4; i <= 4; i++) {
                Point2D point = new Point2D(focus.getX() + i, focus.getY() + j);
                if (loadMap.get(point) == null ) {
                    LoadChunkService loadChunk = new LoadChunkService();
                    loadChunk.point = point;
                    loadChunk.size = CHUNK_SIZE;
                    loadChunk.subdivisions = CHUNK_SUBDIVISIONS;
                    loadChunk.setExecutor(execute);
                    initChunkLoader(loadChunk);
                    loadChunk.restart();
                    loadMap.put(point, loadChunk);
                }
            }
        }
    }
    
    private void initChunkLoader(LoadChunkService loadChunk) {
        loadChunk.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(final WorkerStateEvent event) {
                getChildren().add((MeshView) event.getSource().getValue());
            }
        });
    }

    /**
     * A service that loads a terrain chunk.
     */
    private class LoadChunkService extends Service<MeshView> {

        /**
         * The desired coordinate of the bottom left corner of the chunk.
         */
        private Point2D point; 
        /**
         * The desired size of the chunk.
         */
        private int size;
        /**
         * The desired subdivisions of the chunk.
         */
        private int subdivisions;

        @Override
        protected Task<MeshView> createTask() {
            return new LoadChunkTask(point, size, subdivisions);
        }

        @Override
        protected void cancelled() {
            Thread.currentThread().interrupt();
        }

        /**
         * A task created by a generate noise service to generate noise.
         */
        private class LoadChunkTask extends Task<MeshView> {

            /**
             * The desired coordinate of the bottom left corner of the chunk.
             */
            private Point2D point; 
            /**
             * The desired size of the chunk.
             */
            private int size;
            /**
             * The desired subdivisions of the chunk.
             */
            private int subdivisions;

                /**
                 * Constructs a chunk loading task that generates a chunk at the
                 * desired location, at the desired size, with the desired number
                 * of subdivisions.
                 *
                 * @param aPoint The desired location.
                 * @param aSize The desired size.
                 * @param aSubdivisions The desired subdivisions.
                 */
                public LoadChunkTask(final Point2D aPoint, final int aSize, final int aSubdivisions) {
                    point = aPoint;
                    size = aSize;
                    subdivisions = aSubdivisions;
                }

                @Override
                protected MeshView call() throws Exception {
                    return noise.getChunk(point, size, subdivisions);
                }
        }
    }
}
