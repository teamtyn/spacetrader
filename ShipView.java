package spacetrader;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.util.Duration;
import spacetrader.items.Ship;

/**
 * 
 * @author Team TYN
 */
public class ShipView extends MeshView {
    private Ship ship;
    private Xform mainXform;
    private Xform shipXform;
    private Xform bulletXform;
    private MeshView shield;
    private HashMap<MeshView, Boolean> bullets;
    private SequentialTransition shieldHit;
    private double dx;
    private double dy;
    private double dTheta;
    private double power;
    private double maxSpeed;
    private double maxRotationSpeed;
    private double handling;
    private double mass;

    /**
     * 
     * @param model
     * @param ship 
     */
    public ShipView(final Mesh model, final Ship ship) {
        super(model);
        this.ship = ship;
        mainXform = new Xform();
        shipXform = new Xform();
        bulletXform = new Xform();
        shield = new MeshView(model);
        bullets = new HashMap<>();
        
        shield.setCullFace(CullFace.FRONT);
        shield.setScaleX(0.9);
        shield.setScaleY(0.9);
        shield.setScaleZ(0.9);
        
        Timeline shieldUp = new Timeline(
                new KeyFrame(Duration.seconds(0.1),
                        new KeyValue(shield.scaleXProperty(), 1.2),
                        new KeyValue(shield.scaleYProperty(), 1.2),
                        new KeyValue(shield.scaleZProperty(), 1.2)
                )
        );
        Timeline shieldDown = new Timeline(
                new KeyFrame(Duration.seconds(0.1),
                        new KeyValue(shield.scaleXProperty(), 0.9),
                        new KeyValue(shield.scaleYProperty(), 0.9),
                        new KeyValue(shield.scaleZProperty(), 0.9)
                )
        );
        shieldHit = new SequentialTransition(shieldUp, shieldDown);
        
        EncounterSubScene.SUN.getScope().add(shipXform);
        EncounterSubScene.AMBIENT.getScope().add(shipXform);
        EncounterSubScene.NO_SHADE.getScope().addAll(shield, bulletXform);

        mass = 50;
        handling = 0.3;
        maxSpeed = 2;
        maxRotationSpeed = 2;

        shipXform.getChildren().addAll(this, shield);
        mainXform.getChildren().addAll(shipXform, bulletXform);
    }

    public void applyForce(final double force) {
        dx += (force / 50) * Math.cos(Math.toRadians(shipXform.getRz()));
        dy += (force / 50) * Math.sin(Math.toRadians(shipXform.getRz()));
    }

    public void applyTorque(final double torque) {
        double dThetaTemp = dTheta + torque / 20;
        if (Math.abs(dThetaTemp) < maxRotationSpeed) {
            dTheta = dThetaTemp;
        }
    }
    
    public void fire() {
        Xform trajectory = new Xform();
        trajectory.setTx(shipXform.getTx());
        trajectory.setTy(shipXform.getTy());
        trajectory.setRz(shipXform.getRz());
        TriangleMesh bulletMesh = new TriangleMesh();
        bulletMesh.getPoints().addAll(
                0.0f, 0.0f, (float) shipXform.getTz(),
                50.0f, 0.0f, (float) shipXform.getTz());
        bulletMesh.getTexCoords().addAll(0f, 0f);
        bulletMesh.getFaces().addAll(0, 0, 0, 0, 1, 0);
        MeshView bullet = new MeshView(bulletMesh);
        bullet.setMaterial(new PhongMaterial(Color.rgb(240, 255, 100)));
        bullet.setDrawMode(DrawMode.LINE);
        bullet.setCullFace(CullFace.NONE);
        bullet.setTranslateX(-50);
        bullets.put(bullet, true);
        trajectory.getChildren().add(bullet);
        bulletXform.getChildren().add(trajectory);
    }
    
    public boolean hitShield() {
        shieldHit.play();
        return true;
    }

    public void update() {
        double dir = Math.atan2(dy, dx);
        if (Math.sqrt(dx * dx + dy * dy) > 0) {
            dx -= ((dx * dx * handling) / mass) * Math.cos(dir);
            dy -= ((dy * dy * handling) / mass) * Math.sin(dir);
        }

        if (Math.abs(dTheta) > 0) {
            dTheta -= Math.min(Math.signum(dTheta) * (handling / 25), Math.signum(dTheta) * dTheta);
        }

        shipXform.setTranslate(shipXform.getTx() + dx, shipXform.getTy() + dy);
        shipXform.setRz(shipXform.getRz() + dTheta);
        shipXform.setRx(10 * dTheta);
        shipXform.setRy(-15 * Math.sqrt(dx * dx + dy * dy));
    }

    public Ship getShip() {
        return ship;
    }

    public double getX() {
        return shipXform.getTx();
    }

    public double getY() {
        return shipXform.getTy();
    }

    public DoubleProperty xProperty() {
        return shipXform.xProperty();
    }

    public DoubleProperty yProperty() {
        return shipXform.yProperty();
    }

    public double getRz() {
        return shipXform.getRz();
    }

    public DoubleProperty rzProperty() {
        return shipXform.rzProperty();
    }
    
    public double getDx() {
        return dx;
    }
    
    public double getDy() {
        return dy;
    }

    public Xform getMainXform() {
        return mainXform;
    }
    
    public Xform getBulletXform() {
        return bulletXform;
    }
    
    public HashMap<MeshView, Boolean> getBullets() {
        return bullets;
    }
}
