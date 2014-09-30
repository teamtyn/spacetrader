package spacetrader.market;

import java.util.ArrayList;
import spacetrader.star_system.Planet;

/**
 * 
 * @author Ryan Burns
 */
public class MarketSetup {
    private ArrayList<TradeGood> goods;
    private ArrayList<TradeGood> buyable;
    private ArrayList<TradeGood> sellable;
    private final Planet planet;

    public MarketSetup(Planet planet) {
        this.planet = planet;
        goods = new ArrayList<>();
        buyable = new ArrayList<>();
        sellable = new ArrayList<>();
        for (TradeGood.GoodType type: TradeGood.GoodType.values()) {
            goods.add(new TradeGood(type, planet));
        }
        for (TradeGood good: goods) {
            if (good.type.mtlp <= planet.getTechLevelOrdinality()) {
                buyable.add(good);
            }
            if (good.type.mtlu <= planet.getTechLevelOrdinality()) {
                sellable.add(good);
            }
        }
    }

    public ArrayList<TradeGood> getGoods() {
        return goods;
    }
    
    public ArrayList<TradeGood> getBuyable() {
        return buyable;
    }

    public ArrayList<TradeGood> getSellable() {
        return sellable;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (TradeGood good: goods) {
            str.append(good.type);
            str.append(": ");
            str.append(good.getPrice());
            str.append("\n");
        }
        return str.toString();
    }
}