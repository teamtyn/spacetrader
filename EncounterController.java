package spacetrader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Team TYN
 */
public class EncounterController extends AnimationTimer implements Initializable, ControlledScreen {
    /**
     * 
     */
    private ScreensController parentController;
    /**
     * 
     */
    private EncounterSubScene encounterSubScene;
    /**
     * 
     */
    private SubScene subScene;
    /**
     * 
     */
    private ShipView playerShip;
    /**
     * 
     */
    private ControlledShipView enemyShip;
    /**
     * 
     */
    private PerspectiveCamera camera;

    @FXML
    private Pane subScenePane;

    /**
     * 
     */
    private boolean wDown;
    /**
     * 
     */
    private boolean aDown;
    /**
     * 
     */
    private boolean sDown;
    /**
     * 
     */
    private boolean dDown;

    @Override
    public void initialize(final URL url, final ResourceBundle rBundle) {
        //TODO
    }

    @Override
    public void handle(final long now) {
        if (wDown) {
            sDown = false;
            playerShip.applyForce(1);
        }
        if (sDown) {
            wDown = false;
            playerShip.applyForce(-1);
        }
        if (aDown) {
            dDown = false;
            playerShip.applyTorque(-1);
        }
        if (dDown) {
            aDown = false;
            playerShip.applyTorque(1);
        }

        enemyShip.getController().control();
        enemyShip.update();
        playerShip.update();
    }

    @Override
    public void setScreenParent(final ScreensController aParentController) {
        parentController = aParentController;
    }

    @Override
    public void lazyInitialize() {
        encounterSubScene = new EncounterSubScene();
        subScene = encounterSubScene.getSubScene();
        playerShip = encounterSubScene.getPlayerShip();
        enemyShip = encounterSubScene.getEnemyShip();
        subScenePane.getChildren().add(subScene);
        handleKeyboard();
        start();
    }

    /**
     * Method to handle keyboard input.
     */
    public void handleKeyboard() {
        subScene.setFocusTraversable(true);
        subScene.requestFocus();
        subScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case W:
                        wDown = true;
                        break;
                    case A:
                        aDown = true;
                        break;
                    case S:
                        sDown = true;
                        break;
                    case D:
                        dDown = true;
                        break;
                }
            }
        });

        subScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(final KeyEvent event) {
                final KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case W:
                        wDown = false;
                        break;
                    case A:
                        aDown = false;
                        break;
                    case S:
                        sDown = false;
                        break;
                    case D:
                        dDown = false;
                        break;
                }
            }
        });
    }
}
