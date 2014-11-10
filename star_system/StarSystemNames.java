package spacetrader.star_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Basically the standard set of solar system names, we currently have 125.
 * @author Team TYN
 */
public class StarSystemNames {
    private static final List<String> NAME_LIST = new ArrayList<>();
    static {
        NAME_LIST.add("Acamar");
        NAME_LIST.add("Adahn");
        NAME_LIST.add("Aldea");
        NAME_LIST.add("Andevian");
        NAME_LIST.add("Antedi");
        NAME_LIST.add("Balosnee");
        NAME_LIST.add("Baratas");
        NAME_LIST.add("Brax");
        NAME_LIST.add("Bretel");
        NAME_LIST.add("Burnsea");
        NAME_LIST.add("Calondia");
        NAME_LIST.add("Campor");
        NAME_LIST.add("Capelle");
        NAME_LIST.add("Carzon");
        NAME_LIST.add("Castor");
        NAME_LIST.add("Cestus");
        NAME_LIST.add("Cheron");
        NAME_LIST.add("Courteney");
        NAME_LIST.add("Daled");
        NAME_LIST.add("Damast");
        NAME_LIST.add("Davlos");
        NAME_LIST.add("Deneb");
        NAME_LIST.add("Deneva");
        NAME_LIST.add("Devidia");
        NAME_LIST.add("Dotsonia");
        NAME_LIST.add("Draylon");
        NAME_LIST.add("Drema");
        NAME_LIST.add("Endor");
        NAME_LIST.add("Esmee");
        NAME_LIST.add("Exo");
        NAME_LIST.add("Ferris");
        NAME_LIST.add("Festen");
        NAME_LIST.add("Fourmi");
        NAME_LIST.add("Frolix");
        NAME_LIST.add("Gemulon");
        NAME_LIST.add("Guinifer");
        NAME_LIST.add("Hades");
        NAME_LIST.add("Hamlet");
        NAME_LIST.add("Helena");
        NAME_LIST.add("Hulst");
        NAME_LIST.add("Iodine");
        NAME_LIST.add("Iralius");
        NAME_LIST.add("Janus");
        NAME_LIST.add("Japori");
        NAME_LIST.add("Jarada");
        NAME_LIST.add("Jason");
        NAME_LIST.add("Kaylon");
        NAME_LIST.add("Khefka");
        NAME_LIST.add("Kira");
        NAME_LIST.add("Klaatu");
        NAME_LIST.add("Klaestron");
        NAME_LIST.add("Korma");
        NAME_LIST.add("Kravat");
        NAME_LIST.add("Krios");
        NAME_LIST.add("Kuceraville");
        NAME_LIST.add("Laertes");
        NAME_LIST.add("Largo");
        NAME_LIST.add("Lave");
        NAME_LIST.add("Ligon");
        NAME_LIST.add("Lowry");
        NAME_LIST.add("Magrat");
        NAME_LIST.add("Malcoria");
        NAME_LIST.add("Melina");
        NAME_LIST.add("Mentar");
        NAME_LIST.add("Merik");
        NAME_LIST.add("Mintaka");
        NAME_LIST.add("Montor");
        NAME_LIST.add("Mordan");
        NAME_LIST.add("Myrthe");
        NAME_LIST.add("Nelvana");
        NAME_LIST.add("New de Leon");
        NAME_LIST.add("Nix");
        NAME_LIST.add("Nyle");
        NAME_LIST.add("Odet");
        NAME_LIST.add("Og");
        NAME_LIST.add("Omega");
        NAME_LIST.add("Omphalos");
        NAME_LIST.add("Orias");
        NAME_LIST.add("Othello");
        NAME_LIST.add("Parade");
        NAME_LIST.add("Penthara");
        NAME_LIST.add("Picard");
        NAME_LIST.add("Pollux");
        NAME_LIST.add("Purcelloria");
        NAME_LIST.add("Quator");
        NAME_LIST.add("Rakhar");
        NAME_LIST.add("Ran");
        NAME_LIST.add("Regulas");
        NAME_LIST.add("Relva");
        NAME_LIST.add("Rhymus");
        NAME_LIST.add("Rochani");
        NAME_LIST.add("Rubicum");
        NAME_LIST.add("Rutia");
        NAME_LIST.add("Sarpeidon");
        NAME_LIST.add("Sefalla");
        NAME_LIST.add("Seltrice");
        NAME_LIST.add("Sigma");
        NAME_LIST.add("Sol");
        NAME_LIST.add("Somari");
        NAME_LIST.add("Stakoron");
        NAME_LIST.add("Styris");
        NAME_LIST.add("Talani");
        NAME_LIST.add("Tamus");
        NAME_LIST.add("Tantalos");
        NAME_LIST.add("Tanuga");
        NAME_LIST.add("Tarchannen");
        NAME_LIST.add("Terosa");
        NAME_LIST.add("Thera");
        NAME_LIST.add("Titan");
        NAME_LIST.add("Torin");
        NAME_LIST.add("Triacus");
        NAME_LIST.add("Turkana");
        NAME_LIST.add("Tyrus");
        NAME_LIST.add("Umberlee");
        NAME_LIST.add("Utopia");
        NAME_LIST.add("Vadera");
        NAME_LIST.add("Vagra");
        NAME_LIST.add("Vandor");
        NAME_LIST.add("Ventax");
        NAME_LIST.add("Xenon");
        NAME_LIST.add("Xerxes");
        NAME_LIST.add("Yew");
        NAME_LIST.add("Yojimbo");
        NAME_LIST.add("Zalkon");
        NAME_LIST.add("Zuul");
        Collections.shuffle(NAME_LIST);
    }

    public static String getName() {
        if (!NAME_LIST.isEmpty()) {
            return NAME_LIST.remove(0);
        } else {
            System.out.println("No more solar system names for now!");
            System.exit(1);
            return null;
        }
    }
}
