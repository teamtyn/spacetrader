package spacetrader.items;

import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

/**
 *
 * @author Purcell7
 */
public class Weapon{
    private WeaponType type;
    private int damage;
    private int rateOfFire;
    private String name;
    
    public enum WeaponType {
        //Name      dmg   rof   cost  color
        Gladius    (10,   10,   100,  new SerializableColor(Color.GREEN), "Gladius"), 
        Spatha     (30,   10,   300,  new SerializableColor(Color.BLUE), "Spatha"), 
        Katana     (10,   30,   301,  new SerializableColor(Color.BLUE), "Katana"),
        Rapier     (5,    60,   300,  new SerializableColor(Color.BLUE), "Rapier"),
        Kampilan   (25,   20,   500,  new SerializableColor(Color.RED), "Kampilan"),
        Scimitar   (1,    500,  500,  new SerializableColor(Color.RED), "Scimitar"),
        Claymore   (500,  1,    500,  new SerializableColor(Color.RED), "Claymore"),
        Joyeuse    (100,  25,   2500, new SerializableColor(Color.GOLD), "Joyeuse"),
        Szczerbiec (250,  10,   2500, new SerializableColor(Color.GOLD), "Szczerbiec"),
        ;

        public final int damage;
        public final int rateOfFire;
        public final int cost;
        public final String name;
        public final SerializableColor color;

        WeaponType(int damage, int rateOfFire, int cost, SerializableColor color, String name) {
            this.damage = damage;
            this.rateOfFire = rateOfFire;
            this.cost = cost;
            this.color = color;
            this.name = name;
        }
    };
    
    public Weapon(WeaponType type){
        this.type = type;
        this.damage = type.damage;
        this.rateOfFire = type.rateOfFire;
        this.name = type.name;
    }
    
    public int getDamage(){
        return damage;
    }
    
    public int getRateOfFire(){
        return rateOfFire;
    }
    
    public WeaponType getType(){
        return type;
    }
    
    public String getName(){
        return name;
    }
    
    public String toString(){
        return name;
    }
}