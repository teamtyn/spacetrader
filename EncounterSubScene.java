package spacetrader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
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
import spacetrader.star_system.Planet;

/**
 *
 * @author Team TYN
 */
public class EncounterSubScene extends SubScene{
    public static final AmbientLight NO_SHADE = new AmbientLight();
    public static final AmbientLight AMBIENT = new AmbientLight(
            Color.rgb(20, 20, 20));
    public static final PointLight SUN = new PointLight();
    static {
        SUN.setTranslateX(-50);
        SUN.setTranslateY(-50);
        SUN.setTranslateZ(-1000);
    }

    private final ShipView playerShip;
    private final ArrayList<ControlledShipView> otherShips;
    private final TerrainView terrain;
    private final ArrayList<Debris> debris;
    private final PerspectiveCamera camera;
    private final Xform cameraXform;
    private final Xform debrisXform;

    public EncounterSubScene(Group root, double width, double height, int enemyCount) {
        super(root, width, height, true, SceneAntialiasing.BALANCED);
        setFill(Color.BLACK);

        ModelLoader loader = new ModelLoader("ship2.obj");
        loader.load(false);
        System.out.println(loader.getMesh().getPoints());
        System.out.println(loader.getMesh().getTexCoords());
        System.out.println(loader.getMesh().getFaces());
        
        playerShip = new ShipView(loader.getMesh(),
                GameModel.getPlayer().getShip());
        root.getChildren().add(playerShip.getMainXform());
        
        terrain = new TerrainView(new Planet(0, 0));
        terrain.setTranslateZ(150);
        otherShips = new ArrayList<>();
        debris = new ArrayList<>();
        
        for(int i = 0; i < enemyCount; i++) {
            ControlledShipView other = new ControlledShipView(loader.getMesh(),
                GameModel.getPlayer().getShip(), ControlType.PIRATE);
            other.setTarget(playerShip);
            otherShips.add(other);
            root.getChildren().add(other.getMainXform());
        }

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
        setCamera(camera);

        cameraXform = new Xform();
        cameraXform.translateXProperty().bind(playerShip.xProperty());
        cameraXform.translateYProperty().bind(playerShip.yProperty());
        cameraXform.rzProperty().bind(playerShip.rzProperty().add(90));
        cameraXform.setRx(70);
        camera.setTranslateZ(-50);
        cameraXform.getChildren().add(camera);
        
        debrisXform = new Xform();
        
        NO_SHADE.getScope().addAll(debrisXform);
        SUN.getScope().add(terrain);

        root.getChildren().addAll(NO_SHADE, AMBIENT, SUN,
                cameraXform, debrisXform, terrain);
    }

    public ShipView getPlayerShip() {
        return playerShip;
    }
    
    public ArrayList<ControlledShipView> getOtherShips() {
        return otherShips;
    }
    
    public TerrainView getTerrain() {
        return terrain;
    }

    public ArrayList<Debris> getDebris() {
        return debris;
    }
    
    public Xform getDebrisXform() {
        return debrisXform;
    }
}
