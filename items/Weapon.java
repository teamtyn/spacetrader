package spacetrader.items;

import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 *
 * @author Purcell7
 */
public class Weapon{
    public enum WeaponType {
        //Name      dmg   rof   cost  color
        Gladius    (10,   10,   100,  Color.GREEN), 
        Spatha     (30,   10,   300,  Color.BLUE), 
        Katana     (10,   30,   301,  Color.BLUE),
        Rapier     (5,    60,   300,  Color.BLUE),
        Kampilan   (25,   20,   500,  Color.RED),
        Scimitar   (1,    500,  500,  Color.RED),
        Claymore   (500,  1,    500,  Color.RED),
        Joyeuse    (100,  25,   2500, Color.GOLD),
        Szczerbiec (250,  10,   2500, Color.GOLD),
        ;

        public final int damage;
        public final int rateOfFire;
        public final int cost;
        public final SerializableColor color;

        WeaponType(int damage, int rateOfFire, int cost, Color color) {
            this.damage = damage;
            this.rateOfFire = rateOfFire;
            this.cost = cost;
            this.color = new SerializableColor(color);
        }
    };
}