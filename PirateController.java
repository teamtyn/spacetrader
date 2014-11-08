/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

/**
 *
 * @author Administrator
 */
public class PirateController implements ShipController {
    private ControlledShipView shipView;
    private boolean turning;
    
    public PirateController(ControlledShipView sV) {
        shipView = sV;
    }

    @Override
    public void control() {
        double distance = shipView.distanceToTarget();
        double angle = shipView.angleToTarget();
        if (Math.abs(angle) < 0.01) {
            turning = false;
        } else if (Math.abs(angle) > 0.2) {
            turning = true;
        }
        if (shipView.angleToTarget() > 0 && turning) {
            shipView.applyTorque(1);
        } else if (shipView.angleToTarget() < 0 && turning) {
            shipView.applyTorque(-1);
        }
        shipView.applyForce(1);
    }

    @Override
    public ShipView getShipView() {
        return shipView;
    }
}
