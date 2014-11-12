package spacetrader.items;

import java.io.Serializable;
import java.util.HashMap;
import spacetrader.market.TradeGood;

/**
 * A CargoBay is defined by its capacity, current size,
 *     and contents (which are TradeGoods).
 * @author Team TYN
 */
public class CargoBay implements Serializable {
    /**
     * The capacity of the cargo bay.
     */
    private int capacity;
    /**
     * The current size of the cargo bay's contents.
     */
    private int currentSize;
    /**
     * The goods contained in the cargo bay.
     */
    private final HashMap<String, Integer> goods;

    /**
     * Constructor for a cargo bay.
     * @param aCapacity The capacity of the cargo bay
     */
    public CargoBay(final int aCapacity) {
        capacity = aCapacity;
        goods = new HashMap<>(TradeGood.GoodType.values().length);
        setUpMap();
    }

    /**
     * Initializes the quantities in the cargo bay to zero.
     */
    public final void setUpMap() {
        goods.put("Water", 0);
        goods.put("Furs", 0);
        goods.put("Food", 0);
        goods.put("Ore", 0);
        goods.put("Games", 0);
        goods.put("Firearms", 0);
        goods.put("Medicine", 0);
        goods.put("Machines", 0);
        goods.put("Narcotics", 0);
        goods.put("Robots", 0);
    }

    /**
     * Add as many goods as possible up to specified quantity.
     * @param goodName The good to be stored in the cargo bay
     * @param quantityRequested The quantity to be ideally
     *     stored in the cargo bay
     * @return The number of goods added
     */
    public int addTradeGood(String goodName, int quantityRequested) {
        int quantity = quantityRequested;
        if (quantity > 0) {
            if ((quantity + currentSize) > capacity) {
                quantity = capacity - currentSize;
            }
            if (quantity > 0) {
                currentSize += quantity;
                int oldNum = goods.get(goodName);
                goods.replace(goodName, oldNum + quantity);
            }
        } else {
            quantity = 0;
        }
        return quantity;
    }

    /**
     * Remove as many goods as possible up to specified quantity.
     * @param goodName The good to be removed from the cargo bay
     * @param quantity The quantity to be ideally removed from the cargo bay
     * @return The number of goods removed
     */
    public int removeTradeGood(final String goodName, int quantity) {
        if (quantity > goods.get(goodName)) {
            quantity = goods.get(goodName);
        }
        currentSize -= quantity;
        goods.replace(goodName, goods.get(goodName) - quantity);
        return quantity;
    }

    /**
     * Getter for the goods of the cargo bay.
     * @return The goods of the cargo bay
     */
    public final HashMap<String, Integer> getGoods() {
        return goods;
    }

    /**
     * Getter for the capacity of the cargo bay.
     * @return The capacity of the cargo bay
     */
    public final int getCapacity() {
        return capacity;
    }

    /**
     * Getter for the current size of the cargo bay.
     * @return The current size of the cargo bay
     */
    public final int getCurrentSize() {
        return currentSize;
    }

    /**
     * Only to be used as a convenience when player upgrades their cargo bay.
     * @param aCapacity The new capacity of the cargo bay
     */
    public void setCapacity(final int aCapacity) {
        capacity = aCapacity;
    }

    @Override
    public final String toString() {
        final StringBuilder str = new StringBuilder("Cargo bay contents: \n");
        for (String goodName : goods.keySet()) {
            str.append(goodName);
            str.append(": ");
            str.append(goods.get(goodName));
            str.append("\n");
        }
        return str.toString();
    }
}
