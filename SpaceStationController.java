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
 * @author Team TYN
 */
public class SpaceStationController implements Initializable, ControlledScreen {

    @FXML
    private Pane shipPane;
    @FXML
    private Pane gadgetPane;

    @FXML
    private Button buyShip;
    @FXML
    private TextField confirmationField;
    @FXML
    private VBox shipList;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label shipDialogueField;
    @FXML
    private Label fuelDialogueField;
    @FXML
    private Button viewPlayerCardButton;
    @FXML
    private Button shopForPartsButton;

    @FXML
    private ProgressBar fuelProgress;
    @FXML
    private Label fuelLabel;
    @FXML
    private Label fuelCostLabel;
    @FXML
    private Button cancelFuelButton;
    @FXML
    private Button fillFuelButton;
    @FXML
    private Button confirmFuelButton;

    @FXML
    private Label otherShipLabel;
    @FXML
    private Pane otherShipPicturePane;
    @FXML
    private Label hullStrength;
    @FXML
    private Label fuelCapacity;
    @FXML
    private Label weaponSlots;
    @FXML
    private Label shieldSlots;
    @FXML
    private Label engineSlots;
    @FXML
    private Label cargoBaySlots;
    @FXML
    private Label shipCost;

    @FXML
    private Pane myShipPicturePane;
    @FXML
    private Label myHullStrength;
    @FXML
    private Label myFuelCapacity;
    @FXML
    private Label myWeaponSlots;
    @FXML
    private Label myShieldSlots;
    @FXML
    private Label myEngineSlots;
    @FXML
    private Label myCargoBaySlots;
    @FXML
    private Label myShipValue;

    @FXML
    private Pane gadgetShipViewer;
    @FXML
    private VBox gadgetWeaponsViewer;
    @FXML
    private VBox gadgetShieldsViewer;
    @FXML
    private VBox gadgetEnginesViewer;
    @FXML
    private Button gadgetWeaponsButton;
    @FXML
    private Button gadgetShieldsButton;
    @FXML
    private Button gadgetEnginessButton;
    @FXML
    private Pane gadgetDetailsPane;
    @FXML
    private VBox gadgetList;
    @FXML
    private Button gadgetBuyButton;
    @FXML
    private Pane gadgetPicture;
    @FXML
    private Label gadgetTypeLabel;
    @FXML
    private Label gadgetNameLabel1;
    @FXML
    private Label gadgetNameLabel2;
    @FXML
    private Label gadgetValueLabel1;
    @FXML
    private Label gadgetValueLabel2;
    @FXML
    private Label gadgetCostLabel;
    @FXML
    private TextField confirmationGadgetField;

    /**
     * 
     */
    private Ship myShip;
    /**
     * 
     */
    private Ship otherShip;
    /**
     * 
     */
    private final int fuelCost = 10;
    /**
     * 
     */
    private double tempFuel;
    /**
     * 
     */
    private Player player;
    /**
     * 
     */
    private FadeTransition ft;
    /**
     * 
     */
    private ScreensController parentController;
    /**
     * 
     */
    private String currentGadgetType;
    /**
     * 
     */
    private Weapon selectedWeapon;
    /**
     * 
     */
    private Shield selectedShield;
    /**
     * 
     */
    private Engine selectedEngine;
    /**
     * 
     */
    private int gadgetIndex;

    /**
     * Determines which fuel buttons should currently be disabled.
     * Maintains the progress bar and labels associated with fuel
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
     * Sets up the My Ship panel with the appropriate info.
     * Picture is currently a colored rectangle, TODO: Get Josh's 3D ship
     */
    public void myShipStats() {
        myHullStrength.setText(Integer.toString(myShip.getHull()));
        myFuelCapacity.setText(Double.toString(myShip.getFuelCapacity()));
        myWeaponSlots.setText(Integer.toString(myShip.getWeaponSlots()));
        myShieldSlots.setText(Integer.toString(myShip.getShieldSlots()));
        myEngineSlots.setText(Integer.toString(myShip.getWeaponSlots()));
        myCargoBaySlots.setText(Integer.toString(myShip.getCargoBaySlots()));
        myShipValue.setText(Integer.toString(myShip.type.getCost()));
        myShipPicturePane.getChildren().removeAll();
        final Rectangle myShipPicture = new Rectangle(100, 10, 100, 100);
        myShipPicture.setFill(myShip.type.getColor());
        myShipPicturePane.getChildren().add(myShipPicture);
    }

    /**
     * Sets up the Other Ship panel with the appropriate info.
     * Picture is currently a colored rectangle, TODO: Get Josh's 3D ship
     */
    public void otherShipStats() {
        otherShipLabel.setText(otherShip.type.name());
        hullStrength.setText(Integer.toString(otherShip.getHull()));
        fuelCapacity.setText(Double.toString(otherShip.getFuelCapacity()));
        weaponSlots.setText(Integer.toString(otherShip.getWeaponSlots()));
        shieldSlots.setText(Integer.toString(otherShip.getShieldSlots()));
        engineSlots.setText(Integer.toString(otherShip.getEngineSlots()));
        cargoBaySlots.setText(Integer.toString(otherShip.getCargoBaySlots()));
        otherShipPicturePane.getChildren().removeAll();
        final Rectangle otherShipPicture = new Rectangle(100, 10, 100, 100);
        otherShipPicture.setFill(otherShip.type.getColor());
        otherShipPicturePane.getChildren().add(otherShipPicture);
        shipCost.setText(Integer.toString(otherShip.type.getCost()));
    }

    /**
     * Informs the user if they are not allowed to buy the currently
     *     selected ship.
     * Calls the methods to keep myShip and otherShip up to date
     */
    public void updateShip() {
        if (otherShip.type == myShip.type) {
            shipDialogueField.setText("You already own this type of ship!");
            buyShip.setDisable(true);
        } else if (otherShip.type.getCost() > player.getMoney()) {
            shipDialogueField.setText("You cannot afford this ship!");
            buyShip.setDisable(true);
        } else {
            shipDialogueField.setText("");
            buyShip.setDisable(false);
        }
        myShipStats();
        otherShipStats();
    }

    /**
     * Handles purchase of a ship.
     * Subtracts/Adds money from/to the player, transfers the parts, and
     *     then resets all variables after giving the player their new ship
     */
    public void buyShip() {
        player.subtractMoney(otherShip.type.getCost());
        player.addMoney(myShip.type.getCost());
        transferParts();
        player.setShip(otherShip);
        myShip = otherShip;
        otherShip = new Ship(otherShip.type);
    }

    /**
     * Transfers parts from player's old ship to new ship upon purchase.
     */
    public void transferParts() {
        // TODO: Account for new ship not being able to fit any
        //     of these things, esp. goods
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
                player.addMoney(shield.getType().cost / 2);
                System.out.println("Excess shield sold for: "
                        + shield.getType().cost / 2);
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

    // Sets up the fade transition specifications
    @Override
    public void initialize(final URL url, final ResourceBundle rBundle) {
        ft = new FadeTransition(Duration.millis(1000), fuelDialogueField);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
    }

    /**
     *
     */
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
            label.setPrefSize(200, 25);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    otherShip = new Ship(type);
                    for (Node node : shipList.getChildren()) {
                        node.setStyle("-fx-background-color: #FFFFFF;");
                    }
                    row.setStyle("-fx-background-color: #EEEEEE;");
                    row.setPrefSize(200, 25);
                    row.setLayoutY(mult * 25);
                    buyShip.setDisable(false);
                    updateShip();
                }
            });
            shipList.getChildren().add(row);
        }
        gadgetPane.setVisible(false);
    }

    // All button handlers below here
    @FXML
    private void backButtonAction(ActionEvent event) {
        parentController.setScreen("UniverseMap");
    }

    @FXML
    private void viewPlayerCardButtonAction(ActionEvent event) {
        // TODO
        //parentController.setScreen("PlayerCard");
    }

    @FXML
    private void shopForPartsButtonAction(ActionEvent event) {
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

    private void initializeGadgets() {
        shipDialogueField.setText("");
        gadgetShipViewer.getChildren().removeAll();
        Rectangle shipPicture = new Rectangle(25, 25, 100, 100);
        shipPicture.setFill(myShip.type.getColor());
        gadgetShipViewer.getChildren().add(shipPicture);

        updatePlayerWeapons();
        updatePlayerShields();
        updatePlayerEngines();
    }

    @FXML
    private void viewWeapons(ActionEvent event) {
        gadgetTypeLabel.setText("Weapons");
        gadgetList.getChildren().clear();
        for (WeaponType type : WeaponType.values()) {
            int mult = type.ordinal();
            HBox row = new HBox();
            Label label = new Label(type.name());
            label.setPrefSize(200, 25);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    currentGadgetType = "Weapon";
                    selectedWeapon = new Weapon(type);
                    resetSelected();
                    row.setStyle("-fx-background-color: #EEEEEE;");
                    row.setPrefSize(200, 25);
                    row.setLayoutY(mult * 25);
                    gadgetNameLabel1.setText("Damage ");
                    gadgetNameLabel2.setText("Rate of Fire");
                    gadgetValueLabel1.setText(Integer.toString(
                            selectedWeapon.getDamage()));
                    gadgetValueLabel2.setText(Integer.toString(
                            selectedWeapon.getRateOfFire()));
                    gadgetCostLabel.setText(Integer.toString(
                            selectedWeapon.getCost()));
                    gadgetPicture.getChildren().clear();
                    Rectangle myGadgetPicture = new Rectangle(100, 10, 100, 100);
                    myGadgetPicture.setFill(selectedWeapon.getColor());
                    gadgetPicture.getChildren().add(myGadgetPicture);
                }
            });
            gadgetList.getChildren().add(row);
        }
    }

    @FXML
    private void viewShields(ActionEvent event) {
        gadgetTypeLabel.setText("Shields");
        gadgetList.getChildren().clear();
        for (ShieldType type : ShieldType.values()) {
            final int mult = type.ordinal();
            final HBox row = new HBox();
            final Label label = new Label(type.name());
            label.setPrefSize(200, 25);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    currentGadgetType = "Shield";
                    selectedShield = new Shield(type);
                    resetSelected();
                    row.setStyle("-fx-background-color: #EEEEEE;");
                    row.setPrefSize(200, 25);
                    row.setLayoutY(mult * 25);
                    gadgetNameLabel1.setText("Strength ");
                    gadgetNameLabel2.setText("Recharge Rate");
                    gadgetValueLabel1.setText(Integer.toString(
                            selectedShield.getStrength()));
                    gadgetValueLabel2.setText(Integer.toString(
                            selectedShield.getRechargeRate()));
                    gadgetCostLabel.setText(Integer.toString(
                            selectedShield.getType().cost));
                    gadgetPicture.getChildren().clear();
                    final Rectangle myGadgetPicture
                            = new Rectangle(100, 10, 100, 100);
                    myGadgetPicture.setFill(
                            selectedShield.getType().color.getColor());
                    gadgetPicture.getChildren().add(myGadgetPicture);
                }
            });
            gadgetList.getChildren().add(row);
        }
    }

    @FXML
    private void viewEngines(ActionEvent event) {
        gadgetTypeLabel.setText("Engines");
        gadgetList.getChildren().clear();
        for (EngineType type : EngineType.values()) {
            final int mult = type.ordinal();
            final HBox row = new HBox();
            final Label label = new Label(type.name());
            label.setPrefSize(200, 25);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            row.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    currentGadgetType = "Engine";
                    selectedEngine = new Engine(type);
                    resetSelected();
                    row.setStyle("-fx-background-color: #EEEEEE;");
                    row.setPrefSize(200, 25);
                    row.setLayoutY(mult * 25);
                    gadgetNameLabel1.setText("Acceleration ");
                    gadgetNameLabel2.setText("Fuel Efficiency");
                    gadgetValueLabel1.setText(Integer.toString(
                            selectedEngine.getAcceleration()));
                    gadgetValueLabel2.setText(Integer.toString(
                            selectedEngine.getFuelEfficiency()));
                    gadgetCostLabel.setText(Integer.toString(
                            selectedEngine.getCost()));
                    gadgetPicture.getChildren().clear();
                    final Rectangle myGadgetPicture
                            = new Rectangle(100, 10, 100, 100);
                    myGadgetPicture.setFill(
                            selectedEngine.getColor());
                    gadgetPicture.getChildren().add(myGadgetPicture);
                }
            });
            gadgetList.getChildren().add(row);
        }
    }

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
                    public void handle(MouseEvent mouseEvent) {
                        currentGadgetType = "My Weapon";
                        selectedWeapon = weapon;
                        resetSelected();
                        row.setStyle("-fx-background-color: #EEEEEE;");
                        gadgetNameLabel1.setText("Damage ");
                        gadgetNameLabel2.setText("Rate of Fire");
                        gadgetValueLabel1.setText(Integer.toString(
                                selectedWeapon.getDamage()));
                        gadgetValueLabel2.setText(Integer.toString(
                                selectedWeapon.getRateOfFire()));
                        gadgetCostLabel.setText(Integer.toString(
                                (selectedWeapon.getCost() / 2)));
                        gadgetPicture.getChildren().clear();
                        final Rectangle myGadgetPicture
                                = new Rectangle(100, 10, 100, 100);
                        myGadgetPicture.setFill(
                                selectedWeapon.getColor());
                        gadgetPicture.getChildren().add(myGadgetPicture);
                        gadgetIndex = row.getParent().
                                getChildrenUnmodifiable().indexOf(row);
                    }
                });
            } else {
                label = new Label("Empty slot");
            }
            label.setPrefSize(200, 25);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            gadgetWeaponsViewer.getChildren().add(row);
        }
    }

    /**
     * 
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
                    public void handle(MouseEvent mouseEvent) {
                        currentGadgetType = "My Shield";
                        selectedShield = shield;
                        resetSelected();
                        row.setStyle("-fx-background-color: #EEEEEE;");
                        gadgetNameLabel1.setText("Strength ");
                        gadgetNameLabel2.setText("Recharge Rate");
                        gadgetValueLabel1.setText(Integer.toString(
                                selectedShield.getStrength()));
                        gadgetValueLabel2.setText(Integer.toString(
                                selectedShield.getRechargeRate()));
                        gadgetCostLabel.setText(Integer.toString(
                                (selectedShield.getType().cost / 2)));
                        gadgetPicture.getChildren().clear();
                        final Rectangle myGadgetPicture
                                = new Rectangle(100, 10, 100, 100);
                        myGadgetPicture.setFill(
                                selectedShield.getType().color.getColor());
                        gadgetPicture.getChildren().add(myGadgetPicture);
                        gadgetIndex = row.getParent()
                                .getChildrenUnmodifiable().indexOf(row);
                    }
                });
            } else {
                label = new Label("Empty slot");
            }
            label.setPrefSize(200, 25);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            gadgetShieldsViewer.getChildren().add(row);
        }
    }

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
                    public void handle(MouseEvent mouseEvent) {
                        currentGadgetType = "My Engine";
                        selectedEngine = engine;
                        resetSelected();
                        row.setStyle("-fx-background-color: #EEEEEE;");
                        gadgetNameLabel1.setText("Acceleration ");
                        gadgetNameLabel2.setText("Fuel Efficiency");
                        gadgetValueLabel1.setText(Integer.toString(
                                selectedEngine.getAcceleration()));
                        gadgetValueLabel2.setText(Integer.toString(
                                selectedEngine.getFuelEfficiency()));
                        gadgetCostLabel.setText(Integer.toString(
                                (selectedEngine.getCost() / 2)));
                        gadgetPicture.getChildren().clear();
                        final Rectangle myGadgetPicture
                                = new Rectangle(100, 10, 100, 100);
                        myGadgetPicture.setFill(
                                selectedEngine.getColor());
                        gadgetPicture.getChildren().add(myGadgetPicture);
                        gadgetIndex = row.getParent()
                                .getChildrenUnmodifiable().indexOf(row);
                    }
                });
            } else {
                label = new Label("Empty slot");
            }
            label.setPrefSize(200, 25);
            label.setAlignment(Pos.CENTER);
            row.getChildren().add(label);
            gadgetEnginesViewer.getChildren().add(row);
        }
    }

    /**
     * 
     */
    private void resetSelected() {
        for (Node node : gadgetList.getChildren()) {
            node.setStyle("-fx-background-color: #FFFFFF;");
        }
        for (Node node : gadgetWeaponsViewer.getChildren()) {
            node.setStyle("-fx-background-color: #FFFFFF;");
        }
        for (Node node : gadgetShieldsViewer.getChildren()) {
            node.setStyle("-fx-background-color: #FFFFFF;");
        }
        for (Node node : gadgetEnginesViewer.getChildren()) {
            node.setStyle("-fx-background-color: #FFFFFF;");
        }
    }

    @FXML
    private void buyShipButtonAction(ActionEvent event) {
        if (confirmationField.getText().trim().equals(
                Integer.toString(otherShip.type.getCost()))) {
            shipDialogueField.setText("");
            confirmationField.setText("");
            buyShip();
            updateFuel();
            updateShip();
        } else {
            shipDialogueField.setText("Please confirm the price of your new ship.");
        }
    }

    @FXML
    private void buyGadgetButtonAction(ActionEvent event) {
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
                        Integer.toString(selectedShield.getType().cost))) {
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
        }
    }

    @FXML
    private void sellGadgetButtonAction(ActionEvent event) {
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
                        + selectedShield.getType().cost / 2);
                player.addMoney(selectedShield.getType().cost / 2);
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
     * Purchases a weapon.
     * Checks to see if the player has enough money for the weapon
     * If so, removes the money from the player and adds the weapon to
     *     the player's ship
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
     * Purchases a shield.
     * Checks to see if the player has enough money for the shield
     * If so, removes the money from the player and adds the shield to
     *     the player's ship  
     */
    public void buyShield() {
        if (player.getMoney() >= selectedShield.getType().cost) {
            player.subtractMoney(selectedShield.getType().cost);
            if (player.getShip().addShield(selectedShield)) {
                shipDialogueField.setText("Purchase complete");
            } else {
                shipDialogueField.setText("Purchase failed, cost refunded");
                player.addMoney(selectedShield.getType().cost);
            }
        } else {
            shipDialogueField.setText("Not enough money");
        }
        updatePlayerShields();
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }

    /**
     * Purchases an engine.
     * Checks to see if the player has enough money for the engine
     * If so, removes the money from the player and adds the engine to
     *     the player's ship
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

    @FXML
    private void confirmFuelButtonAction(ActionEvent event) {
        final int newFuel = (int) (tempFuel - player.getShip().getFuel());
        fuelDialogueField.setText("Purchased " + newFuel + " gallons of fuel.");
        if (ft != null) {
            ft.play();
        }
        player.getShip().addFuel(newFuel);
        player.subtractMoney((int) (newFuel * fuelCost));
        updateFuel();
    }

    @FXML
    private void cancelFuelButtonAction(ActionEvent event) {
        tempFuel = player.getShip().getFuel();
        updateFuel();
    }

    @FXML
    private void fillFuelButtonAction(ActionEvent event) {
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
