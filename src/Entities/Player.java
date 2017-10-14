package Entities;

import Utils.EntitySet;
import Utils.Resources;

import java.awt.*;
import java.util.Observable;
/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Player extends Observable implements Entity, java.io.Serializable {
    private Direction dir = Direction.Right;

    private int health = 100; // how much health the unit has
    private int maxHealth = 100;
    private int maxSpecial = 100;
    private int special = 100;
    private int exp = 0;
    private int maxExp = 100;
    private int damage = 1; // how much damage the unit deals
    private int level = 1;

    //animation related fields
    private boolean defending = false;
    private boolean attacking = false;
    private boolean dying = false;
    private boolean aoe = false;
    private int animCount = 0;  //max value of 6

    private transient EntitySet images;

    public Player(int health, int damage) {
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        images = new EntitySet(true, 0);
    }

    public Player() {
        images = new EntitySet(true, 0);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxSpecial() {
        return maxSpecial;
    }

    public int getSpecial() {
        return special;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public int getExp() {
        return exp;
    }

    public int getDamage() {
        return damage;
    }
    // public int getSpeed(){return speed;}

    public Direction getDir() {
        return dir;
    }


    public void setHealth(int Health) {
        health = Health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int Damage) {
        damage = Damage;
    }

    //public void  setSpeed(int Speed){speed = Speed;}
    public void setSpecial(int s) {
        special = s;
    }


    public void incExp(int xp) {
        setChanged();
        this.exp += xp;
        if (this.exp >= maxExp) {
            levelUp();
        }
        notifyObservers();
    }

    public String getImageName() {
        return null;
    }

    public boolean isDead() {
        if (health <= 0) {
            dying = true;
            return true;
        }
        return false;
    }

    public void levelUp() {
        exp = 0;
        level++;
        damage = damage + 2;
    }

    /**
     * attack method for changing the players sprite to attack
     */
    public void attack() {

    }

    public boolean isDefending() {
        return defending;
    }

    public void toggleGuard() {
        //toggling off
        if (defending) {
            defending = !defending;
            //TODO play a sound
            System.out.println("not blocking damage");
            return;
        }
        //toggling on
        else if (special > 0) {
            //valid to block
            this.defending = true;
            System.out.println("blocking damage");

        }
    }
    public int getLevel(){
        return level;
    }
    /**
     * attack method for when an entity is in the target range, so damage is dealt
     *
     * @param entity
     */
    public void attack(Entity entity) {
        entity.damaged(this.damage);
//        attack();
        if(entity.isDead()){
            incExp(10);
        }
    }

    public void damaged(int damageAmount) {
        setChanged();
        if (defending) {
            int beforeSpecial = special;
            this.special -= damageAmount;
            damageAmount = damageAmount - beforeSpecial;
            if (damageAmount > 0) { //defence broken
                defending = false;
                this.health = this.health - damageAmount;
                System.out.println("Play a guard breaking sound here");
            }
        } else {
            this.health = this.health - damageAmount;
            System.out.println(this.health);
        }
        notifyObservers();
    }

    public boolean inAggroRange() {
        return false;
    }

    public boolean canMove() {
        return true;
    }

    public boolean canStepOn() {
        return false;
    }

    public String toString() {
        return "+";
    }

    public void setDirection(Direction dir) {
        this.dir = dir;
    }

    public void attackAOE() {
        setChanged();
        notifyObservers();
    }

    //change the sprite of the player, can't move the player as it doesn't know its tile
    public void move(Direction dir) {
        if (dir == Direction.Up) {
            //be first sprite, update view, be moving sprite update view, be final sprite
        } else if (dir == Direction.Down) {

        } else if (dir == Direction.Left) {

        } else if (dir == Direction.Right) {

        } else {
            throw new Error("Direction not valid");
        }
    }

    public void increaseAttack(int attack) {
        this.damage = attack + this.damage;
    }

    public Image getIdle() {
        return images.getIdle(dir.ordinal());
    }
    public Image getDefending(){return images.getDefending();}

    public Image getAoE(){return images.getAoE();}

    public Image getAttack(){
        return images.getAttack(dir.ordinal(), animCount);
    }

    public boolean ping(){
        //stuff incoming
        if(attacking || aoe) {
            if (animCount < 5) {
                animCount++;
                if(Resources.DEBUG) System.out.println("Player Animation progress: " + animCount);
                return false;
            } else {
                animCount = 0;
                return true;
            }
        }
        return false;
    }

    /**
     * Used to Reset the TileSet
     * after this class is Deserialized
     */
    public void resetPlayer(){
        images = new EntitySet(true, 0);
    }

    public boolean isPlayerAttack(){return this.attacking;}
    public boolean isPlayerAttackAoE(){return this.aoe;}
    public boolean isPlayerDying(){return this.isDead();}

    public void resetPlayerActions(String action){
        switch(action){
            case "atk":
                attacking = false;
                break;
            case "aoe":
                aoe = false;
                break;
        }
        this.animCount = 0;
    }

    public void startAction(String action){
        switch(action){
            case "atk":
                attacking = true;
                break;
            case "aoe":
                aoe = true;
                break;
        }
        this.animCount = 0;
    }
}
