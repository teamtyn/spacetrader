package spacetrader.market;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import spacetrader.GameModel;
import spacetrader.star_system.Government;
import spacetrader.star_system.Planet;

/**
 * TechLevel -- {PREAGRICULTURAL [0], AGRICULTURAL [1], MEDIEVAL [2],
 * RENAISSANCE [3], EARLYINDUSTRIAL [4], INDUSTRIAL [5],
 * POSTINDUSTRIAL [6],HIGHTECH [7]};
 *
 * ResourceLevel -- {NOSPECIALRESOURCES [0], MINERALRICH [1], MINERALPOOR [2],
 * DESERT [3], LOTSOFWATER [4], RICHSOIL [5], POORSOIL [6], RICHFAUNA [7],
 * LIFELESS [8], WEIRDMUSHROOMS [9], LOTSOFHERBS [10],
 * ARTISTIC [11], WARLIKE [12]};
 *
 * Circumstance -- {NONE [0], DROUGHT [1], COLD [2], CROPFAIL [3], WAR [4],
 * BOREDOM [5], PLAGUE [6], LACKOFWORKERS [7]};
 *
 * If IE, CR, or ER == -1, it means that condition is never present
 * @author Team TYN
 */
public class TradeGood implements Serializable {
    /**
     * The price multiplier if the ER condition is met.
     */
    private static final double ER_MULTIPLIER = 1.25;
    /**
     * The price multiplier if the CR condition is met.
     */
    private static final double CR_MULTIPLIER = .75;
    /**
     * The price multiplier if the IE condition is met.
     */
    private static final double IE_MULTIPLIER = 1.5;
    /**
     * The global quantity for all goods.
     */
    private static final int GLOBAL_QUANTITY = 10;

    /**
     * The type of the good (from the enum).
     */
    private final GoodType type;
    /**
     * The planet the good is on.
     */
    private final Planet planet;
    /**
     * The price of the good.
     */
    private int price;
    /**
     * The quantity of the good.
     */
    private int quantity;
    /**
     * Price multipliers from government type of the planet.
     */
    private static final Map<Government.Type, Double> GOVERNMENT_PRICE;

    static {
        Map<Government.Type, Double> govPrice = new HashMap<>();
        govPrice.put(Government.Type.ANARCHY, 1.5);
        govPrice.put(Government.Type.ARISTOCRACY, 1.0);
        govPrice.put(Government.Type.CAPITALIST, 1.0);
        govPrice.put(Government.Type.COMMUNIST, 1.25);
        govPrice.put(Government.Type.CORPORATE, 1.0);
        govPrice.put(Government.Type.DEMOCRACY, 1.0);
        govPrice.put(Government.Type.FASCIST, 1.0);
        govPrice.put(Government.Type.MERITOCRACY, .75);
        govPrice.put(Government.Type.MONARCHY, 1.0);
        govPrice.put(Government.Type.OLIGARCHY, 1.0);
        govPrice.put(Government.Type.TECHNOCRACY, .5);
        govPrice.put(Government.Type.THEOCRACY, 1.0);
        GOVERNMENT_PRICE = Collections.unmodifiableMap(govPrice);
    }

    /**
     * The set of all predefined types of goods available in the universe.
     */
    public enum GoodType {
        /**
         * The good type of water.
         */
        Water(0, 0, 2, 30, 10, 3, 4, 1, 4, 3, 30, 500, "Water"),
        /**
         * The good type of furs.
         */
        Furs(0, 0, 0, 250, 10, 10, 10, 2, 7, 8, 230, 280, "Furs"),
        /**
         * The good type of food.
         */
        Food(1, 0, 1, 100, 10, 5, 5, 3, 5, 6, 90, 160, "Food"),
        /**
         * The good type of ore.
         */
        Ore(2, 2, 3, 350, 10, 20, 10, 4, 1, 2, 350, 420, "Ore"),
        /**
         * The good type of games.
         */
        Games(3, 1, 6, 250, 10, -10, 5, 5, 11, -1, 160, 270, "Games"),
        /**
         * The good type of firearms.
         */
        Firearms(3, 1, 5, 1250, 10, -75, 100, 4, 12, -1, 600, 1100, "Firearms"),
        /**
         * The good type of medicine.
         */
        Medicine(4, 1, 6, 650, 10, -20, 10, 6, 10, -1, 400, 700, "Medicine"),
        /**
         * The good type of machines.
         */
        Machines(4, 3, 5, 900, 10, -30, 5, 7, -1, -1, 600, 800, "Machines"),
        /**
         * The good type of narcotics.
         */
        Narcotics(5, 0, 5, 3500, 10, -125, 150, 5, 9, -1, 2000, 3000,
                "Narcotics"),
        /**
         * The good type of robots.
         */
        Robots(6, 4, 7, 5000, 10, -150, 100, 7, -1, -1, 3500, 5000, "Robots");

        /**
         * Minimum Tech Level to produce this good
         *     (You can't buy on planets below this level).
         */
        public int mtlp;
        /**
         * Minimum Tech Level to use this good
         *     (You can't sell on planets below this level).
         */
        public int mtlu;
        /**
         * Tech Level which produces the most of this good.
         */
        public int ttp;
        /**
         * The base price of the good
         */
        public int basePrice;
        /**
         * The base quantity of the good.
         */
        public int baseQuantity;
        /**
         * Price increase per tech level.
         */
        public int ipl;
        /**
         * Maximum percentage that the price can vary above or below the base.
         */
        public int var;
        /**
         * Radical price increase event, when this event happens on a planet,
         *     the price may increase astronomically.
         */
        public int ie;
        /**
         * When this condition is present, the good is cheap.
         */
        public int cr;
        /**
         * When this condition is present, the good is expensive.
         */
        public int er;
        /**
         * Minimum price offered in space trade with random trader
         *     (not on a planet).
         */
        public int mtl;
        /**
         * Maximum price offered in space trade with random trader
         *     (not on a planet).
         */
        public int mhl;
        /**
         * The name of the good.
         */
        public String name;

        /**
         * Constructor for the predefined good types.
         * @param amtlp MTLP for this good type.
         * @param amtlu MTLU for this good type.
         * @param attp TTP for this good type.
         * @param abasePrice Base price for this good type.
         * @param abaseQuantity Base quantity for this good type.
         * @param aipl IPL for this good type.
         * @param avar VAR for this good type.
         * @param aie IE for this good type.
         * @param acr CR for this good type.
         * @param aer ER for this good type.
         * @param amtl MTL for this good type.
         * @param amhl MHL for this good type.
         * @param aname Name for this good type.
         */
        GoodType(final int amtlp, final int amtlu, final int attp,
                final int abasePrice, final int abaseQuantity, final int aipl,
                final int avar, final int aie, final int acr, final int aer,
                final int amtl, final int amhl, final String aname) {
            mtlp = amtlp;
            mtlu = amtlu;
            ttp = attp;
            basePrice = abasePrice;
            baseQuantity = abaseQuantity;
            ipl = aipl;
            var = 1 + (((GameModel.getRandom().nextInt((2 * avar) + 1))
                    - avar) / 100);
            ie = aie;
            cr = acr;
            er = aer;
            mtl = amtl;
            mhl = amhl;
            name = aname;
        }
    };

    /**
     * Creates a trade good and calculates its price and quantity.
     * @param aType A good type to create
     * @param aPlanet A planet to create the good on
     */
    public TradeGood(final GoodType aType, final Planet aPlanet) {
        type = aType;
        planet = aPlanet;
        price = calcPrice();
        quantity = calcQuantity();
    }

    /**
     * Calculates the price of the good.
     * @return The price of this good in this specific situation
     */
    public final int calcPrice() {
        double thisPrice = (type.basePrice + (type.ipl
                * (planet.getTechLevelOrdinality() - type.mtlp))) * type.var;
        if (type.cr == planet.getResourceLevelOrdinality()) {
            thisPrice *= CR_MULTIPLIER;
        }
        if (type.er == planet.getResourceLevelOrdinality()) {
            thisPrice *= ER_MULTIPLIER;
        }
        if (type.ie == planet.getCircumstance().getOrdinality()) {
            thisPrice *= IE_MULTIPLIER;
        }
        thisPrice *= GOVERNMENT_PRICE.get(planet.getGovernment().getType());
        return (int) thisPrice;
    }

    /**
     * Converts from a good name to a good type.
     * @param name The name to be translated to a good type
     * @return The GoodType associated with this name
     */
    public static GoodType fromNameToType(final String name) {
        GoodType found = null;
        for (GoodType type : GoodType.values()) {
            if (type.name.equals(name)) {
                found = type;
            }
        }
        return found;
    }

    // TODO: Allow different quantities upon initialization
    /**
     * Quantity is currently predicated on the government multipliers (all 1),
     *     and a simplistic stab at supply and demand from the price ratio.
     * @return The quantity of this good available in this specific situation
     */
    public final int calcQuantity() {
        //int ratio = getPrice() / type.basePrice;
        //int thisQuantity = type.baseQuantity / ratio;
        //thisQuantity *= govQuantity.get(planet.getGovernment().getType());
        //return thisQuantity;
        return GLOBAL_QUANTITY;
    }

    /**
     * Gets the price of the good.
     * @return The price of the good
     */
    public final int getPrice() {
        return price;
    }

    /**
     * Gets the quantity of the good.
     * @return The quantity of the good
     */
    public final int getQuantity() {
        return quantity;
    }

    /**
     * Gets the type of the good.
     * @return The type of the good
     */
    public final GoodType getType() {
        return type;
    }

    /**
     * Sets the quantity of the good.
     * @param newQuantity The new quantity of the good
     */
    public final void setQuantity(final int newQuantity) {
        quantity = newQuantity;

    }

    /**
     * Sets the price of the good.
     * @param newPrice The new price of the good
     */
    public final void setPrice(final int newPrice) {
        price = newPrice;
    }

    @Override
    public final String toString() {
        return this.type.name + " " + quantity;
    }
}
