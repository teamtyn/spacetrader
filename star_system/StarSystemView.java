package spacetrader.star_system;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import spacetrader.GameModel;
import spacetrader.UniverseMapSubScene;
import spacetrader.Xform;
import spacetrader.Xform.RotateOrder;

/**
 *
 * @author Team TYN
 */
public final class StarSystemView extends Sphere {

    /**
     * The star system represented by this system view.
     */
    private final StarSystem system;
    /**
     * The planet views within the planet level Xform of this system view.
     */
    private final ArrayList<PlanetView> planetViews;
    /**
     * The system level Xform.
     */
    private final Xform systemXform;
    /**
     * The planet level Xform.
     */
    private final Xform planetsXform;
    /**
     * The light emitted by the represented system's star.
     */
    private final PointLight light;
    /**
     * The star material.
     */
    private final PhongMaterial material;

    /**
     * Constructs a star system view object given a star system.
     * @param aSystem aSystem The system to be represented by this star system view
     */
    public StarSystemView(final StarSystem aSystem) {
        super(aSystem.getSize());
        system = aSystem;
        planetViews = new ArrayList<PlanetView>(system.getPlanets().length);
        systemXform = new Xform(RotateOrder.ZYX);
        planetsXform = new Xform();
        light = new PointLight();
        light.setLightOn(false);
        material = new PhongMaterial();
        setMaterial(new PhongMaterial(Color.rgb(240, 255, 100)));
        systemXform.setTranslate(system.getCoordinateX(),
                system.getCoordinateY());
        systemXform.setRotate(180 * GameModel.getRandom().nextDouble(),
                180 * GameModel.getRandom().nextDouble(), 0);
        for (Planet planet : system.getPlanets()) {
            PlanetView planetView = new PlanetView(planet);
            planetsXform.getChildren().add(planetView.getOrbitXform());
            planetViews.add(planetView);
        }
        systemXform.getChildren().addAll(this, planetsXform, light);
        light.getScope().add(planetsXform);
        UniverseMapSubScene.AMBIENT.getScope().add(planetsXform);
        UniverseMapSubScene.NO_SHADE.getScope().add(this);
        light.getScope().add(planetsXform);
        UniverseMapSubScene.AMBIENT.getScope().add(planetsXform);
        UniverseMapSubScene.NO_SHADE.getScope().add(this);
    }

    /**
     * Gets the star system associated with this star system view.
     *
     * @return The star system associated with this star system view
     */
    public StarSystem getSystem() {
        return system;
    }

    /**
     * Gets the planet views associated with this star system view.
     *
     * @return The planet views associated with this star system view
     */
    public ArrayList<PlanetView> getPlanetViews() {
        return planetViews;
    }

    /**
     * Tests whether this planet is already contained.
     *
     * @param planet To be tested for containment
     * @return Whether the planet views contains this planet
     */
    public boolean containsPlanet(final PlanetView planet) {
        return planetViews.contains(planet);
    }

    /**
     * Gets the system x form associated with this star system view.
     *
     * @return The system x form associated with this star system view
     */
    public Xform getSystemXform() {
        return systemXform;
    }

    /**
     * Gets the planets x form associated with this star system view.
     *
     * @return The planets x form associated with this star system view
     */
    public Xform getPlanetsXform() {
        return planetsXform;
    }

    /**
     * Gets the x associated with this star system view.
     *
     * @return The x associated with this star system view
     */
    public double getX() {
        return systemXform.getTx();
    }

    /**
     * Gets the y associated with this star system view.
     *
     * @return The y associated with this star system view
     */
    public double getY() {
        return systemXform.getTy();
    }

    /**
     * Gets the z associated with this star system view.
     *
     * @return The z associated with this star system view
     */
    public double getZ() {
        return systemXform.getTz();
    }

    /**
     * Gets the rx associated with this star system view.
     *
     * @return The rx associated with this star system view
     */
    public double getRx() {
        return systemXform.getRx();
    }

    /**
     * Gets the ry associated with this star system view.
     *
     * @return The ry associated with this star system view
     */
    public double getRy() {
        return systemXform.getRy();
    }

    /**
     * Gets the rz associated with this star system view.
     *
     * @return The rz associated with this star system view
     */
    public double getRz() {
        return systemXform.getRz();
    }

    /**
     * Delegates to the setLightOn() method of light.
     *
     * @param on Whether to turn the light on or not
     */
    public void setLightOn(final boolean on) {
        light.setLightOn(on);
    }

    /**
     * Expands all of the planet views.
     */
    public void expand() {
        for (PlanetView planet : planetViews) {
            planet.expand();
        }
    }

    /**
     * Collapses all of the planet views.
     */
    public void collapse() {
        for (PlanetView planet : planetViews) {
            planet.collapse();
        }
    }

    /**
     * Updates all planet textures.
     * @param width The desired width.
     * @param height The desired height.
     */
    public void updateTextures(final int width, final int height) {
        ExecutorService es = Executors.newSingleThreadExecutor(
                new ThreadFactory() {

                    @Override
                    public Thread newThread(final Runnable runnable) {
                        Thread thread
                        = Executors.defaultThreadFactory().newThread(runnable);
                        thread.setDaemon(true);
                        return thread;
                    }
                });
        for (PlanetView planet : planetViews) {
            planet.updateTextures(width, height, es);
        }
        es.shutdown();
    }

    /**
     * Increments orbits.
     */
    public void incrementOrbits() {
        for (PlanetView planet : planetViews) {
            planet.incrementOrbit();
        }
    }
}
