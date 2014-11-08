/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import javafx.scene.shape.Mesh;
import spacetrader.items.Ship;

/**
 *
 * @author Administrator
 */
public class ControlledShipView extends ShipView{
    public enum ControlType {PIRATE};
    private ShipController controller;
    private ShipView target;
    
    public ControlledShipView(Mesh model, Ship ship, ControlType type) {
        super(model, ship);
        
        switch (type){
            case PIRATE:
                controller = new PirateController(this);
        }
    }
    
    public ShipController getController() {
        return controller;
    }
    
    public ShipView getTarget() {
        return target;
    }
    
    public void setTarget(ShipView t) {
        target = t;
    }
    
    public double distanceToTarget() {
        if (target != null) {
            return Math.sqrt((getX() - target.getX()) * (getX() - target.getX()) +
                (getY() - target.getY()) * (getY() - target.getY()));
        } else {
            return 0.0;
        }
    }
    
    public double angleToTarget() {
        double mag = distanceToTarget();
        if (target != null && mag > 0) {
            double tx = (target.getX() - getX()) / mag;
            double ty = (target.getY() - getY()) / mag;
            double rx = Math.cos(Math.toRadians(getRz()));
            double ry = Math.sin(Math.toRadians(getRz()));
            return (rx * ty - ry * tx) * Math.acos(rx * tx + ry * ty);
        } else {
            return 0.0;
        }
    }
}
