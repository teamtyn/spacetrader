package spacetrader;

/**
 * The controller for an individual ship in an encounter.
 * @author Administrator
 */
public interface ShipController {

    /**
     * The control method needed by all ShipControllers.
     */
    void control();

    /**
     * Getter for the ShipView.
     * @return the view representing the ship
     */
    ShipView getShipView();
}
