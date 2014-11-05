/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import spacetrader.items.Ship;

/**
 *
 * @author Administrator
 */
public class ShipView extends Box{
    private Ship ship;
    private Xform mainXform;
    private double mass;
    
    private double dx;
    private double dy;
    private double dtheta;
    
    public ShipView(Ship ship) {
        super(2, 4, 2);
        this.ship = ship;
        
        mainXform = new Xform();
        
        mainXform.getChildren().add(this);
    }
    
    public void applyForce(double force) {
        double direction = mainXform.getRz();
        dx += (force / mass) * Math.cos(Math.toRadians(direction));
        dy += (force / mass) * Math.sin(Math.toRadians(direction));
    }
    
    public void applyTorque(double torque) {
        dtheta += torque / mass;
    }
    
    public void update() {
        mainXform.setTranslate(mainXform.getTx() + dx, mainXform.getTy() + dy);
        mainXform.setRz(mainXform.getRz() + dtheta);
    }
    
    public Ship getShip() {
        return ship;
    }
}
