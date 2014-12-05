package spacetrader;

/**
 *
 * @author Team TYN
 */
public class PirateController implements ShipController {
    /**
     * 
     */
    private ControlledShipView shipView;
    /**
     * 
     */
    private boolean turning;

    /**
     * 
     * @param sView 
     */
    public PirateController(final ControlledShipView sView) {
        shipView = sView;
    }

    @Override
    public void control() {
        double distance = shipView.distanceToTarget();
        double angle = shipView.angleToTarget();
        if (Math.abs(angle) < 0.01) {
            turning = false;
        } else if (Math.abs(angle) > 0.3) {
            turning = true;
        }
        
        if (Math.abs(angle) < 0.2 && distance < 200) {
            shipView.fire();
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
