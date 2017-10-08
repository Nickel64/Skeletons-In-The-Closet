package Entities;

import Utils.EntitySet;

import java.awt.*;
import java.util.Observable;
/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Player extends Observable implements Entity {
    private Direction dir = Direction.Right;
    private int health = 10; // how much health the unit has
    private int maxHealth =10;
    private int maxSpecial = 100;
    private int special = 100;
    private int exp = 0;
    private int maxExp = 100;
    //private int level;
    private int damage = 1; // how much damage the unit deals
    private int speed; // how fast the unit can move
    private int experience;
    private int level = 1;

    private EntitySet images;

    public Player(int health, int damage){
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        images = new EntitySet(true, 0);
        System.out.println("Player created");
    }

    public Player(){
        images = new EntitySet(true, 0);
    }
    public int getMaxHealth(){return maxHealth;}
    public int getHealth(){return health;}
    public int getMaxSpecial(){return maxSpecial;}
    public int getSpecial(){return special;}
    public int getMaxExp(){return maxExp;}
    public int getExp(){return exp;}
    public int getDamage(){return damage;}
   // public int getSpeed(){return speed;}

    public Direction getDir() { return dir; }

    public void setHealth(int Health){ health = Health;}
    public void  setDamage(int Damage){ damage = Damage;}
    //public void  setSpeed(int Speed){speed = Speed;}
    public void setSpecial(int s){special = s;}

    public void setMaxHealth(int Health){maxHealth = Health;}

    public void incExp(int xp){
        this.exp += xp;
        if(this.exp >= maxExp){
            levelUp();
        }
    }

    public String getImageName(){
        return null;
    }

    public boolean isDead(){
        if (health<=0){
            return true;
        }
        return false;
    }
    public void levelUp(){
        exp = 0;
        level++;
        damage = damage + 2;
    }
    /**
     * attack method for changing the players sprite to attack
     */
    public void attack(){

    }

    /**
     * attack method for when an entity is in the target range, so damage is dealt
     * @param entity
     */
    public void attack(Entity entity){
        entity.damaged(this.damage);
//        attack();
    }
    public void damaged(int damageAmount){
        this.health = this.health - damageAmount;
        System.out.println(this.health);
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

    public void setDirection(Direction dir){
        this.dir = dir;
    }

    public void attackAOE(Entity[] entities){
        setChanged();
        if(this.special-10 >0){
            for (Entity e : entities) {
                attack(e);
            }
            this.special -= 10;
            notifyObservers();
            return;
        }
        clearChanged();
    }
    //change the sprite of the player, can't move the player as it doesn't know its tile
    public void move(Direction dir){
        if(dir==Direction.Up){
        //be first sprite, update view, be moving sprite update view, be final sprite
        }
        else if(dir==Direction.Down){

        }
        else if(dir==Direction.Left){

        }
        else if(dir==Direction.Right){

        }

        else{
            throw new Error("Direction not valid");
        }
    }

    public void increaseAttack(int attack){
        this.damage = attack + this.damage;
    }

    public Image getIdle(){
        return images.getIdle(dir.ordinal());
    }
}
