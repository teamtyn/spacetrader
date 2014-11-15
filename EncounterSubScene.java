package spacetrader;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import java.net.URL;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import spacetrader.ControlledShipView.ControlType;

/**
 *
 * @author Team TYN
 */
public class EncounterSubScene {
    /**
     * 
     */
    public static final AmbientLight NO_SHADE = new AmbientLight();
    /**
     * 
     */
    public static final AmbientLight AMBIENT = new AmbientLight(
            Color.rgb(20, 20, 20));
    public static final PointLight SUN = new PointLight();
    {
        SUN.setTranslateX(-50);
        SUN.setTranslateY(-50);
        SUN.setTranslateZ(-200);
    }

    /**
     * 
     */
    private final SubScene subScene;
    /**
     * 
     */
    private final ShipView playerShip;
    /**
     * 
     */
    private final ControlledShipView enemyShip;
    /**
     * 
     */
    private final PerspectiveCamera camera;
    /**
     * 
     */
    private final Xform cameraXform;

    /**
     * 
     */
    public EncounterSubScene() {
        final Group root = new Group();
        subScene = new SubScene(root, SpaceTrader.SCREEN_WIDTH,
                SpaceTrader.SCREEN_HEIGHT, true,
                SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);

        final ObjModelImporter objImp = new ObjModelImporter();
        ModelLoader loader = new ModelLoader("ship2.obj");
        loader.load(false);
        playerShip = new ShipView(loader.getMesh(),
                GameModel.getPlayer().getShip());
        enemyShip = new ControlledShipView(loader.getMesh(),
                GameModel.getPlayer().getShip(), ControlType.PIRATE);
        enemyShip.setTarget(playerShip);
        final Xform enemyXform = enemyShip.getMainXform();
        final Xform shipXform = playerShip.getMainXform();
        SUN.getScope().addAll(playerShip, enemyShip);

        final Box test = new Box(500, 500, 1);
        test.setTranslateZ(50);
        final PhongMaterial testMaterial = new PhongMaterial(Color.BLUE);
        testMaterial.setDiffuseMap(
                new Image(getClass().getResource("test.png").toExternalForm()));
        test.setMaterial(testMaterial);
        SUN.getScope().add(test);

        camera = new PerspectiveCamera(true);
        camera.setFieldOfView(45);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        subScene.setCamera(camera);

        cameraXform = new Xform();
        cameraXform.translateXProperty().bind(playerShip.xProperty());
        cameraXform.translateYProperty().bind(playerShip.yProperty());
        cameraXform.rzProperty().bind(playerShip.rzProperty().add(90));
        cameraXform.setRx(70);
        camera.setTranslateZ(-50);
        cameraXform.getChildren().add(camera);

        root.getChildren().addAll(AMBIENT, SUN,
                cameraXform, shipXform, enemyXform, test);
    }

    /**
     * 
     * @return 
     */
    public SubScene getSubScene() {
        return subScene;
    }

    /**
     * 
     * @return 
     */
    public ShipView getPlayerShip() {
        return playerShip;
    }

    /**
     * 
     * @return 
     */
    public ControlledShipView getEnemyShip() {
        return enemyShip;
    }
}
