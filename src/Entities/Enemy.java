package Entities;

import Behaviour.Pathfinder;
import Utils.EntitySet;
import Utils.Resources;

import java.awt.*;
import java.util.Observable;
import java.util.StringJoiner;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Enemy extends Observable implements Entity, java.io.Serializable {
    protected Direction dir;
    protected int health; // how much health the unit has
    protected int maxHealth;  //needed for hp display purposes
    protected int damage; // how much damage the unit deals
    protected int speed; // how fast the unit can move
    protected boolean inRange;
    protected int level;


    protected boolean attacking = false;
    protected boolean dying = false;
    protected int animCount = 0;  //max value of 6
    protected transient EntitySet images;

    public int getHealth(){return health;}
    public int getMaxHealth(){return maxHealth;}
    public int getSpeed(){return speed;}

    //name will determine the sprite or something
    public Enemy(int level, int health, int damage, int speed){
        this.level = level;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.speed = speed;
        dir = Direction.Right;
        images = new EntitySet(false, false, level);
    }

    public EntitySet getImages(){
        return images;
    }

    public void resetImage() {images = new EntitySet(false, false, level);}

    public Image getIdle() {
        return images.getIdle(dir.ordinal());
    }

    public Image getAttack(){
        return images.getAttack(dir.ordinal(), animCount);
    }

    public int getLevel(){
        return level;
    }

    @Override
    public int getDamage() {
        return damage;
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
    public void attack(Entity entity, boolean aoe){
        entity.damaged(this.damage);
        if(isDead()){

        }
    }
    public void damaged(int damageAmount){
        this.health = this.health - damageAmount;
        Resources.playAudio("DamageEnemy.wav");
    }

    public boolean canMove() {
        return true;
    }

    public boolean canStepOn() {
        return false;
    }

    public String toString() {
        return Integer.toString(level);
    }


    public boolean ping(){
        if(attacking) {
            if (animCount < 5) {
                animCount++;
                if(Resources.DEBUG) System.out.println("Enemy Animation progress: " + animCount);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void resetEnemyActions(String action){
        switch(action){
            case "atk":
                attacking = false;
                break;
        }
        this.animCount = 0;
    }

    public void startAction(String action) {
        switch (action) {
            case "atk":
                attacking = true;
                break;
        }
        this.animCount = 0;
    }

    public boolean isEnemyAttack(){return this.attacking;}
}
