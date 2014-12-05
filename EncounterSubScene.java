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
import spacetrader.items.Ship;
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
        SUN.setTranslateX(-500000);
        SUN.setTranslateY(-100000);
        SUN.setTranslateZ(-1000000);
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
        
        playerShip = new ShipView(loader.getMesh(),
                GameModel.getPlayer().getShip());
        root.getChildren().add(playerShip.getMainXform());
        
        terrain = new TerrainView(GameModel.getPlayer().getPlanet(), 256);
        terrain.setTranslateZ(250);
        otherShips = new ArrayList<>();
        debris = new ArrayList<>();
        
        for(int i = 0; i < enemyCount; i++) {
            ControlledShipView other = new ControlledShipView(loader.getMesh(),
                Ship.randomShip(1), ControlType.PIRATE);
            other.setTarget(playerShip);
            otherShips.add(other);
            root.getChildren().add(other.getMainXform());
        }

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
