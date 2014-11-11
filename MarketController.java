package spacetrader;

import spacetrader.observer.Observer;
import spacetrader.market.MarketPlace;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import spacetrader.items.CargoBay;
import spacetrader.market.TradeGood;
import spacetrader.player.Player;

/**
 * FXML Controller for the generation of a market.
 * @author Team TYN
 */
public class MarketController implements
        Initializable, ControlledScreen, Observer<ControlledScreen> {

    @FXML
    private VBox buyGoodsVBox;
    @FXML
    private AnchorPane chartPane;
    @FXML
    private VBox sellGoodsVBox;
    @FXML
    private Label cargoLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label dialogueField;
    @FXML
    private Label priceField;

    private ScreensController parentController;
    public static MarketPlace market;
    private FadeTransition ft;
    private GoodsList buyList;
    private GoodsList sellList;
    private Player player;
    private ArrayList<TradeGood> goods;
    private CargoBay cargoBay;
    private final int CHART_DAYS_TO_DISPLAY = 5;
    private final int CHART_RANDOM_PRICE_MULTIPLIER = 3;
    private final int CHART_WIDTH = 300;
    private final int CHART_HEIGHT = 300;

    @Override
    public void setScreenParent(ScreensController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ft = new FadeTransition(Duration.millis(1000), dialogueField);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
    }

    @Override
    public void lazyInitialize() {
        player = GameModel.getPlayer();
        GameModel.getObserverRegistry().registerObserver(this);
        display();
    }

    /*
     * Initializes the market, goods, and cargoBay Maintains
     * the cargo and money labels as well as the good lists.
     */
    public void display() {
        market = player.getPlanet().getMarket();
        goods = market.getGoods();
        cargoBay = player.getShip().getCargoBay();
        cargoLabel.setText(Integer.toString(player.getShip().getExtraSpace()));
        moneyLabel.setText(Integer.toString(player.getMoney()));
        setUpGoodsLists();
    }

    /**
     * Clears the current lists, and then populates the buy and sell tabs.
     */
    public void setUpGoodsLists() {
        buyGoodsVBox.getChildren().clear();
        sellGoodsVBox.getChildren().clear();
        buyList = new GoodsList(buyGoodsVBox, true);
        sellList = new GoodsList(sellGoodsVBox, false);
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        parentController.setScreen("UniverseMap");
    }

    /**
     * Sets up and then updates the past pricing chart.
     *
     * @param name The name of the good the chart applies to
     * @param price The current price of that good
     */
    public void generateChart(String name, int price) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        AreaChart<Number, Number> ac = new AreaChart<>(xAxis, yAxis);
        ac.setTitle(name + " prices over last 5 days");
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < CHART_DAYS_TO_DISPLAY - 1; i++) {
            series.getData().add(new XYChart
                    .Data(i - CHART_DAYS_TO_DISPLAY, Math.random()
                            * price * CHART_RANDOM_PRICE_MULTIPLIER + 1));
        }
        series.getData().add(new XYChart.Data(0, price));
        ac.getData().addAll(series);
        chartPane.getChildren().clear();
        chartPane.getChildren().add(ac);
        ac.setPrefHeight(CHART_HEIGHT);
        ac.setPrefWidth(CHART_WIDTH);
        ac.setId("chart");
    }

    /**
     * Makes a purchase Subtracts the price of the good, stores it in
     * the cargo bay, removes it from the market, then updates the display.
     * If out of cargo space or money, a dialogue is shown on
     * the bottom dock.
     *
     * @param good The good to be purchased
     */
    public void buy(TradeGood good) {
        if (player.getMoney() > good.getPrice()) {
            if (player.getShip().storeTradeGood(good.type.name, 1) > 0) {
                player.setMoney(player.getMoney() - good.getPrice());
                market.changeQuantity(good, -1);
            } else {
                dialogueField.setText("You're out of cargo space!");
                if (ft != null) {
                    ft.play();
                }
            }
        } else {
            dialogueField.setText("You don't have enough money!");
            if (ft != null) {
                ft.play();
            }
        }
        display();
    }

    /**
     * Makes a sale Adds the price of the good, removes it from the
     * cargo bay, adds it to the market, then updates the display.
     *
     * @param good The good to be sold
     */
    public void sell(TradeGood good) {
        if (player.getShip().removeTradeGood(good.type.name, 1) > 0) {
            player.setMoney(player.getMoney() + good.getPrice());
            market.changeQuantity(good, 1);
        }
        display();
    }

    @Override
    public void notifyChange(ControlledScreen changedObject) {
        if (this.equals(changedObject)) {
            display();
        }
    }

    /**
     * Buy Button.
     */
    private class BuyButton extends Button {

        public BuyButton(TradeGood good) {
            super("BUY");
            this.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    buy(good);
                }
            });
            this.setId("row-button");
        }
    }

    /**
     * Sell Button.
     */
    private class SellButton extends Button {

        public SellButton(TradeGood good) {
            super("SELL");
            this.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    sell(good);
                }
            });
            this.setId("row-button");
        }
    }

    /**
     * GoodsRow is a HBox that contains two labels and a buy/sell button.
     */
    private class GoodsRow extends HBox {
        /**
         * Constructor for GoodsRow.
         *
         * @param good the TradeGood to be represented
         * @param isABuyRow whether the represented good can be bought by player
         */
        public GoodsRow(TradeGood good, boolean isABuyRow,
                boolean isDisabled, boolean notAllowedHere) {
            this.getChildren().add(new Label(good.type.name));
            Label quantityLabel;
            Button button;
            if (isABuyRow) {
                quantityLabel = new Label("x" + good.getQuantity());
                button = new BuyButton(good);
            } else {
                quantityLabel = new Label("x"
                        + cargoBay.getGoods().get(good.type.name));
                button = new SellButton(good);
            }
            this.getChildren().add(quantityLabel);
            if (isDisabled) {
                button.setDisable(true);
            }
            this.getChildren().add(button);
            this.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    generateChart(good.type.name, good.getPrice());
                    if (notAllowedHere && isABuyRow) {
                        priceField.setText(good.type.name
                                + " cannot be bought here due to "
                                + player.getPlanet().getName()
                                + "'s tech level being too low.");
                    } else if (notAllowedHere && !isABuyRow) {
                        priceField.setText(good.type.name
                                + " cannot be sold here due to "
                                + player.getPlanet().getName()
                                + "'s tech level being too low.");
                    } else {
                        priceField.setText(good.type.name
                                + " costs "
                                + good.getPrice()
                                + " per unit.");
                    }
                }
            });
            this.setId("goods-row");
            this.setAlignment(Pos.CENTER_RIGHT);
            this.setSpacing(30);
        }
    }

    /**
     * GoodsList is a VBox made up of HBox GoodsRow's.
     */
    private class GoodsList {

        private final VBox vBox;
        private final boolean isABuyList;
        /**
         * GoodsList is a VBox made up of HBox GoodsRow's.
         *
         * @param vBox the VBox that is to be populated with RowBoxes
         * @param isABuyList whether the list has goods the player can buy
         */
        public GoodsList(VBox vBox, boolean isABuyList) {
            this.vBox = vBox;
            this.isABuyList = isABuyList;
            listGoods();
        }
        /**
        * Populate the VBox with GoodsRows.
        */
        public final void listGoods() {
            for (TradeGood good : goods) {
                boolean isDisabled = false;
                boolean notAllowedHere = false;
                if (good.getPrice() <= 0) {
                    isDisabled = true;
                }
                if (isABuyList && good.getQuantity() <= 0) {
                    isDisabled = true;
                }
                if (!isABuyList && cargoBay.getGoods()
                        .get(good.type.name) <= 0) {
                    isDisabled = true;
                }
                if (isABuyList && !market.getBuyable().contains(good)) {
                    isDisabled = true;
                    notAllowedHere = true;
                }
                if (!isABuyList && !market.getSellable().contains(good)) {
                    isDisabled = true;
                    notAllowedHere = true;
                }
                GoodsRow row = new GoodsRow(good, isABuyList,
                        isDisabled, notAllowedHere);
                this.addChild(row);
            }
        }
        /**
        * Add the node to the VBox as a child.
        */
        public void addChild(Node node) {
            vBox.getChildren().add(node);
        }
    }
}
