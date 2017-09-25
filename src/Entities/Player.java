package Entities;

import java.awt.*;
import java.util.Observable;
/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Player extends Observable implements Entity {
    private Direction dir;
    private Image sprite; //the visual representation of the unit
    private int health = 100; // how much health the unit has
    private int special = 100;
    private int exp = 0;
    private int level;
    private int damage; // how much damage the unit deals
    private int speed; // how fast the unit can move

    public Player(int health, int damage, int speed){

    }

    public Player(){

    }
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
    public void attack(Entity entity){
        entity.damaged(this.damage);
    }
    public void damaged(int damageAmount){
        this.health = this.health - damageAmount;
    }
    public boolean inAggroRange(){return false;}

    public void start() {}

    public boolean canMove() {
        return true;
    }

    public boolean canStepOn() {
        return false;
    }

    public String toString() {
        return "+";
    }
}
