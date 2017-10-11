package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Nothing implements Entity {
    public boolean inAggroRange(){throw new Error();}
    public boolean isDead(){return false;}
    public void damaged(int damage){}

    @Override
    public void setDirection(Direction dir) {

    }

    @Override
    public int getDamage() {
        return -1;
    }

    @Override
    public int getHealth() {
        return -1;
    }

    @Override
    public int getMaxHealth() {
        return -1;
    }

    public void attack(Entity entity){}

    public boolean canMove() {
        return true;
    }

    public boolean canStepOn() {
        return true;
    }

    @Override
    public void ping() {
        //do nothing
    }

    public String getImageName(){
        return null;
    }

    public String toString() {
        return ".";
    }
    //update view, then view calls model;

    @Override
    public int getLevel() {
        return 0;
    }
}
