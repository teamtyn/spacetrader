package spacetrader;

import spacetrader.market.MarketSetup;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import spacetrader.items.Ship;
import spacetrader.market.TradeGood;
import spacetrader.player.Player;
import spacetrader.star_system.Planet;

/**
 *
 * @author Clayton Kucera
 */
public class MarketController implements Initializable, ControlledScreen {
    @FXML private VBox buyGoodsVBox;
    @FXML private AnchorPane chartPane;
    @FXML private Tab buyTab;
    @FXML private VBox sellGoodsVBox;
    @FXML private Tab sellTab;
    @FXML private Label sellLabel;
    @FXML private Label buyLabel;
    @FXML private Label playerInfo;
    @FXML private Label statusLabel;
    @FXML private Button backButton;

    private ScreensController parentController;
    public static MarketSetup market;   //THIS WILL BE CHANGED ONCE WE HAVE A SINGLETON
    public String[] goods;
    GoodsList buyList;
    GoodsList sellList;
    Player player;
    Ship ship;
    int money = 1000;

    // This is the screen parent method that we must implement
    @Override
    public void setScreenParent(ScreensController parentController) {
           this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        market = new MarketSetup(new Planet());
        //market = SpaceTrader.marketSetup;
        //market = new MarketSetup(SpaceTrader.getPlanet());
        player = new Player();
        ship = player.getShip();
        ship.storeTradeGood(new TradeGood(TradeGood.GoodType.Firearms, 20));
        ship.storeTradeGood(new TradeGood(TradeGood.GoodType.Furs, 20));
        ship.storeTradeGood(new TradeGood(TradeGood.GoodType.Ore, 20));
        updatePlayerInfo();
        setUpGoodsLists();
    }

    public void updatePlayerInfo() {
        playerInfo.setText("Cargo Space Remaining: " + ship.getExtraSpace() + "\nMoney Remaining: " + 
                money + " bitcoins");
    }

    public void display() {
        setUpGoodsLists();
        updatePlayerInfo();
    }

    public void setUpGoodsLists() {
        buyGoodsVBox.getChildren().clear();
        sellGoodsVBox.getChildren().clear();
        ArrayList<TradeGood> buys = market.getBuyable();
        ArrayList<TradeGood> sells = market.getSellable();
        buyList = new GoodsList(buyGoodsVBox, sells, true);
        //sellList = new GoodsList(sellGoodsVBox, sells, false);
        specifyList(buys, ship.getCargo());
        sellList = new GoodsList(sellGoodsVBox, ship.getCargo(), false);
    }

    public void specifyList(ArrayList<TradeGood> marketList, ArrayList<TradeGood> playerList) {
        for (TradeGood tgm: marketList) {
            for (TradeGood tgp: playerList) {
                if (tgm.type == tgp.type) {
                    //tg = new TradeGood(tgm.type, Math.min(tgm.getQuantity(), tgp.getQuantity()));
                    tgp.setPrice(tgm.getPrice());
                }
            }
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        parentController.setScreen("StarMap");
    }

    public static void setMarket(MarketSetup newMarket) {
        market = newMarket;
    }

    public void statusPanelMessage(String status) {
        statusLabel.setText(status);
    }

    public void updateLabels(String name, int price) {
        sellLabel.setText(name + " will sell for " + price + " bitcoins per unit.");
        buyLabel.setText("You can purchase " + name + " for " + price + " bitcoins per unit.");
    }

    public void chart(String name, int price) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        AreaChart<Number,Number> ac = new AreaChart<>(xAxis,yAxis);
        ac.setTitle(name + " prices over last 5 days");

        XYChart.Series series = new XYChart.Series();
        series.setName(name);
        for(int i = 0; i < 4; i++) {
            series.getData().add(new XYChart.Data(i - 5, Math.random() * price * 3 + 1));
        }
        series.getData().add(new XYChart.Data(0, price));

        ac.getData().addAll(series);
        chartPane.getChildren().clear();
        chartPane.getChildren().add(ac);
        ac.setMaxHeight(300);
        ac.setMaxWidth(300);
        ac.setId("chart");
    }

    public void buy(TradeGood good, int amount) {
        int newMoney = money - good.getPrice() * amount;
        if (newMoney > 0) {
        money = money - good.getPrice() * amount;
        ship.storeTradeGood(new TradeGood(good.type, amount));
        market.decreaseQuantity(good, amount);
        }
        display();
    }

    public void sell(TradeGood good, int amount) {
        money = money + good.getPrice() * amount;
        ship.removeTradeGood(new TradeGood(good.type, amount));
        display();
    }

    class BuyButton extends Button {
        public BuyButton(TradeGood good) {
            super("BUY");
            this.setOnAction((ActionEvent event) -> {
                buy(good, 1);
            });
            this.setId("row-button");
        }
    }

    class SellButton extends Button {
        public SellButton(TradeGood good) {
            super("SELL");
            this.setOnAction((ActionEvent event) -> {
            sell(good, 1);
        });
        this.setId("row-button");
     }
 }

    class GoodsRow extends HBox {
        public GoodsRow(TradeGood good, boolean isBuy) {
            this.getChildren().add(new Label(good.type.name));
            this.getChildren().add(new Label("x" + good.getQuantity()));
            if(isBuy) {
                this.getChildren().add(new BuyButton(good));  
            } else {
                this.getChildren().add(new SellButton(good));  
            }
            this.setId("goods-row");
            this.setOnMouseEntered((MouseEvent event) -> {
                chart(good.type.name, good.getPrice());
                updateLabels(good.type.name, good.getPrice());
            });
            this.setAlignment(Pos.CENTER_RIGHT);
            this.setSpacing(30);
        }
    }

    class GoodsList {
        private ArrayList<TradeGood> tradeGoods;
        private ArrayList<String> names;
        private ArrayList<Integer> qtys;
        private ArrayList<Integer> prices;
        int goodsNumber;
        double rowHeight;
        boolean isBuy;
        VBox box;

        public GoodsList(VBox newBox, ArrayList<TradeGood> newGoods, boolean newIsBuy) {
            tradeGoods = newGoods;
            box = newBox;
            goodsNumber = tradeGoods.size();
            isBuy = newIsBuy;
            listGoods();
        }

        private void listGoods() {
            //set up the VBox
            for(int i = 0; i < goodsNumber; i++) {
                TradeGood tg = tradeGoods.get(i);
                if(tg.getPrice() > 0 && tg.getQuantity() > 0) {
                    GoodsRow row = new GoodsRow(tradeGoods.get(i), isBuy);
                    this.addChild(row);
                }
            }
        }

        public void addChild(Node node) {
            box.getChildren().add(node);
        }
    }
}