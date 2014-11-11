package spacetrader;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.util.Duration;
import spacetrader.Xform.RotateOrder;
import spacetrader.star_system.PlanetView;
import spacetrader.star_system.StarSystem;
import spacetrader.star_system.StarSystemView;

/**
 * A universe map subscene renders all three dimensional nodes in the
 * universe map. It also contains several methods for handling camera movement.
 *
 * @author Team TYN
 */
public class UniverseMapSubScene extends SubScene {

    /**
     * An ambient light that provides full illumination. Adding a node to this
     * light's scope will effectively remove shading.
     */
    public static final AmbientLight NO_SHADE = new AmbientLight();
    /**
     * An ambient light used for shaded objects. Nodes that are shaded should
     * be added to this light's scope to eliminate black featureless artifacts.
     */
    public static final AmbientLight AMBIENT =
            new AmbientLight(Color.rgb(20, 20, 20));
    /**
     * The default universal level zoom.
     */
    public static final double UNIVERSE_ZOOM = -2000;
    /**
     * The default system level zoom.
     */
    public static final double SYSTEM_ZOOM = -200;
    /**
     * The default planet level zoom.
     */
    public static final double PLANET_ZOOM = -13;

    /**
     * The field of view for this subscene's camera.
     */
    private static final int FOV = 45;
    /**
     * The far clip plane for this subscene's camera.
     */
    private static final int FAR_CLIP = 20000;
    /**
     * The bloom threshold for this subscene.
     */
    private static final double BLOOM_THRESHOLD = 0.9;
    /**
     * The scale of the skybox.
     */
    private static final double SKYBOX_SCALE = 5000;

    /**
     * The list of system views rendered in this subscene.
     */
    private final ArrayList<StarSystemView> systemViews;
    /**
     * The camera for this subscene.
     */
    private final PerspectiveCamera camera;
    /**
     * The top level camera Xform.
     */
    private final Xform topXform;
    /**
     * The base level camera Xform.
     */
    private final Xform baseXform;
    /**
     * The animation that shifts the camera into universe level scope.
     */
    private Timeline toUniverse;
    /**
     * The animation that shifts the camera into system level scope.
     */
    private Timeline toSystem;
    /**
     * The animation that shifts the camera into planet level scope.
     */
    private Timeline toPlanet;
    /**
     * The skybox for this subscene.
     */
    private final MeshView skybox;
    /**
     * The highlight object for this subscene.
     */
    private final Sphere highlight;
    /**
     * A node that is constantly animated to refresh the subscene.
     */
    private final Box updater;

    /**
     * Constructs a universe map subscene.
     *
     * @param root The root of this subscene.
     * @param width The width of this subscene.
     * @param height The height of this subscene.
     */
    public UniverseMapSubScene(final Group root, final double width,
            final double height) {
        super(root, width, height, true, SceneAntialiasing.BALANCED);
        setFill(Color.BLACK);

        systemViews = new ArrayList<>();
        buildSystems(root);

        camera = new PerspectiveCamera(true);
        camera.setFieldOfView(FOV);
        camera.setNearClip(1);
        camera.setFarClip(FAR_CLIP);
        setCamera(camera);

        topXform = new Xform(RotateOrder.ZYX);
        baseXform = new Xform();
        buildCamera(root);

        skybox = new MeshView();
        buildSkybox(root);

        highlight = new Sphere();
        highlight.setMaterial(new PhongMaterial(Color.WHITE));
        highlight.setCullFace(CullFace.FRONT);
        NO_SHADE.getScope().add(highlight);
        highlight.setVisible(false);

        updater = new Box(1, 1, 1);
        updater.setTranslateX(2 * FAR_CLIP);
        Timeline update = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(updater.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(updater.rotateProperty(), 360)));
        update.setCycleCount(Timeline.INDEFINITE);
        update.play();

        root.getChildren().addAll(NO_SHADE, AMBIENT, highlight, updater);
        Bloom bloom = new Bloom();
        bloom.setThreshold(BLOOM_THRESHOLD);
        setEffect(bloom);
    }

    /**
     * Getter for the system views rendered in this subscene.
     * @return An array list of system views.
     */
    public final ArrayList<StarSystemView> getSystemViews() {
        return systemViews;
    }

    /**
     * Getter for the top level camera Xform.
     * @return The top level camera Xform.
     */
    public final Xform getTopXform() {
        return topXform;
    }

    /**
     * Getter for the base level camera Xform.
     * @return The base level camera Xform.
     */
    public final Xform getBaseXform() {
        return baseXform;
    }

    /**
     * Getter for the skybox.
     * @return The skybox.
     */
    public final MeshView getSkybox() {
        return skybox;
    }

    /**
     * Getter for the highlight.
     * @return The highlight.
     */
    public final Sphere getHighlight() {
        return highlight;
    }

    /**
     * Instantiates the system views rendered by this subscene based on the
     * systems stored in the game model.
     * @param root The root of this subscene.
     */
    private void buildSystems(final Group root) {
        StarSystem[] systems = GameModel.getSystems();
        for (StarSystem system : systems) {
            StarSystemView systemView = new StarSystemView(system);
            root.getChildren().add(systemView.getSystemXform());
            systemViews.add(systemView);
        }
    }

    /**
     * Builds the hierarchy of transformation Xform used for manipulating the
     * camera's movements.
     *
     * @param root The root of this subscene.
     */
    private void buildCamera(final Group root) {
        topXform.setTranslate(
                GameModel.UNIVERSE_WIDTH / 2, GameModel.UNIVERSE_HEIGHT / 2);
        camera.setTranslateZ(UNIVERSE_ZOOM);

        baseXform.getChildren().add(camera);
        topXform.getChildren().add(baseXform);
        root.getChildren().add(topXform);
    }

    /**
     * Animates the camera into universe level scope.
     */
    public final void cameraToUniverse() {
        if (toUniverse != null) {
            toUniverse.stop();
        }
        if (toSystem != null) {
            toSystem.stop();
        }
        if (toPlanet != null) {
            toPlanet.stop();
        }

        topXform.rzProperty().unbind();
        toUniverse = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(baseXform.xProperty(), 0),
                        new KeyValue(baseXform.yProperty(), 0),
                        new KeyValue(baseXform.zProperty(), 0),
                        new KeyValue(baseXform.rxProperty(), 0),
                        new KeyValue(baseXform.ryProperty(), 0),
                        new KeyValue(baseXform.rzProperty(), 0),
                        new KeyValue(
                                camera.translateZProperty(), UNIVERSE_ZOOM),
                        new KeyValue(topXform.rxProperty(), 0),
                        new KeyValue(topXform.ryProperty(), 0),
                        new KeyValue(topXform.rzProperty(), 0)
                )
        );
        toUniverse.play();
    }

    /**
     * Animates the camera into system level scope.
     *
     * @param system The focused system.
     */
    public final void cameraToSystem(final StarSystemView system) {
        if (toUniverse != null) {
            toUniverse.stop();
        }
        if (toSystem != null) {
            toSystem.stop();
        }
        if (toPlanet != null) {
            toPlanet.stop();
        }

        topXform.rzProperty().unbind();
        toSystem = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(baseXform.xProperty(), 0),
                        new KeyValue(baseXform.yProperty(), 0),
                        new KeyValue(baseXform.zProperty(), 0),
                        new KeyValue(baseXform.rxProperty(), 0),
                        new KeyValue(baseXform.ryProperty(), 0),
                        new KeyValue(baseXform.rzProperty(), 0),
                        new KeyValue(topXform.xProperty(), system.getX()),
                        new KeyValue(topXform.yProperty(), system.getY()),
                        new KeyValue(topXform.zProperty(), system.getZ()),
                        new KeyValue(camera.translateZProperty(), SYSTEM_ZOOM),
                        new KeyValue(topXform.rxProperty(), system.getRx()),
                        new KeyValue(topXform.ryProperty(), system.getRy()),
                        new KeyValue(topXform.rzProperty(), system.getRz())
                )
        );
        toSystem.play();
    }

    /**
     * Animates the camera into planet level scope.
     *
     * @param system The focused system.
     * @param planet The focused planet.
     */
    public final void cameraToPlanet(final StarSystemView system,
            final PlanetView planet) {
        if (toUniverse != null) {
            toUniverse.stop();
        }
        if (toSystem != null) {
            toSystem.stop();
        }
        if (toPlanet != null) {
            toPlanet.stop();
        }

        topXform.rzProperty().unbind();
        DoubleProperty angleOffset =
                new SimpleDoubleProperty(topXform.getRz() - planet.getRz());
        topXform.rzProperty().bind(
                planet.getOrbitXform().rzProperty().add(angleOffset));
        toPlanet = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(topXform.rxProperty(), system.getRx()),
                        new KeyValue(topXform.ryProperty(), system.getRy()),
                        new KeyValue(angleOffset, 0),
                        new KeyValue(baseXform.xProperty(),
                                planet.getOffsetX()),
                        new KeyValue(baseXform.yProperty(),
                                planet.getOffsetY()),
                        new KeyValue(baseXform.zProperty(),
                                planet.getOffsetZ()),
                        new KeyValue(camera.translateZProperty(), PLANET_ZOOM),
                        new KeyValue(baseXform.rxProperty(), 90),
                        new KeyValue(baseXform.ryProperty(), 0),
                        new KeyValue(baseXform.rzProperty(), 0)
                )
        );
        toPlanet.play();
    }

    /**
     * Builds the skybox triangle mesh.
     * @param root The root of this subscene.
     */
    private void buildSkybox(final Group root) {
        TriangleMesh skyboxMesh = new TriangleMesh();
        skyboxMesh.getPoints().addAll(
                -1f, -1f, -1f,
                -1f, 1f, -1f,
                1f, -1f, -1f,
                1f, 1f, -0.999999f,
                1f, -1f, 1f,
                0.999999f, 1f, 1.000001f,
                -1f, -1f, 1f,
                -1f, 1f, 1f
        );
        skyboxMesh.getTexCoords().addAll(
                0f, 0f,
                0f, 1.0f,
                0.166687f, 0f,
                0.166687f, 1.0f,
                0.333133f, 0.000200f,
                0.333133f, 1.0f,
                0.5f, 0f,
                0.5f, 1.0f,
                0.66666666667f, 0f,
                0.66666666667f, 1.0f,
                0.83333333333f, 0f,
                0.83333333333f, 1.0f,
                1.0f, 0f,
                1.0f, 1.0f
        );
        skyboxMesh.getFaces().addAll(
                0, 0, 2, 2, 1, 1,
                1, 1, 2, 2, 3, 3,
                2, 2, 4, 4, 3, 3,
                3, 3, 4, 4, 5, 5,
                4, 4, 6, 6, 5, 5,
                5, 5, 6, 6, 7, 7,
                6, 6, 0, 8, 7, 7,
                7, 7, 0, 8, 1, 9,
                6, 8, 4, 10, 0, 9,
                0, 9, 4, 10, 2, 11,
                1, 10, 3, 12, 7, 11,
                7, 11, 3, 12, 5, 13
        );
        skybox.setMesh(skyboxMesh);
        skybox.setScaleX(SKYBOX_SCALE);
        skybox.setScaleY(SKYBOX_SCALE);
        skybox.setScaleZ(SKYBOX_SCALE);

        PhongMaterial skyboxMaterial = new PhongMaterial();
        skyboxMaterial.setDiffuseMap(new Image(getClass()
                .getResource("skybox1.png").toExternalForm()));
        skybox.setMaterial(skyboxMaterial);
        NO_SHADE.getScope().add(skybox);

        root.getChildren().add(skybox);
    }
}
