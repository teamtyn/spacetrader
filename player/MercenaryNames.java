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

    private static final List<String> names = new ArrayList();

    static {
        names.add("Lucjan");
        names.add("Mikuláš");
        names.add("Sebastion");
        names.add("Witold");
        names.add("Silvester");
        names.add("Urbain");
        names.add("Biorr");
        names.add("Ostrava");
        names.add("Mścisław");
        names.add("Eligiusz");
        names.add("Tomek");
        names.add("Bolek");
        names.add("Zygfryd");
        names.add("Andrzej");
        names.add("Teofil");
        names.add("Miloslav");
        names.add("Borys");
        names.add("Radko");
        names.add("Zbygněv");
        names.add("František");
        names.add("Roman");

    }

    /**
     * Returns a randomly selected String name from the names list.
     *
     * @return the name to be returned
     */
    public static String getName() {
        return names.get(GameModel.getRandom().nextInt(names.size()));
    }
}
