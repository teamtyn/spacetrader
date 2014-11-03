/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetrader.items;

import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

public class Shield{
    private int strength;
    private int rechargeRate;
    private ShieldType type;
    
    public enum ShieldType {
        //TODO: Balancing to make better.
        //Name      str   RoC   cost  color
        Kite        (100, 10,   100,  Color.RED),
        Heater      (125, 10,   300,  Color.GREEN),
        Targe       (100, 15,   300,  Color.PINK),
        Buckler     (25,  25,   300,  Color.AQUA),
        Ishlangu    (400, 0,    300,  Color.WHITE),
        Hoplon      (200, 15,   500,  Color.DARKKHAKI),
        Riot        (300, 10,   500,  Color.BLACK),
        BatterSea   (400, 5,    500,  Color.DARKGOLDENROD),
        Scutum      (500, 10,   1000, Color.DARKRED),
        Aegis       (500, 25,   2500, Color.YELLOW),
        Svalinn     (1000,10,   1000, Color.BLUEVIOLET),
        ;

        public final int shieldStrength;
        public final int rechargeRate;
        public final int cost;
        public final SerializableColor color;

        ShieldType(int shieldStrength, int rechargeRate, int cost, Color color) {
            this.shieldStrength = shieldStrength;
            this.rechargeRate = rechargeRate;
            this.cost = cost;
            this.color = new SerializableColor(color);
        }
    };
    
    public Shield(ShieldType type){
        this.strength = type.shieldStrength;
        this.rechargeRate = type.rechargeRate;
        this.type = type;
    }
    
    //Return strength of shield
    public int getStrength(){
        return strength;
    }
    
    //Recharge the shield for X units of time
    public int recharge(int time){
        strength += rechargeRate * time;
        return strength;
    }
    
    //Damage the shield for X damages.  ALL DAMAGE IS BLOCKED BY SHIELD EVEN IF OVERFLOW
    public int doDamage(int damage){
        strength -= damage;
        if(strength < 0){
            strength = 0;
        }
        return strength;
    }
}
