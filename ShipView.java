package spacetrader;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import spacetrader.items.Ship;

/**
 * 
 * @author Team TYN
 */
public class ShipView extends MeshView {
    /**
     * 
     */
    private Ship ship;
    /**
     * 
     */
    private Xform mainXform;
    /**
     * 
     */
    private double dx;
    /**
     * 
     */
    private double dy;
    /**
     * 
     */
    private double dTheta;
    /**
     * 
     */
    private double power;
    /**
     * 
     */
    private double maxSpeed;
    /**
     * 
     */
    private double maxRotationSpeed;
    /**
     * 
     */
    private double handling;
    /**
     * 
     */
    private double mass;

    /**
     * 
     * @param model
     * @param ship 
     */
    public ShipView(final Mesh model, final Ship ship) {
        //super(4, 2, 2);
        super(model);
        this.ship = ship;
        mainXform = new Xform();

        mass = 50;
        handling = 0.3;
        maxSpeed = 2;
        maxRotationSpeed = 2;

        mainXform.getChildren().add(this);
    }

    /**
     *
     * @param force
     */
    public void applyForce(final double force) {
//        double dxTemp = dx + (force / 50) * Math.cos(Math.toRadians(mainXform.getRz()));
//        double dyTemp = dy + (force / 50) * Math.sin(Math.toRadians(mainXform.getRz()));
//        if (Math.sqrt(dxTemp * dxTemp + dyTemp * dyTemp) < maxSpeed) {
//            dx = dxTemp;
//            dy = dyTemp;
//        }
        dx += (force / 50) * Math.cos(Math.toRadians(mainXform.getRz()));
        dy += (force / 50) * Math.sin(Math.toRadians(mainXform.getRz()));
    }

    /**
     *
     * @param torque
     */
    public void applyTorque(final double torque) {
        double dThetaTemp = dTheta + torque / 20;
        if (Math.abs(dThetaTemp) < maxRotationSpeed) {
            dTheta = dThetaTemp;
        }
    }

    /**
     *
     */
    public void update() {
        double dir = Math.atan2(dy, dx);
        if (Math.sqrt(dx * dx + dy * dy) > 0) {
            dx -= ((dx * dx * handling) / mass) * Math.cos(dir);
            dy -= ((dy * dy * handling) / mass) * Math.sin(dir);
//            double slowX = (0.007) * Math.cos(dir);
//            double slowY = (0.007) * Math.sin(dir);
//            dx -= Math.min(slowX, Math.signum(slowX) * dx);
//            dy -= Math.min(slowY, Math.signum(slowY) * dy);
        }

        if (Math.abs(dTheta) > 0) {
            dTheta -= Math.min(Math.signum(dTheta) * (handling / 25), Math.signum(dTheta) * dTheta);
        }

//        if (dTheta > 0) {
//            dTheta -= (handling / 5);
//            if (dTheta <= 0) {
//                dTheta = 0;
//            }
//        } else if (dTheta < 0) {
//            dTheta += (handling / 5);
//            if (dTheta >= 0) {
//                dTheta = 0;
//            }
//        }
        mainXform.setTranslate(mainXform.getTx() + dx, mainXform.getTy() + dy);
        mainXform.setRz(mainXform.getRz() + dTheta);
        mainXform.setRx(10 * dTheta);
        mainXform.setRy(-15 * Math.sqrt(dx * dx + dy * dy));
    }

    /**
     *
     * @return
     */
    public Ship getShip() {
        return ship;
    }

    /**
     *
     * @return
     */
    public double getX() {
        return mainXform.getTx();
    }

    /**
     *
     * @return
     */
    public double getY() {
        return mainXform.getTy();
    }

    /**
     *
     * @return
     */
    public DoubleProperty xProperty() {
        return mainXform.xProperty();
    }

    /**
     *
     * @return
     */
    public DoubleProperty yProperty() {
        return mainXform.yProperty();
    }

    /**
     *
     * @return
     */
    public double getRz() {
        return mainXform.getRz();
    }

    /**
     *
     * @return
     */
    public DoubleProperty rzProperty() {
        return mainXform.rzProperty();
    }

    /**
     *
     * @return
     */
    public Xform getMainXform() {
        return mainXform;
    }
}
