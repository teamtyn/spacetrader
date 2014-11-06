/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader.player;

import java.util.ArrayList;
import java.util.List;
import spacetrader.GameModel;

/**
 *
 * @author Local Clayton
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
    
    public static String getName() {
        return names.get(GameModel.getRandom().nextInt(names.size()));
    }
}
