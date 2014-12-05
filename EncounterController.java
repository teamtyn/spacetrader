package spacetrader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;

/**
 * FXML Controller class
 *
 * @author Team TYN
 */
public class EncounterController extends AnimationTimer implements Initializable, ControlledScreen {
    private ScreensController parentController;
    private EncounterSubScene subScene;
    private ShipView playerShip;
    private ArrayList<ControlledShipView> otherShips;
    private TerrainView terrain;
    private ArrayList<Debris> debris;
    private PerspectiveCamera camera;

    @FXML
    private Pane subScenePane;

    private boolean wDown;
    private boolean aDown;
    private boolean sDown;
    private boolean dDown;
    private boolean spaceDown;

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
        if (spaceDown) {
            playerShip.fire();
        }

        handleBullets(playerShip);
        Iterator<Debris> iterator = debris.iterator();
        while(iterator.hasNext()) {
            Debris d = iterator.next();
            d.update();
            if (d.getIsDone()) {
                subScene.getDebrisXform().getChildren().remove(d);
                iterator.remove();
            }
        }
        
        for (ControlledShipView other : otherShips) {
            other.getController().control();
            handleBullets(other);
            other.update();
        }
        playerShip.update();
        terrain.setFocus(new Point2D(Math.floor(playerShip.getX() / 256), Math.floor(playerShip.getY() / 256)));
    }

    @Override
    public void setScreenParent(final ScreensController aParentController) {
        parentController = aParentController;
    }

    @Override
    public void lazyInitialize() {
        subScene = new EncounterSubScene(new Group(), SpaceTrader.SCREEN_WIDTH, SpaceTrader.SCREEN_HEIGHT, 1);
        playerShip = subScene.getPlayerShip();
        otherShips = subScene.getOtherShips();
        terrain = subScene.getTerrain();
        debris = subScene.getDebris();
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
        subScene.setOnKeyPressed((KeyEvent event) -> {
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
                case SPACE:
                    spaceDown = true;
                    break;
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
                    case SPACE:
                        spaceDown = false;
                        break;
                }
            }
        });
    }
    
    public void handleBullets(ShipView ship) {
        Iterator<MeshView> iterator = ship.getBullets().keySet().iterator();
        while (iterator.hasNext()) {
            MeshView bullet = iterator.next();
            if (!ship.getBullets().get(bullet) || bullet.getTranslateX() > 5000) {
                ship.getBulletXform().getChildren().remove(bullet.getParent());
                iterator.remove();
            } else {
                bullet.setTranslateX(bullet.getTranslateX() + 50);
                double bx1 = bullet.getLocalToSceneTransform().getTx();
                double by1 = bullet.getLocalToSceneTransform().getTy();
                double bx2 = bx1 + 50 * Math.cos(
                        Math.toRadians(((Xform)bullet.getParent()).getRz()));
                double by2 = by1 + 50 * Math.sin(
                        Math.toRadians(((Xform)bullet.getParent()).getRz()));

                double t = 2.0;
                ShipView hit = null;
                if (!ship.equals(playerShip)) {
                    double dist = checkHit(bx1, by1, bx2, by2, 
                            playerShip.getLocalToSceneTransform().getTx(),
                            playerShip.getLocalToSceneTransform().getTy(), 5);
                    if (dist >= 0 && dist < t) {
                        t = dist;
                        hit = playerShip;
                    }
                }

                for (ShipView other : otherShips) {
                    if (!ship.equals(other)) {
                        double dist = checkHit(bx1, by1, bx2, by2, 
                                other.getLocalToSceneTransform().getTx(),
                                other.getLocalToSceneTransform().getTy(), 5);
                        if (dist >= 0 && dist < t) {
                            t = dist;
                            hit = other;
                        }
                    }
                }

                if (t <= 1) {
                    ((TriangleMesh) bullet.getMesh()).getPoints().set(3, (float) t * 50);
                    ship.getBullets().put(bullet, false);
                    Sphere testHit = new Sphere(2);
                    double x = t * (bx2 - bx1) + bx1;
                    double y = t * (by2 - by1) + by1;

                    double hitVecX = (x - hit.getX()) / 5;
                    double hitVecY = (y - hit.getY()) / 5;
                    double bVecX = (bx1 - bx2) / 50;
                    double bVecY = (by1 - by2) / 50;
                    double hitAngle = Math.toDegrees(Math.atan2(hitVecY, hitVecX));
                    double sign = Math.signum(hitVecY * bVecX - hitVecX * bVecY);
                    
                    double bounceAngle = sign * Math.toDegrees(Math.acos(dot(hitVecX, hitVecY, bVecX, bVecY)));
                    
                    hit.hitShield();
                    Debris d = new Debris(x, y, hit.getDx(), hit.getDy(), hitAngle + bounceAngle, Color.WHITE);
                    debris.add(d);
                    subScene.getDebrisXform().getChildren().add(d);
                }
            }
        }
    }
    
    private double checkHit(double x1, double y1, double x2, double y2, double shipX, double shipY, double shipSize) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double fx = x1 - shipX;
        double fy = y1 - shipY;
        
        double a = dot(dx, dy, dx, dy);
        double b = 2 * dot(fx, fy, dx, dy);
        double c = dot(fx, fy, fx, fy) - shipSize * shipSize;
        
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return -1.0;
        } else {
            discriminant = Math.sqrt(discriminant);
            double t1 = (-b - discriminant) / (2 * a);
            double t2 = (-b + discriminant) / (2 * a);
            
            if (t1 >= 0 && t1 <= 1) {
                return t1;
            } else if (t2 >= 0 && t2 <= 1) {
                return t2;
            } else {
                return -1;
            }
        }
    }
    
    private static double mag(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
    
    
    private static double dot(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }
}
