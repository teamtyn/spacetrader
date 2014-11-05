package spacetrader;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
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
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.util.Duration;
import spacetrader.Xform.RotateOrder;
import spacetrader.star_system.PlanetView;
import spacetrader.star_system.StarSystem;
import spacetrader.star_system.StarSystemView;

/**
 *
 * @author Administrator
 */
public class UniverseMapSubScene {
    public static final AmbientLight NO_SHADE = new AmbientLight();
    public static final AmbientLight AMBIENT = new AmbientLight(Color.rgb(20, 20, 20));
    
    private final SubScene subScene;
    private final ArrayList<StarSystemView> systemViews;
    private final PerspectiveCamera camera;
    private final Xform topXform;
    private final Xform baseXform;
    private Timeline toUniverse;
    private Timeline toSystem;
    private Timeline toPlanet;
    private final MeshView skybox;
    private final Sphere highlight;
    private final Box updater;

    public UniverseMapSubScene() {
        Group root = new Group();
        subScene = new SubScene(root, SpaceTrader.SCREEN_WIDTH,
                SpaceTrader.SCREEN_HEIGHT, true,
                SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);
        
        systemViews = new ArrayList<>();
        buildSystems(root);
        
        camera = new PerspectiveCamera(true);
        camera.setFieldOfView(45);
        camera.setNearClip(1);
        camera.setFarClip(20000);
        subScene.setCamera(camera);
        
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
        
        updater = new Box(1,1,1);
        updater.setTranslateX(200000);
        Timeline update = new Timeline(
            new KeyFrame(Duration.seconds(0),
                new KeyValue(updater.rotateProperty(), 0)),
            new KeyFrame(Duration.seconds(1),
                new KeyValue(updater.rotateProperty(),360)));
        update.setCycleCount(Timeline.INDEFINITE);
        update.play();

        root.getChildren().addAll(NO_SHADE, AMBIENT, highlight, updater);
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.9);
        subScene.setEffect(bloom);
    }
    
    public SubScene getSubScene() {
        return subScene;
    }
    
    public ArrayList<StarSystemView> getSystemViews() {
        return systemViews;
    }
    
    public Xform getTopXform() {
        return topXform;
    }
    
    public Xform getBaseXform() {
        return baseXform;
    }
    
    public PerspectiveCamera getCamera() {
        return camera;
    }
    
    public MeshView getSkybox() {
        return skybox;
    }
    
    public Sphere getHighlight() {
        return highlight;
    }
    
    private void buildSystems(Group root) {        
        StarSystem[] systems = GameModel.getSystems();
        for (StarSystem system : systems) {
            StarSystemView systemView = new StarSystemView(system);
            root.getChildren().add(systemView.getSystemXform());
            systemViews.add(systemView);
        }
    }

    private void buildCamera(Group root) {
        topXform.setTranslate(GameModel.UNIVERSE_WIDTH / 2, GameModel.UNIVERSE_HEIGHT / 2);
        camera.setTranslateZ(-2000);
        
        baseXform.getChildren().add(camera);
        topXform.getChildren().add(baseXform);
        root.getChildren().add(topXform);
    }
    
    public void cameraToUniverse() {
        if (toUniverse != null) {   
            toUniverse.stop();
        }
        if (toSystem != null) {   
            toSystem.stop();
        }
        if (toPlanet != null) {
            toPlanet.stop();
        }
        
        topXform.rz.angleProperty().unbind();
        toUniverse = new Timeline(
            new KeyFrame(Duration.seconds(2),
                new KeyValue(baseXform.t.xProperty(), 0),
                new KeyValue(baseXform.t.yProperty(), 0),
                new KeyValue(baseXform.t.zProperty(), 0),
                
                new KeyValue(baseXform.rx.angleProperty(), 0),
                new KeyValue(baseXform.ry.angleProperty(), 0),
                new KeyValue(baseXform.rz.angleProperty(), 0),
                    
                new KeyValue(camera.translateZProperty(), -2000),
                
                new KeyValue(topXform.rx.angleProperty(), 0),
                new KeyValue(topXform.ry.angleProperty(), 0),
                new KeyValue(topXform.rz.angleProperty(), 0)
            )
        );
        toUniverse.play();
    }
    
    public void cameraToSystem(StarSystemView system) {
        if (toUniverse != null) {   
            toUniverse.stop();
        }
        if (toSystem != null) {   
            toSystem.stop();
        }
        if (toPlanet != null) {
            toPlanet.stop();
        }
        
        topXform.rz.angleProperty().unbind();
        toSystem = new Timeline(
            new KeyFrame(Duration.seconds(2),
                new KeyValue(baseXform.t.xProperty(), 0),
                new KeyValue(baseXform.t.yProperty(), 0),
                new KeyValue(baseXform.t.zProperty(), 0),
                
                new KeyValue(baseXform.rx.angleProperty(), 0),
                new KeyValue(baseXform.ry.angleProperty(), 0),
                new KeyValue(baseXform.rz.angleProperty(), 0),
                    
                new KeyValue(topXform.t.xProperty(), system.getX()),
                new KeyValue(topXform.t.yProperty(), system.getY()),
                new KeyValue(topXform.t.zProperty(), system.getZ()),
                new KeyValue(camera.translateZProperty(), -200),
                
                new KeyValue(topXform.rx.angleProperty(), system.getRx()),
                new KeyValue(topXform.ry.angleProperty(), system.getRy()),
                new KeyValue(topXform.rz.angleProperty(), system.getRz())
            )
        );
        toSystem.play();
    }
    
    public void cameraToPlanet(StarSystemView system, PlanetView planet) {
        if (toUniverse != null) {   
            toUniverse.stop();
        }
        if (toSystem != null) {   
            toSystem.stop();
        }
        if (toPlanet != null) {
            toPlanet.stop();
        }
        
        topXform.rz.angleProperty().unbind();
        DoubleProperty angleOffset = new SimpleDoubleProperty(topXform.rz.getAngle() - planet.getRz());
        topXform.rz.angleProperty().bind(planet.getOrbitXform().rz.angleProperty().add(angleOffset));
        toPlanet = new Timeline(
            new KeyFrame(Duration.seconds(2),
                new KeyValue(topXform.rx.angleProperty(), system.getRx()),
                new KeyValue(topXform.ry.angleProperty(), system.getRy()),
                
                new KeyValue(angleOffset, 0),
                new KeyValue(baseXform.t.xProperty(), planet.getAxisXform().t.getX()),
                new KeyValue(baseXform.t.yProperty(), planet.getAxisXform().t.getY()),
                new KeyValue(baseXform.t.zProperty(), planet.getAxisXform().t.getZ()),
                new KeyValue(camera.translateZProperty(), -13),
                    
                new KeyValue(baseXform.rx.angleProperty(), 90),
                new KeyValue(baseXform.ry.angleProperty(), 0),
                new KeyValue(baseXform.rz.angleProperty(), 0)
            )
        );
        toPlanet.play();
    }
    
    private void buildSkybox(Group root) {
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
        skybox.setScaleX(5000);
        skybox.setScaleY(5000);
        skybox.setScaleZ(5000);
        
        PhongMaterial skyboxMaterial = new PhongMaterial();
        skyboxMaterial.setDiffuseMap(new Image(getClass().getResource("skybox1.png").toExternalForm()));
        skybox.setMaterial(skyboxMaterial);
        NO_SHADE.getScope().add(skybox);
        
        root.getChildren().add(skybox);
    }
}