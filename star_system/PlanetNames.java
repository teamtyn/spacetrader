package spacetrader.star_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import spacetrader.GameModel;

/**
 * Generates planet names based on their original government.
 *
 * @author Team TYN
 */
public final class PlanetNames {

    /**
     * A list of appropriate names, by type of government.
     */
    private static final EnumMap<Government.Type, List<String>> NAME_LISTS
            = new EnumMap<>(Government.Type.class);

    /**
     * The maximum number of names to generate for anarchic governments.
     */
    private static final int ANARCHY_NAME_COUNT = 200;
    /**
     * The size of the alphabet to use for the names of anarchic systems.
     */
    private static final int ANARCHY_NAME_BASE = 26;
    /**
     * The size of the space for the names of anarchic systems, 26 ** 6.
     */
    private static final int ANARCHY_NAME_MAX = 308915776;

    static {
        List<String> anarchicNames = new ArrayList<>();
        for (int count = 0; count < ANARCHY_NAME_COUNT; count++) {
            int nameInt = GameModel.getRandom().nextInt(ANARCHY_NAME_MAX);
            char[] nameChars = Integer
                    .toString(nameInt, ANARCHY_NAME_BASE).toCharArray();
            for (int i = 0; i < nameChars.length; i++) {
                if (nameChars[i] <= '9') {
                    nameChars[i] += 'a' - '0';
                } else {
                    nameChars[i] += 'k' - 'a';
                }
            }
            anarchicNames.add(new String(nameChars));
        }
        NAME_LISTS.put(Government.Type.ANARCHY, anarchicNames);

        List<String> capitalistCorporateNames = new ArrayList<>();
        capitalistCorporateNames.add("Abbott");
        capitalistCorporateNames.add("Actavis");
        capitalistCorporateNames.add("Aetna");
        capitalistCorporateNames.add("Agilent");
        capitalistCorporateNames.add("Alexion");
        capitalistCorporateNames.add("Allergan");
        capitalistCorporateNames.add("Amgen");
        capitalistCorporateNames.add("Bard");
        capitalistCorporateNames.add("Baxter");
        capitalistCorporateNames.add("Becton");
        capitalistCorporateNames.add("Bergen");
        capitalistCorporateNames.add("Boston");
        capitalistCorporateNames.add("Bristol");
        capitalistCorporateNames.add("Cardinal");
        capitalistCorporateNames.add("Celgene");
        capitalistCorporateNames.add("Cerner");
        capitalistCorporateNames.add("Cigna");
        capitalistCorporateNames.add("Covidien");
        capitalistCorporateNames.add("DaVita");
        capitalistCorporateNames.add("Edwards");
        capitalistCorporateNames.add("Gilead");
        capitalistCorporateNames.add("Hospira");
        capitalistCorporateNames.add("Humana");
        capitalistCorporateNames.add("Johnson");
        capitalistCorporateNames.add("Lilly");
        capitalistCorporateNames.add("McKesson");
        capitalistCorporateNames.add("Merck");
        capitalistCorporateNames.add("Mylan");
        capitalistCorporateNames.add("Patterson");
        capitalistCorporateNames.add("Perkin");
        capitalistCorporateNames.add("Perrigo");
        capitalistCorporateNames.add("Pfizer");
        capitalistCorporateNames.add("Quest");
        capitalistCorporateNames.add("Stryker");
        capitalistCorporateNames.add("Tenet");
        capitalistCorporateNames.add("Varian");
        capitalistCorporateNames.add("Vertex");
        capitalistCorporateNames.add("Waters");
        capitalistCorporateNames.add("Zimmer");
        capitalistCorporateNames.add("Zoetis");
        Collections.shuffle(capitalistCorporateNames, GameModel.getRandom());
        NAME_LISTS.put(Government.Type.CAPITALIST, capitalistCorporateNames);
        NAME_LISTS.put(Government.Type.CORPORATE, capitalistCorporateNames);
    }

    /**
     * @param government the type of government for which to get a name
     * @return an appropriate name for a planet with the given government
     */
    public static String getName(final Government government) {
        Government.Type governmentType = government.getType();
        while (NAME_LISTS.get(governmentType) == null
                || NAME_LISTS.get(governmentType).isEmpty()) {
            governmentType = Government.Type.values()[GameModel
                    .getRandom().nextInt(Government.Type.values().length)];
        }
        return NAME_LISTS.get(governmentType).remove(0);
    }

    /**
     * Prevent instantiation.
     */
    private PlanetNames() {
    }
}
