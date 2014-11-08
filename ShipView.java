/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Box;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import spacetrader.items.Ship;

/**
 *
 * @author Administrator
 */
public class ShipView extends MeshView{
    private Ship ship;
    private Xform mainXform;

    private double dx;
    private double dy;
    private double dTheta;
    
    private double power;
    private double maxSpeed;
    private double maxRotationSpeed;
    private double handling;
    private double mass;
    
    public ShipView(Mesh model, Ship ship) {
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
    
    public void applyForce(double force) {
//        double dxTemp = dx + (force / 50) * Math.cos(Math.toRadians(mainXform.getRz()));
//        double dyTemp = dy + (force / 50) * Math.sin(Math.toRadians(mainXform.getRz()));
//        if (Math.sqrt(dxTemp * dxTemp + dyTemp * dyTemp) < maxSpeed) {
//            dx = dxTemp;
//            dy = dyTemp;
//        }
        dx += (force / 50) * Math.cos(Math.toRadians(mainXform.getRz()));
        dy += (force / 50) * Math.sin(Math.toRadians(mainXform.getRz()));
    }
    
    public void applyTorque(double torque) {
        double dThetaTemp = dTheta + torque / 20;
        if (Math.abs(dThetaTemp) < maxRotationSpeed) {
            dTheta = dThetaTemp;
        }
    }
    
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
    
    public Ship getShip() {
        return ship;
    }
    
    public double getX() {
        return mainXform.getTx();
    }
    
    public double getY() {
        return mainXform.getTy();
    }
    
    public DoubleProperty xProperty() {
        return mainXform.xProperty();
    }
    
    public DoubleProperty yProperty() {
        return mainXform.yProperty();
    }
    
    public double getRz() {
        return mainXform.getRz();
    }
    
    public DoubleProperty rzProperty() {
        return mainXform.rzProperty();
    }
    
    public Xform getMainXform() {
        return mainXform;
    }
}
