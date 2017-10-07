package Entities;

import java.awt.*;
import java.util.Observable;
import java.util.StringJoiner;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Enemy extends Observable implements Entity {
    private int name;
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
    public Enemy(int name, int health, int damage, int speed){
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
    }

    public String getImageName(){
        return null;
    }

    public void setDirection(Direction dir){
        this.dir = dir;
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
        entity.damaged(this.damage);
        System.out.println("Enemy hit for: "+damage);
    }
    public void move(){

    }
    public void damaged(int damageAmount){
        this.health = this.health - damageAmount;
        if(isDead()){
            //tell view that this entity is dead
        }
        System.out.println("enemy health from "+(this.health+damageAmount)+" to "+this.health);
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
        return name+"";
    }

}
