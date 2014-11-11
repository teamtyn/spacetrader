package spacetrader.market;

import java.io.Serializable;
import java.util.ArrayList;
import spacetrader.star_system.Planet;

/**
 * MarketSetup has three lists, goods, buyable, and sellable.
 * @author Team TYN
 */
public class MarketPlace implements Serializable {
    /**
     * All the goods in the game, with prices based on the planet passed in.
     */
    private final ArrayList<TradeGood> goods;
    /**
     * All the goods that are allowed to be bought here (via tech level).
     */
    private final ArrayList<TradeGood> buyable;
    /**
     * All the goods that are allowed to be sold here (via tech level).
     */
    private final ArrayList<TradeGood> sellable;

    /**
     * Initializes the three lists based on the correct planet.
     * @param planet The planet that that market is on
     */
    public MarketPlace(final Planet planet) {
        goods = new ArrayList<>();
        buyable = new ArrayList<>();
        sellable = new ArrayList<>();
        for (TradeGood.GoodType type : TradeGood.GoodType.values()) {
            TradeGood newGood = new TradeGood(type, planet);
            goods.add(newGood);
            if (type.mtlp <= planet.getTechLevelOrdinality()) {
                buyable.add(newGood);
            }
            if (type.mtlu <= planet.getTechLevelOrdinality()) {
                sellable.add(newGood);
            }
        }
    }

    /**
     * Gets the goods of the market place.
     * @return The goods of the market place
     */
    public final ArrayList<TradeGood> getGoods() {
        return goods;
    }

    /**
     * Gets the buyable goods of the market place.
     * @return The buyable goods of the market place
     */
    public final ArrayList<TradeGood> getBuyable() {
        return buyable;
    }

    /**
     * Gets the sellable goods of the market place.
     * @return The sellable goods of the market place
     */
    public final ArrayList<TradeGood> getSellable() {
        return sellable;
    }

    /**
     * Works as both the increment and decrement functions.
     * @param good The good to be changed
     * @param change The change in amount of the good
     */
    public final void changeQuantity(final TradeGood good, final int change) {
        for (TradeGood tg : goods) {
            if (tg.type == good.type) {
                tg.setQuantity(tg.getQuantity() + change);
            }
        }
    }

    @Override
    public final String toString() {
        StringBuilder str = new StringBuilder();
        for (TradeGood good : goods) {
            str.append(good.type);
            str.append(": ");
            str.append(good.getPrice());
            str.append("\n");
        }
        return str.toString();
    }
}
