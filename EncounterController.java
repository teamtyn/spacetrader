/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
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
 * @author Administrator
 */
public class EncounterController extends AnimationTimer implements Initializable, ControlledScreen {
    private ScreensController parentController;
    private EncounterSubScene encounterSubScene;
    
    private SubScene subScene;
    private ShipView playerShip;
    private ControlledShipView enemyShip;
    private PerspectiveCamera camera;
    
    @FXML private Pane subScenePane;
    
    private boolean wDown;
    private boolean aDown;
    private boolean sDown;
    private boolean dDown;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //TODO
    }    

    @Override
    public void handle(long now) {
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
    public void setScreenParent(ScreensController parentController) {
        this.parentController = parentController;
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
    
    public void handleKeyboard() {
        subScene.setFocusTraversable(true);
        subScene.requestFocus();
        subScene.setOnKeyPressed(event -> {
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
        });
        
        subScene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
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
        });
    }
}
