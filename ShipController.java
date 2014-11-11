package spacetrader;

/**
 * The controller for an individual ship in an encounter.
 * @author Team TYN
 */
public interface ShipController {

    /**
     * The control method needed by all ShipControllers.
     */
    void control();

    /**
     * Getter for the ShipView.
     * @return The view representing the ship
     */
    ShipView getShipView();
}
