/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import spacetrader.Xform.RotateOrder;

/**
 *
 * @author Administrator
 */
public class EncounterSubScene {
    public static final AmbientLight NO_SHADE = new AmbientLight();
    public static final AmbientLight AMBIENT = new AmbientLight(Color.rgb(20, 20, 20));
    public static final PointLight SUN = new PointLight(); 
    {
        SUN.setTranslateX(-50);
        SUN.setTranslateY(-50);
        SUN.setTranslateZ(-200);
    }
    
    private final SubScene subScene;
    private final ShipView playerShip;
    private final ControlledShipView enemyShip;
    private final PerspectiveCamera camera;
    private final Xform cameraXform;
    
    public EncounterSubScene() {
        Group root = new Group();
        subScene = new SubScene(root, SpaceTrader.SCREEN_WIDTH,
                SpaceTrader.SCREEN_HEIGHT, true,
                SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);
        
        
        ObjModelImporter objImp = new ObjModelImporter();
        final URL modelUrl = getClass().getResource("ship2.obj");
        objImp.read(modelUrl);
        MeshView[] shipModel = objImp.getImport();
        playerShip = new ShipView(shipModel[0].getMesh(), GameModel.getPlayer().getShip());
        enemyShip = new ControlledShipView(shipModel[0].getMesh(), GameModel.getPlayer().getShip(), ControlType.PIRATE);
        enemyShip.setTarget(playerShip);
        Xform enemyXform = enemyShip.getMainXform();
        Xform shipXform = playerShip.getMainXform();
        SUN.getScope().addAll(playerShip, enemyShip);
        
        Box test = new Box(500,500,1);
        test.setTranslateZ(50);
        PhongMaterial testMaterial = new PhongMaterial(Color.BLUE);
        testMaterial.setDiffuseMap(new Image(getClass().getResource("test.png").toExternalForm()));
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
        
        root.getChildren().addAll(AMBIENT, SUN, cameraXform, shipXform, enemyXform, test);
    }
    
    public SubScene getSubScene() {
        return subScene;
    }
    
    public ShipView getPlayerShip() {
        return playerShip;
    }
    
    public ControlledShipView getEnemyShip() {
        return enemyShip;
    }
}