package spacetrader.player;

import java.util.ArrayList;
import java.util.List;
import spacetrader.GameModel;

/**
 * This class contains names for our mercenaries.
 *
 * @author Clayton
 */
public final class MercenaryNames {

    private static final List<String> NAMES = new ArrayList();

    static {
        NAMES.add("Lucjan");
        NAMES.add("Mikuláš");
        NAMES.add("Sebastion");
        NAMES.add("Witold");
        NAMES.add("Silvester");
        NAMES.add("Urbain");
        NAMES.add("Biorr");
        NAMES.add("Ostrava");
        NAMES.add("Mścisław");
        NAMES.add("Eligiusz");
        NAMES.add("Tomek");
        NAMES.add("Bolek");
        NAMES.add("Zygfryd");
        NAMES.add("Andrzej");
        NAMES.add("Teofil");
        NAMES.add("Miloslav");
        NAMES.add("Borys");
        NAMES.add("Radko");
        NAMES.add("Zbygněv");
        NAMES.add("František");
        NAMES.add("Roman");

    }

    /**
     * Returns a randomly selected String name from the names list.
     *
     * @return the name to be returned
     */
    public static String getName() {
        return NAMES.get(GameModel.getRandom().nextInt(NAMES.size()));
    }
}
