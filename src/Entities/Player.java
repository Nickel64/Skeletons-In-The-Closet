package Entities;

import java.awt.*;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Player implements Entity{
    private Image sprite; //the visual representation of the unit
    private int health; // how much health the unit has
    private int damage; // how much damage the unit deals
    private int speed; // how fast the unit can move


    public int getHealth(){return health;}
    public int getDamage(){return damage;}
    public int getSpeed(){return speed;}
    public Image getSprite(){return sprite;}

    public boolean isDead(){
        if (health<=0){
            return true;
        }
        return false;
    }
    public void attack(){

    }
}
