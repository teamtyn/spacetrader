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

    /**
     * 
     */
    @FXML
    private VBox buyGoodsVBox;
    /**
     * 
     */
    @FXML
    private AnchorPane chartPane;
    /**
     * 
     */
    @FXML
    private VBox sellGoodsVBox;
    /**
     * 
     */
    @FXML
    private Label cargoLabel;
    /**
     * 
     */
    @FXML
    private Label moneyLabel;
    /**
     * 
     */
    @FXML
    private Label dialogueField;
    /**
     * 
     */
    @FXML
    private Label priceField;

    /**
     * 
     */
    private ScreensController parentController;
    /**
     * 
     */
    public static MarketPlace market;
    /**
     * 
     */
    private FadeTransition ft;
    /**
     * 
     */
    private GoodsList buyList;
    /**
     * 
     */
    private GoodsList sellList;
    /**
     * 
     */
    private Player player;
    /**
     * 
     */
    private ArrayList<TradeGood> goods;
    /**
     * 
     */
    private CargoBay cargoBay;

    /**
     * The number of days to show on the chart of the market GUI.
     */
    private static final int CHART_DAYS_TO_DISPLAY = 5;
    /**
     * 
     */
    private static final int CHART_RANDOM_PRICE_MULTIPLIER = 3;
    /**
     * The width of the chart of the market GUI.
     */
    private static final int CHART_WIDTH = 300;
    /**
     * The height of the chart of the market GUI.
     */
    private static final int CHART_HEIGHT = 300;
    /**
     * The duration of the fade transition in milliseconds.
     */
    private static final int FT_DURATION = 1000;

    @Override
    public final void setScreenParent(
            final ScreensController aParentController) {
        parentController = aParentController;
    }

    @Override
    public final void initialize(final URL url, final ResourceBundle rBundle) {
        ft = new FadeTransition(Duration.millis(FT_DURATION), dialogueField);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
    }

    @Override
    public final void lazyInitialize() {
        player = GameModel.getPlayer();
        GameModel.getObserverRegistry().registerObserver(this);
        display();
    }

    /**
     * Initializes the market, goods, and cargoBay.
     *     Maintains the cargo and money labels as well as the good lists
     */
    public final void display() {
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
    public final void setUpGoodsLists() {
        buyGoodsVBox.getChildren().clear();
        sellGoodsVBox.getChildren().clear();
        buyList = new GoodsList(buyGoodsVBox, true);
        sellList = new GoodsList(sellGoodsVBox, false);
    }

    /**
     * Defines the back button's behavior.
     * @param event The back button being pushed
     */
    @FXML
    private void backButtonAction(final ActionEvent event) {
        parentController.setScreen("UniverseMap");
    }

    /**
     * Sets up and then updates the past pricing chart.
     * @param name The name of the good the chart applies to
     * @param price The current price of that good
     */
    public final void generateChart(final String name, final int price) {
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
     * Makes a purchase.
     * Subtracts the price of the good, stores it in the cargo bay,
     *     removes it from the market, then updates the display
     * If out of cargo space or money, a dialogue is shown on the bottom dock
     * @param good The good to be purchased
     */
    public final void buy(final TradeGood good) {
        if (player.getMoney() > good.getPrice()) {
            if (player.getShip().storeTradeGood(good.getType().name, 1) > 0) {
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
     * Makes a sale.
     * Adds the price of the good, removes it from the
     *     cargo bay, adds it to the market, then updates the display
     * @param good The good to be sold
     */
    public final void sell(final TradeGood good) {
        if (player.getShip().removeTradeGood(good.getType().name, 1) > 0) {
            player.setMoney(player.getMoney() + good.getPrice());
            market.changeQuantity(good, 1);
        }
        display();
    }

    @Override
    public final void notifyChange(final ControlledScreen changedObject) {
        if (this.equals(changedObject)) {
            display();
        }
    }

    /**
     * Buy Button.
     */
    private class BuyButton extends Button {

        /**
         * Constructor for a buy button which sets up how it will react to
         *     being pushed.
         * @param good The good to be bought
         */
        public BuyButton(final TradeGood good) {
            super("BUY");
            this.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(final ActionEvent event) {
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

        /**
         * Constructor for a sell button which sets up how it will react to
         *     being pushed.
         * @param good The good to be sold
         */
        public SellButton(final TradeGood good) {
            super("SELL");
            this.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(final ActionEvent event) {
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
         * The spacing for goods rows.
         */
        private static final int GOODSROW_SPACING = 30;

        /**
         * Constructor for GoodsRow.
         * @param good The TradeGood to be represented
         * @param isABuyRow Whether the represented good can be bought by player
         * @param isDisabled Whether this good is disabled in this situation
         * @param notAllowedHere Whether this good is allowed on this planet
         */
        public GoodsRow(final TradeGood good, final boolean isABuyRow,
                final boolean isDisabled, final boolean notAllowedHere) {
            this.getChildren().add(new Label(good.getType().name));
            Label quantityLabel;
            Button button;
            if (isABuyRow) {
                quantityLabel = new Label("x" + good.getQuantity());
                button = new BuyButton(good);
            } else {
                quantityLabel = new Label("x"
                        + cargoBay.getGoods().get(good.getType().name));
                button = new SellButton(good);
            }
            this.getChildren().add(quantityLabel);
            if (isDisabled) {
                button.setDisable(true);
            }
            this.getChildren().add(button);
            this.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(final MouseEvent event) {
                    generateChart(good.getType().name, good.getPrice());
                    if (notAllowedHere && isABuyRow) {
                        priceField.setText(good.getType().name
                                + " cannot be bought here due to "
                                + player.getPlanet().getName()
                                + "'s tech level being too low.");
                    } else if (notAllowedHere && !isABuyRow) {
                        priceField.setText(good.getType().name
                                + " cannot be sold here due to "
                                + player.getPlanet().getName()
                                + "'s tech level being too low.");
                    } else {
                        priceField.setText(good.getType().name
                                + " costs "
                                + good.getPrice()
                                + " per unit.");
                    }
                }
            });
            this.setId("goods-row");
            this.setAlignment(Pos.CENTER_RIGHT);
            this.setSpacing(GOODSROW_SPACING);
        }
    }

    /**
     * GoodsList is a VBox made up of HBox GoodsRow's.
     */
    private class GoodsList {
        /**
         * The VBox which will house the good list's contents.
         */
        private final VBox vBox;
        /**
         * Whether or not this good list is to represent buyable items.
         */
        private final boolean isABuyList;
        /**
         * GoodsList is a VBox made up of HBox GoodsRows.
         * @param aVBox the VBox that is to be populated with RowBoxes
         * @param thisIsABuyList whether the list has goods the player can buy
         */
        public GoodsList(final VBox aVBox, final boolean thisIsABuyList) {
            vBox = aVBox;
            isABuyList = thisIsABuyList;
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
                        .get(good.getType().name) <= 0) {
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
        * @param node The node to be added to the VBox
        */
        public void addChild(final Node node) {
            vBox.getChildren().add(node);
        }
    }
}
