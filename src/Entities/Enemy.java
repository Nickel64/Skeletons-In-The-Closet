package Entities;

import java.awt.*;
import java.util.Observable;
/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Enemy extends Observable implements Entity {
    private Direction dir;
    private Image sprite; //the visual representation of the unit
    private int health; // how much health the unit has
    private int damage; // how much damage the unit deals
    private int speed; // how fast the unit can move
    private boolean inRange;

    public int getHealth(){return health;}
    public int getDamage(){return damage;}
    public int getSpeed(){return speed;}
    public Image getSprite(){return sprite;}

    //name will determine the sprite or something
    public Enemy(String name, int health, int damage, int speed){

    }
    public Enemy(){

    }
    /**
     * Calculates whether the enemy is in range to attack the player
     * @return inRange
     */
    public boolean inAggroRange(){
        return inRange;
    }
    public boolean isDead(){
        if (health<=0){
            return true;
        }
        return false;
    }
    public void attack(Entity entity){

    }
    public void move(){

    }
    public void damaged(int damageAmount){

    }

    public void start() {
        //TODO: start pinging
    }

    public boolean canMove() {
        return true;
    }

    public boolean canStepOn() {
        return false;
    }

    public String toString() {
        return "E";
    }

}
