package spacetrader;

import javafx.scene.shape.Mesh;
import spacetrader.items.Ship;

/**
 * Controller that displays a controlled ship.
 * @author Team TYN
 */
public class ControlledShipView extends ShipView {

    /**
     * 
     */
    public enum ControlType {
        PIRATE
    };
    /**
     * 
     */
    private ShipController controller;
    /**
     * 
     */
    private ShipView target;

    /**
     * The constructor for this class.
     *
     * @param model 3D mesh that goes on the ship
     * @param ship The ship's data stored in a ship object
     * @param type The type of controlled ship
     */
    public ControlledShipView(final Mesh model, final Ship ship,
            final ControlType type) {
        super(model, ship);

        switch (type) {
            case PIRATE:
                controller = new PirateController(this);
        }
    }

    /**
     * Getter for the controller.
     * 
     * @return the controller.
     */
    public ShipController getController() {
        return controller;
    }

    /**
     * Getter for this ship's target.
     * 
     * @return this ship's target.
     */
    public ShipView getTarget() {
        return target;
    }

    /**
     * Setter for this ship's target.
     * 
     * @param t the new target.
     */
    public void setTarget(ShipView t) {
        target = t;
    }

    /**
     * Calculates the distance to the target.
     * 
     * @return distance to the target.
     */
    public double distanceToTarget() {
        if (target != null) {
            return Math.sqrt((getX() - target.getX()) * (getX() - target.getX())
                    + (getY() - target.getY()) * (getY() - target.getY()));
        } else {
            return 0.0;
        }
    }

    /**
     * Calculates the angle towards the target.
     * 
     * @return the angle.
     */
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
