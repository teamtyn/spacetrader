package spacetrader;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import spacetrader.items.Engine;
import spacetrader.items.Engine.EngineType;
import spacetrader.items.Shield;
import spacetrader.items.Shield.ShieldType;
import spacetrader.items.Ship;
import spacetrader.items.Ship.ShipType;
import spacetrader.items.Weapon;
import spacetrader.items.Weapon.WeaponType;
import spacetrader.player.Player;

/**
 * FXML Controller class for SpaceStation.
 *
 * @author Team TYN
 */
public final class SpaceStationController
        implements Initializable, ControlledScreen {

    /**
     * The length of fade transitions.
     */
    private static final int FADE_DURATION = 1000;
    /**
     * The width of information labels.
     */
    private static final int LABEL_WIDTH = 200;
    /**
     * The height of information labels.
     */
    private static final int LABEL_HEIGHT = 25;
    /**
     * The size of "pictures" of items.
     */
    private static final int PICTURE_SIZE = 100;
    /**
     * A CSS styling option for a certain color.
     */
    private static final String CSS_COLOR_1 = "-fx-background-color: #FFFFFF;";
    /**
     * A CSS styling option for a certain color.
     */
    private static final String CSS_COLOR_2 = "-fx-background-color: #EEEEEE;";

    /**
     * Pane that stores ship ui.
     */
    @FXML
    private Pane shipPane;
    /**
     * Pane that stores gadget ui.
     */
    @FXML
    private Pane gadgetPane;
    /**
     * Button to buy new ship.
     */
    @FXML
    private Button buyShipButton;
    /**
     * Field to confirm new purchases.
     */
    @FXML
    private TextField confirmationField;
    /**
     * List of ships.
     */
    @FXML
    private VBox shipList;
    /**
     * Label that displays player money.
     */
    @FXML
    private Label moneyLabel;
    /**
     * Shows dialog for players.
     */
    @FXML
    private Label shipDialogueField;
    /**
     * Fuel Dialog field.
     */
    @FXML
    private Label fuelDialogueField;
    /**
     * Button to view player card.
     */
    @FXML
    private Button viewPlayerCardButton;
    /**
     * Button to switch between ship and gadget.
     */
    @FXML
    private Button shopForPartsButton;
    /**
     * How much fuel your ship has.
     */
    @FXML
    private ProgressBar fuelProgress;
    /**
     * Shows how much fuel your ship has.
     */
    @FXML
    private Label fuelLabel;
    /**
     * Cost of fuel.
     */
    @FXML
    private Label fuelCostLabel;
    /**
     * Cancels fuel purchase.
     */
    @FXML
    private Button cancelFuelButton;
    /**
     * Buys enough to fill ship.
     */
    @FXML
    private Button fillFuelButton;
    /**
     * Confirms purchase of fuel.
     */
    @FXML
    private Button confirmFuelButton;
    /**
     * Data about other ship.
     */
    @FXML
    private Label otherShipLabel;
    /**
     * Picture of other ship.
     */
    @FXML
    private Pane otherShipPicturePane;
    /**
     * hull of other ship.
     */
    @FXML
    private Label hullStrength;
    /**
     * fuel cap of other ship.
     */
    @FXML
    private Label fuelCapacity;
    /**
     * weapon slots of other ship.
     */
    @FXML
    private Label weaponSlots;
    /**
     * shields of other ship.
     */
    @FXML
    private Label shieldSlots;
    /**
     * engines of other ship.
     */
    @FXML
    private Label engineSlots;
    /**
     * cargo of other ship.
     */
    @FXML
    private Label cargoBaySlots;
    /**
     * cost of other ship.
     */
    @FXML
    private Label shipCost;
    /**
     * Picture of my ship.
     */
    @FXML
    private Pane myShipPicturePane;
    /**
     * hull of my ship.
     */
    @FXML
    private Label myHullStrength;
    /**
     * fuel of my ship.
     */
    @FXML
    private Label myFuelCapacity;
    /**
     * weapons of my ship.
     */
    @FXML
    private Label myWeaponSlots;
    /**
     * shields of my ship.
     */
    @FXML
    private Label myShieldSlots;
    /**
     * engines of my ship.
     */
    @FXML
    private Label myEngineSlots;
    /**
     * cargo bays of my ship.
     */
    @FXML
    private Label myCargoBaySlots;
    /**
     * value of my ship.
     */
    @FXML
    private Label myShipValue;
    /**
     * shows gadgets on ship.
     */
    @FXML
    private Pane gadgetShipViewer;
    /**
     * shows weapons on ship.
     */
    @FXML
    private VBox gadgetWeaponsViewer;
    /**
     * shows shields on ship.
     */
    @FXML
    private VBox gadgetShieldsViewer;
    /**
     * shows engines on ship.
     */
    @FXML
    private VBox gadgetEnginesViewer;
    /**
     * view weapons.
     */
    @FXML
    private Button gadgetWeaponsButton;
    /**
     * list of gadgets.
     */
    @FXML
    private VBox gadgetList;
    /**
     * picture of gadgets.
     */
    @FXML
    private Pane gadgetPicture;
    /**
     * type of gadget.
     */
    @FXML
    private Label gadgetTypeLabel;
    /**
     * name 1 of gadget.
     */
    @FXML
    private Label gadgetNameLabel1;
    /**
     * name 2 of gadget.
     */
    @FXML
    private Label gadgetNameLabel2;
    /**
     * value 1 of gadget.
     */
    @FXML
    private Label gadgetValueLabel1;
    /**
     * value 2 of gadget.
     */
    @FXML
    private Label gadgetValueLabel2;
    /**
     * cost of gadget.
     */
    @FXML
    private Label gadgetCostLabel;
    /**
     * confrmation field for gadget.
     */
    @FXML
    private TextField confirmationGadgetField;

    /**
     * Player's ship.
     */
    private Ship myShip;
    /**
     * Ship being considered for purchase.
     */
    private Ship otherShip;
    /**
     * Cose of fuel.
     */
    private final int fuelCost = 10;
    /**
     * Fuel that we are attempting to add.
     */
    private double tempFuel;
    /**
     * The player.
     */
    private Player player;
    /**
     * Fancy effects.
     */
    private FadeTransition ft;
    /**
     * The screen controller.
     */
    private ScreensController parentController;
    /**
     * The current type of gadgets being looked at.
     */
    private String currentGadgetType;
    /**
     * Current weapon being looked at.
     */
    private Weapon selectedWeapon;
    /**
     * Current shield being looked at.
     */
    private Shield selectedShield;
    /**
     * Current engine being looked at.
     */
    private Engine selectedEngine;
    /**
     * Used to determine where the gadget is in the array.
     */
    private int gadgetIndex;
    /**
     * Dimension of squares used to represent things.
     */
    private int rectangleLength;
    /**
     * Dimension of squares used to represent things.
     */
    private static final int RECT_LENGTH = 25;

    /**
     * Determines which fuel buttons should currently be disabled.Maintains the.
     * progress bar and labels associated with fuel.
     */
    public void updateFuel() {
        fuelProgress.setProgress(tempFuel / player.getShip().getFuelCapacity());
        fuelLabel.setText(Math.round(tempFuel) + "/" + Math.round(
                player.getShip().getFuelCapacity()));
        confirmFuelButton.setDisable(player.getShip().getFuel() == tempFuel);
        cancelFuelButton.setDisable(player.getShip().getFuel() == tempFuel);
        fillFuelButton.setDisable(
                tempFuel == player.getShip().getFuelCapacity());
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }

    /**
     * Sets up the My Ship panel with the appropriate info. Picture is currently
     * a colored rectangle.
     */
    public void myShipStats() {
        myHullStrength.setText(Integer.toString(myShip.getHull()));
        myFuelCapacity.setText(Double.toString(myShip.getFuelCapacity()));
        myWeaponSlots.setText(Integer.toString(myShip.getWeaponSlots()));
        myShieldSlots.setText(Integer.toString(myShip.getShieldSlots()));
        myEngineSlots.setText(Integer.toString(myShip.getWeaponSlots()));
        myCargoBaySlots.setText(Integer.toString(myShip.getCargoBaySlots()));
        myShipValue.setText(Integer.toString(myShip.getType().getCost()));
        myShipPicturePane.getChildren().removeAll();
        final Rectangle myShipPicture
                = new Rectangle(100, 10, PICTURE_SIZE, PICTURE_SIZE);
        myShipPicture.setFill(myShip.getType().getColor());
        myShipPicturePane.getChildren().add(myShipPicture);
    }

    /**
     * Sets up the Other Ship panel with the appropriate info. Picture is.
     * currently a colored rectangle.
     */
    public void otherShipStats() {
        otherShipLabel.setText(otherShip.getType().name());
        hullStrength.setText(Integer.toString(otherShip.getHull()));
        fuelCapacity.setText(Double.toString(otherShip.getFuelCapacity()));
        weaponSlots.setText(Integer.toString(otherShip.getWeaponSlots()));
        shieldSlots.setText(Integer.toString(otherShip.getShieldSlots()));
        engineSlots.setText(Integer.toString(otherShip.getEngineSlots()));
        cargoBaySlots.setText(Integer.toString(otherShip.getCargoBaySlots()));
        otherShipPicturePane.getChildren().removeAll();
        final Rectangle otherShipPicture
                = new Rectangle(100, 10, PICTURE_SIZE, PICTURE_SIZE);
        otherShipPicture.setFill(otherShip.getType().getColor());
        otherShipPicturePane.getChildren().add(otherShipPicture);
        shipCost.setText(Integer.toString(otherShip.getType().getCost()));
    }

    /**
     * Informs the user if they are not allowed to buy the currently selected
     * ship. Calls the methods to keep myShip and otherShip up to date
     */
    public void updateShip() {
        if (otherShip.getType() == myShip.getType()) {
            shipDialogueField.setText("You already own this type of ship!");
            buyShipButton.setDisable(true);
        } else if (otherShip.getType().getCost() > player.getMoney()) {
            shipDialogueField.setText("You cannot afford this ship!");
            buyShipButton.setDisable(true);
        } else {
            shipDialogueField.setText("");
            buyShipButton.setDisable(false);
        }
        myShipStats();
        otherShipStats();
    }

    /**
     * Handles purchase of a ship. Subtracts/Adds money from/to the player,
     * transfers the parts, and then resets all variables after giving the
     * player their new ship
     */
    public void buyShip() {
        player.subtractMoney(otherShip.getType().getCost());
        player.addMoney(myShip.getType().getCost());
        transferParts();
        player.setShip(otherShip);
        myShip = otherShip;
        otherShip = new Ship(otherShip.getType());
    }

    /**
     * Transfers parts from player's old ship to new ship upon purchase.
     */
    public void transferParts() {
        for (int i = 0; i < player.getShip().getWeapons().length; i++) {
            if (otherShip.addWeapon(player.getShip().getWeapons()[i])) {
                player.getShip().removeWeapon(i);
            }
        }
        for (Weapon weapon : player.getShip().getWeapons()) {
            if (weapon != null) {
                player.addMoney(weapon.getCost() / 2);
                System.out.println("Excess weapon sold for: "
                        + weapon.getCost() / 2);
            }
        }
        for (int i = 0; i < player.getShip().getShields().length; i++) {
            if (otherShip.addShield(player.getShip().getShields()[i])) {
                player.getShip().removeShield(i);
            }
        }
        for (Shield shield : player.getShip().getShields()) {
            if (shield != null) {
                player.addMoney(shield.getCost() / 2);
                System.out.println("Excess shield sold for: "
                        + shield.getCost() / 2);
            }
        }
        for (int i = 0; i < player.getShip().getEngines().length; i++) {
            if (otherShip.addEngine(player.getShip().getEngines()[i])) {
                player.getShip().removeEngine(i);
            }
        }
        for (Engine engine : player.getShip().getEngines()) {
            if (engine != null) {
                player.addMoney(engine.getCost() / 2);
                System.out.println("Excess weapon sold for: "
                        + engine.getCost() / 2);
            }
        }
        boolean hasEngine = false;
        for (Engine engine : otherShip.getEngines()) {
            if (engine != null) {
                hasEngine = true;
            }
        }
        if (!hasEngine) {
            System.out.println("Complimentary engine yay");
            otherShip.addEngine(new Engine(EngineType.Hackney));
        }
        final HashMap<String, Integer> goods
                = player.getShip().getCargoBay().getGoods();
        for (String goodName : goods.keySet()) {
            otherShip.storeTradeGood(goodName, goods.get(goodName));
        }
    }

    @Override
    public void initialize(final URL url, final ResourceBundle rBundle) {
        ft = new FadeTransition(
                Duration.millis(FADE_DURATION), fuelDialogueField);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
    }

    @Override
    public void lazyInitialize() {
        player = GameModel.getPlayer();
        myShip = player.getShip();
        otherShip = player.getShip();
        fuelCostLabel.setText(Integer.toString(fuelCost));
        tempFuel = myShip.getFuel();
        updateFuel();
        updateShip();
        for (ShipType type : ShipType.values()) {
            final int mult = type.ordinal();
            final HBox row = new HBox();
            final Label label = new Label(type.name());
            label.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(final MouseEvent mouseEvent) {
                            otherShip = new Ship(type);
                            for (Node node : shipList.getChildren()) {
                                node.setStyle(CSS_COLOR_1);
                            }
                            row.setStyle(CSS_COLOR_2);
                            row.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
                            row.setLayoutY(mult * LABEL_HEIGHT);
                            buyShipButton.setDisable(false);
                            updateShip();
                        }
                    });
            shipList.getChildren().add(row);
        }
        gadgetPane.setVisible(false);
    }

    /**
     * Returns to the universe map.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void backButtonAction(final ActionEvent event) {
        parentController.setScreen("UniverseMap");
    }

    /**
     * Does nothing right now.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void viewPlayerCardButtonAction(final ActionEvent event) {
        // TODO
        //parentController.setScreen("PlayerCard");
    }

    /**
     * Toggles between parts and ships.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void shopForPartsButtonAction(final ActionEvent event) {
        if (shipPane.isVisible()) {
            shipPane.setVisible(false);
            gadgetPane.setVisible(true);
            shopForPartsButton.setText("Shop for Ships");
            initializeGadgets();
        } else {
            int engineCount = 0;
            for (Engine engine : player.getShip().getEngines()) {
                if (engine != null) {
                    engineCount++;
                }
            }
            if (engineCount > 0) {
                shipPane.setVisible(true);
                gadgetPane.setVisible(false);
                shopForPartsButton.setText("Shop for Parts");
            } else {
                shipDialogueField.setText("Hey buddy, I can't "
                        + "let you leave with no engine");
                viewEngines(new ActionEvent());
            }
        }
    }

    /**
     * Sets up the gadget buying pane.
     */
    private void initializeGadgets() {
        shipDialogueField.setText("");
        gadgetShipViewer.getChildren().removeAll();
        rectangleLength = RECT_LENGTH;
        Rectangle shipPicture
                = new Rectangle(rectangleLength,
                        rectangleLength, PICTURE_SIZE, PICTURE_SIZE);
        shipPicture.setFill(myShip.getType().getColor());
        gadgetShipViewer.getChildren().add(shipPicture);

        updatePlayerWeapons();
        updatePlayerShields();
        updatePlayerEngines();
    }

    /**
     * Shows you the available weapons.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void viewWeapons(final ActionEvent event) {
        gadgetTypeLabel.setText("Weapons");
        gadgetList.getChildren().clear();
        for (WeaponType type : WeaponType.values()) {
            if(type != WeaponType.EmptySlot){
                int mult = type.ordinal();
                HBox row = new HBox();
                Label label = new Label(type.name());
                label.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
                label.setAlignment(Pos.CENTER);
                row.getChildren().add(label);
                row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(final MouseEvent mouseEvent) {
                                currentGadgetType = "Weapon";
                                selectedWeapon = new Weapon(type);
                                resetSelected();
                                row.setStyle(CSS_COLOR_2);
                                row.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
                                row.setLayoutY(mult * LABEL_HEIGHT);
                                gadgetNameLabel1.setText("Damage ");
                                gadgetNameLabel2.setText("Rate of Fire");
                                gadgetValueLabel1.setText(Integer.toString(
                                                selectedWeapon.getDamage()));
                                gadgetValueLabel2.setText(Integer.toString(
                                                selectedWeapon.getRateOfFire()));
                                gadgetCostLabel.setText(Integer.toString(
                                                selectedWeapon.getCost()));
                                gadgetPicture.getChildren().clear();
                                Rectangle myGadgetPicture = new Rectangle(
                                        rectangleLength + rectangleLength, 10,
                                        PICTURE_SIZE, PICTURE_SIZE);
                                myGadgetPicture.setFill(selectedWeapon.getColor());
                                gadgetPicture.getChildren().add(myGadgetPicture);
                            }
                        });
                gadgetList.getChildren().add(row);
            }
        }
    }

    /**
     * Shows you the available shields.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void viewShields(final ActionEvent event) {
        gadgetTypeLabel.setText("Shields");
        gadgetList.getChildren().clear();
        for (ShieldType type : ShieldType.values()) {
            if(type != ShieldType.EmptySlot){
                final int mult = type.ordinal();
                final HBox row = new HBox();
                final Label label = new Label(type.name());
                label.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
                label.setAlignment(Pos.CENTER);
                row.getChildren().add(label);
                row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(final MouseEvent mouseEvent) {
                                currentGadgetType = "Shield";
                                selectedShield = new Shield(type);
                                resetSelected();
                                row.setStyle(CSS_COLOR_2);
                                row.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
                                row.setLayoutY(mult * LABEL_HEIGHT);
                                gadgetNameLabel1.setText("Strength ");
                                gadgetNameLabel2.setText("Recharge Rate");
                                gadgetValueLabel1.setText(Integer.toString(
                                                selectedShield.getStrength()));
                                gadgetValueLabel2.setText(Integer.toString(
                                                selectedShield.getRechargeRate()));
                                gadgetCostLabel.setText(Integer.toString(
                                                selectedShield.getCost()));
                                gadgetPicture.getChildren().clear();
                                final Rectangle myGadgetPicture = new Rectangle(
                                        100, 10, PICTURE_SIZE, PICTURE_SIZE);
                                myGadgetPicture.setFill(
                                        selectedShield.getColor());
                                gadgetPicture.getChildren().add(myGadgetPicture);
                            }
                        });
                gadgetList.getChildren().add(row);
            }
        }
    }

    /**
     * Shows you the available engines.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void viewEngines(final ActionEvent event) {
        gadgetTypeLabel.setText("Engines");
        gadgetList.getChildren().clear();
        for (EngineType type : EngineType.values()) {
            if(type != EngineType.EmptySlot){
                final int mult = type.ordinal();
                final HBox row = new HBox();
                final Label label = new Label(type.name());
                label.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
                label.setAlignment(Pos.CENTER);
                row.getChildren().add(label);
                row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(final MouseEvent mouseEvent) {
                                currentGadgetType = "Engine";
                                selectedEngine = new Engine(type);
                                resetSelected();
                                row.setStyle(CSS_COLOR_2);
                                row.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
                                row.setLayoutY(mult * LABEL_HEIGHT);
                                gadgetNameLabel1.setText("Acceleration ");
                                gadgetNameLabel2.setText("Fuel Efficiency");
                                gadgetValueLabel1.setText(Integer.toString(
                                                selectedEngine.getAcceleration()));
                                gadgetValueLabel2.setText(Integer.toString(
                                                selectedEngine
                                                .getFuelEfficiency()));
                                gadgetCostLabel.setText(Integer.toString(
                                                selectedEngine.getCost()));
                                gadgetPicture.getChildren().clear();
                                final Rectangle myGadgetPicture = new Rectangle(
                                        100, 10, PICTURE_SIZE, PICTURE_SIZE);
                                myGadgetPicture.setFill(
                                        selectedEngine.getColor());
                                gadgetPicture.getChildren().add(myGadgetPicture);
                            }
                        });
                gadgetList.getChildren().add(row);
            }
        }
    }

    /**
     * updates the display of player weapons.
     */
    private void updatePlayerWeapons() {
        gadgetWeaponsViewer.getChildren().clear();
        for (Weapon weapon : player.getShip().getWeapons()) {
            HBox row = new HBox();
            Label label;
            if (weapon != null) {
                label = new Label(weapon.getName());
                row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(final MouseEvent mouseEvent) {
                                currentGadgetType = "My Weapon";
                                selectedWeapon = weapon;
                                resetSelected();
                                row.setStyle(CSS_COLOR_2);
                                gadgetNameLabel1.setText("Damage ");
                                gadgetNameLabel2.setText("Rate of Fire");
                                gadgetValueLabel1.setText(Integer.toString(
                                                selectedWeapon.getDamage()));
                                gadgetValueLabel2.setText(Integer.toString(
                                                selectedWeapon
                                                .getRateOfFire()));
                                gadgetCostLabel.setText(Integer.toString(
                                                selectedWeapon.getCost() / 2));
                                gadgetPicture.getChildren().clear();
                                final Rectangle myGadgetPicture = new Rectangle(
                                        100, 10, PICTURE_SIZE, PICTURE_SIZE);
                                myGadgetPicture.setFill(
                                        selectedWeapon.getColor());
                                gadgetPicture
                                .getChildren().add(myGadgetPicture);
                                gadgetIndex = row.getParent().
                                getChildrenUnmodifiable().indexOf(row);
                            }
                        });
            } else {
                label = new Label("Empty slot");
            }
            label.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            gadgetWeaponsViewer.getChildren().add(row);
        }
    }

    /**
     * updates the display of player shields.
     */
    private void updatePlayerShields() {
        gadgetShieldsViewer.getChildren().clear();
        for (Shield shield : player.getShip().getShields()) {
            System.out.println(shield);
            final HBox row = new HBox();
            Label label;
            if (shield != null) {
                label = new Label(shield.getName());
                row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(final MouseEvent mouseEvent) {
                                currentGadgetType = "My Shield";
                                selectedShield = shield;
                                resetSelected();
                                row.setStyle(CSS_COLOR_2);
                                gadgetNameLabel1.setText("Strength ");
                                gadgetNameLabel2.setText("Recharge Rate");
                                gadgetValueLabel1.setText(Integer.toString(
                                                selectedShield.getStrength()));
                                gadgetValueLabel2.setText(Integer.toString(
                                                selectedShield
                                                .getRechargeRate()));
                                gadgetCostLabel.setText(Integer.toString(
                                                selectedShield.getCost() / 2));
                                gadgetPicture.getChildren().clear();
                                final Rectangle myGadgetPicture = new Rectangle(
                                        100, 10, PICTURE_SIZE, PICTURE_SIZE);
                                myGadgetPicture.setFill(
                                        selectedShield.getColor());
                                gadgetPicture
                                .getChildren().add(myGadgetPicture);
                                gadgetIndex = row.getParent()
                                .getChildrenUnmodifiable().indexOf(row);
                            }
                        });
            } else {
                label = new Label("Empty slot");
            }
            label.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            gadgetShieldsViewer.getChildren().add(row);
        }
    }

    /**
     * updates the display of player engines.
     */
    private void updatePlayerEngines() {
        gadgetEnginesViewer.getChildren().clear();
        for (Engine engine : player.getShip().getEngines()) {
            final HBox row = new HBox();
            Label label;
            if (engine != null) {
                label = new Label(engine.getName());
                row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(final MouseEvent mouseEvent) {
                                currentGadgetType = "My Engine";
                                selectedEngine = engine;
                                resetSelected();
                                row.setStyle(CSS_COLOR_2);
                                gadgetNameLabel1.setText("Acceleration ");
                                gadgetNameLabel2.setText("Fuel Efficiency");
                                gadgetValueLabel1.setText(Integer.toString(
                                                selectedEngine
                                                .getAcceleration()));
                                gadgetValueLabel2.setText(Integer.toString(
                                                selectedEngine
                                                .getFuelEfficiency()));
                                gadgetCostLabel.setText(Integer.toString(
                                                selectedEngine.getCost() / 2));
                                gadgetPicture.getChildren().clear();
                                final Rectangle myGadgetPicture = new Rectangle(
                                        100, 10, PICTURE_SIZE, PICTURE_SIZE);
                                myGadgetPicture.setFill(
                                        selectedEngine.getColor());
                                gadgetPicture
                                .getChildren().add(myGadgetPicture);
                                gadgetIndex = row.getParent()
                                .getChildrenUnmodifiable().indexOf(row);
                            }
                        });
            } else {
                label = new Label("Empty slot");
            }
            label.setPrefSize(LABEL_WIDTH, LABEL_HEIGHT);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            gadgetEnginesViewer.getChildren().add(row);
        }
    }

    /**
     * resets the ui of all elements.
     */
    private void resetSelected() {
        for (Node node : gadgetList.getChildren()) {
            node.setStyle(CSS_COLOR_1);
        }
        for (Node node : gadgetWeaponsViewer.getChildren()) {
            node.setStyle(CSS_COLOR_1);
        }
        for (Node node : gadgetShieldsViewer.getChildren()) {
            node.setStyle(CSS_COLOR_1);
        }
        for (Node node : gadgetEnginesViewer.getChildren()) {
            node.setStyle(CSS_COLOR_1);
        }
    }

    /**
     * Buys a new ship.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void buyShipButtonAction(final ActionEvent event) {
        if (confirmationField.getText().trim().equals(
                Integer.toString(otherShip.getType().getCost()))) {
            shipDialogueField.setText("");
            confirmationField.setText("");
            buyShip();
            updateFuel();
            updateShip();
        } else {
            shipDialogueField
                    .setText("Please confirm the price of your new ship.");
        }
    }

    /**
     * Buys a new gadget.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void buyGadgetButtonAction(final ActionEvent event) {
        switch (currentGadgetType) {
            case "Weapon":
                if (confirmationGadgetField.getText().trim().equals(
                        Integer.toString(selectedWeapon.getCost()))) {
                    shipDialogueField.setText("");
                    confirmationGadgetField.setText("");
                    buyWeapon();
                } else {
                    shipDialogueField.setText("Please confirm the "
                            + "price of your new weapon.");
                }
                break;
            case "Shield":
                if (confirmationGadgetField.getText().trim().equals(
                        Integer.toString(selectedShield.getCost()))) {
                    shipDialogueField.setText("");
                    confirmationGadgetField.setText("");
                    buyShield();
                } else {
                    shipDialogueField.setText("Please confirm the "
                            + "price of your new shield.");
                }
                break;
            case "Engine":
                if (confirmationGadgetField.getText().trim().equals(
                        Integer.toString(selectedEngine.getCost()))) {
                    shipDialogueField.setText("");
                    confirmationGadgetField.setText("");
                    buyEngine();
                } else {
                    shipDialogueField.setText("Please confirm the "
                            + "price of your new engine.");
                }
                break;
            default:
        }
    }

    /**
     * Sells a gadget.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void sellGadgetButtonAction(final ActionEvent event) {
        switch (currentGadgetType) {
            case "My Weapon":
                player.addMoney(selectedWeapon.getCost() / 2);
                shipDialogueField.setText("Weapon sold for "
                        + selectedWeapon.getCost() / 2);
                player.getShip().removeWeapon(gadgetIndex);
                updatePlayerWeapons();
                gadgetIndex = -1;
                currentGadgetType = "None";
                break;
            case "My Shield":
                shipDialogueField.setText("Shield sold for "
                        + selectedShield.getCost() / 2);
                player.addMoney(selectedShield.getCost() / 2);
                player.getShip().removeShield(gadgetIndex);
                updatePlayerShields();
                gadgetIndex = -1;
                currentGadgetType = "None";
                break;
            case "My Engine":
                player.addMoney(selectedEngine.getCost() / 2);
                shipDialogueField.setText("Engine sold for "
                        + selectedEngine.getCost() / 2);
                player.getShip().removeEngine(gadgetIndex);
                updatePlayerEngines();
                gadgetIndex = -1;
                currentGadgetType = "None";
                break;
            default:
                shipDialogueField.setText("Please select a"
                        + "valid part to sell.");
                break;
        }
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }

    /**
     * Purchases a weapon. Checks to see if the player has enough money for the.
     * weapon If so, removes the money from the player and adds the weapon to.
     * the player's ship.
     */
    public void buyWeapon() {
        if (player.getMoney() >= selectedWeapon.getCost()) {
            player.subtractMoney(selectedWeapon.getCost());
            if (player.getShip().addWeapon(selectedWeapon)) {
                shipDialogueField.setText("Purchase complete");
            } else {
                shipDialogueField.setText("Purchase failed, cost refunded");
                player.addMoney(selectedWeapon.getCost());
            }
        } else {
            shipDialogueField.setText("Not enough money");
        }
        updatePlayerWeapons();
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }

    /**
     * Purchases a shield. Checks to see if the player has enough money for the.
     * shield If so, removes the money from the player and adds the shield to.
     * the player's ship.
     */
    public void buyShield() {
        if (player.getMoney() >= selectedShield.getCost()) {
            player.subtractMoney(selectedShield.getCost());
            if (player.getShip().addShield(selectedShield)) {
                shipDialogueField.setText("Purchase complete");
            } else {
                shipDialogueField.setText("Purchase failed, cost refunded");
                player.addMoney(selectedShield.getCost());
            }
        } else {
            shipDialogueField.setText("Not enough money");
        }
        updatePlayerShields();
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }

    /**
     * Purchases an engine. Checks to see if the player has enough money for.
     * the engine If so, removes the money from the player and adds the engine.
     * to the player's ship.
     */
    public void buyEngine() {
        if (player.getMoney() >= selectedEngine.getCost()) {
            player.subtractMoney(selectedEngine.getCost());
            if (player.getShip().addEngine(selectedEngine)) {
                shipDialogueField.setText("Purchase complete");
            } else {
                shipDialogueField.setText("Purchase failed, cost refunded");
                player.addMoney(selectedEngine.getCost());
            }
        } else {
            shipDialogueField.setText("Not enough money");
        }
        updatePlayerEngines();
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }

    /**
     * Confirms purchase of fuel.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void confirmFuelButtonAction(final ActionEvent event) {
        final int newFuel = (int) (tempFuel - player.getShip().getFuel());
        fuelDialogueField.setText("Purchased " + newFuel + " gallons of fuel.");
        if (ft != null) {
            ft.play();
        }
        player.getShip().addFuel(newFuel);
        player.subtractMoney((int) (newFuel * fuelCost));
        updateFuel();
    }

    /**
     * Cancels purchase of fuel.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void cancelFuelButtonAction(final ActionEvent event) {
        tempFuel = player.getShip().getFuel();
        updateFuel();
    }

    /**
     * Fills up your fuel tank.
     *
     * @param event the event that triggers this method.
     */
    @FXML
    private void fillFuelButtonAction(final ActionEvent event) {
        final int potentialNewFuel = (int) (player.getShip().getFuelCapacity()
                - player.getShip().getFuel());
        final int canAffordFuel = player.getMoney() / fuelCost;
        if (canAffordFuel >= potentialNewFuel) {
            tempFuel = player.getShip().getFuelCapacity();
        } else {
            tempFuel = player.getShip().getFuel() + canAffordFuel;
            fuelDialogueField.setText("Can only afford "
                    + canAffordFuel + " gallons of fuel.");
            if (ft != null) {
                ft.play();
            }
        }
        updateFuel();
    }

    @Override
    public void setScreenParent(final ScreensController aParentController) {
        parentController = aParentController;
    }
}
