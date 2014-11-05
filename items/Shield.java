/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacetrader.items;

import java.io.Serializable;
import javafx.scene.paint.Color;
import spacetrader.ui.SerializableColor;

public class Shield implements Serializable {
    private int strength;
    private int rechargeRate;
    private String name;
    private ShieldType type;
    
    public enum ShieldType {
        //TODO: Balancing to make better.
        //Name      str   RoC   cost  color
        Kite        (100, 10,   100,  new SerializableColor(Color.RED), "Kite"),
        Heater      (125, 10,   300,  new SerializableColor(Color.GREEN), "Heater"),
        Targe       (100, 15,   300,  new SerializableColor(Color.PINK), "Targe"),
        Buckler     (25,  25,   300,  new SerializableColor(Color.AQUA), "Buckler"),
        Ishlangu    (400, 0,    300,  new SerializableColor(Color.WHITE), "Ishlangu"),
        Hoplon      (200, 15,   500,  new SerializableColor(Color.DARKKHAKI), "Hoplon"),
        Riot        (300, 10,   500,  new SerializableColor(Color.BLACK), "Riot"),
        BatterSea   (400, 5,    500,  new SerializableColor(Color.DARKGOLDENROD), "BatterSea"),
        Scutum      (500, 10,   1000, new SerializableColor(Color.DARKRED), "Scutum"),
        Aegis       (500, 25,   2500, new SerializableColor(Color.YELLOW), "Aegis"),
        Svalinn     (1000,10,   1000, new SerializableColor(Color.BLUEVIOLET), "Svalinn"),
        ;

        public final int shieldStrength;
        public final int rechargeRate;
        public final int cost;
        public final String name;
        public final SerializableColor color;

        ShieldType(int shieldStrength, int rechargeRate, int cost, SerializableColor color, String name) {
            this.shieldStrength = shieldStrength;
            this.rechargeRate = rechargeRate;
            this.cost = cost;
            this.color = color;
            this.name = name;
        }
    };
    
    public Shield(ShieldType type){
        this.strength = type.shieldStrength;
        this.rechargeRate = type.rechargeRate;
        this.type = type;
        this.name = type.name;
    }
    
    //Return strength of shield
    public int getStrength(){
        return strength;
    }
    
    //Return racharge rate of shield
    public int getRechargeRate(){
        return rechargeRate;
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
    
    public String getName(){
        return name;
    }
    
    public ShieldType getType(){
        return type;
    }
}
